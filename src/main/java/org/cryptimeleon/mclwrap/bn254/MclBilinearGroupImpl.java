package org.cryptimeleon.mclwrap.bn254;

import com.herumi.mcl.Mcl;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.serialization.StringRepresentation;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearGroup;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearGroupImpl;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearMapImpl;
import org.cryptimeleon.math.structures.groups.mappings.impl.GroupHomomorphismImpl;
import org.cryptimeleon.math.structures.groups.mappings.impl.HashIntoGroupImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * A wrapper around the efficient type 3 Barreto-Naehrig pairing implementation with a group order of 254 bits provided
 * by the Mcl library.
 *
 * @see <a href="https://github.com/herumi/mcl">Mcl library on Github</a>
 */
class MclBilinearGroupImpl implements BilinearGroupImpl {
    private static boolean isInitialized = false;
    protected static MclGroup1Impl g1;
    protected static MclGroup2Impl g2;
    protected static MclGroupTImpl gt;

    protected static MclHashIntoG1Impl hashIntoG1;
    protected static MclHashIntoG2Impl hashIntoG2;


    public MclBilinearGroupImpl() {
        init(true);
    }

    public MclBilinearGroupImpl(Representation repr) {
        this();
        if (!repr.str().get().equals("bn254")) {
            throw new IllegalArgumentException("Invalid representation");
        }
    }

    /**
     * Returns true if the native library is available, false otherwise.
     */
    public static boolean isAvailable() {
        init(false);
        return isInitialized;
    }

    protected static boolean loadIncludedLibrary(){
        final String platformName = System.getProperty("os.name").toLowerCase();
        final String platformArch = System.getProperty("os.arch").toLowerCase();

        String requiredLibraryName = null;

        // for Windows, OS and arch names defined in windows/native/libjava/java_props_md.c
        // should be stable
        if(platformName.contains("win")){
            if(platformArch.equals("x86")){
                requiredLibraryName="mcljava-win-x86.dll";
            }
            if(platformArch.equals("amd64")){
                requiredLibraryName="mcljava-win-x64.dll";
            }
        }
        // OS name on Linux / BSDs is as returned by uname(2).
        // it is unclear whether the naming on linux is stable when it comes to Intel 32 bit
        if(platformName.contains("linux")){
            if(platformArch.equals("x86") || platformArch.equals("i386")){
                requiredLibraryName="mcljava-linux-x86.so";
            }
            if(platformArch.equals("amd64")){
                requiredLibraryName="mcljava-linux-x64.so";
            }
        }

        if(platformName.contains("mac")){
            if(platformArch.equals("amd64")){
                requiredLibraryName="mcljava-mac-x64.dylib";
            }
        }

        if(requiredLibraryName == null){
            return false;
        }
        InputStream nativeLibrary = MclBilinearGroupImpl.class.getResourceAsStream(requiredLibraryName);

        if(nativeLibrary == null){
            return false;
        }

        String tempdir;
        try {
            tempdir = Files.createTempDirectory("mclwrap").toAbsolutePath().toString();
        } catch (IOException e) {
            return false;
        }

        File outputFile = new File(tempdir+File.separator+requiredLibraryName);

        // never overwrite a library the user might have provided
        try{
            Files.copy(nativeLibrary, outputFile.toPath());
        } catch (IOException e) {
            return false;
        }

        try{
            System.load(outputFile.getAbsolutePath());
        }
        catch (Exception e){
            return false;
        }

        return true;
    }

    protected static void init(boolean printError) {
        if (!isInitialized) {
            String lib = "mcljava";
            try {
                System.loadLibrary(lib);
            } catch (UnsatisfiedLinkError le) {
                boolean couldLoadProvidedLibrary = loadIncludedLibrary();
                if (printError && !couldLoadProvidedLibrary) {
                    le.printStackTrace();
                    String libName = System.mapLibraryName(lib);
                    System.err.println("If you get this error, the required native library " + libName + " was not found and none of the included libraries could be used!");
                    System.err.println("You need to retrieve the native mcljava library that is appropriate for your platform and install it into one of the lib directories:");
                    System.err.println(System.getProperty("java.library.path"));
                    return;
                }
                else if(printError){
                        System.err.println("The required native mcl library was not found on this system, but one of the included pre-compiled libraries could be used.");
                        System.err.println("mclwrap will work as expected, but for optimal run-time performance, please compile the mcljava library from source and install it into one of the lib directories:");
                        System.err.println(System.getProperty("java.library.path"));
                }
            }
            try {
                // TODO: DO we want to offer the other curve type too?
                Mcl.SystemInit(Mcl.BN254);
            } catch (UnsatisfiedLinkError le) {
                if (printError) {
                    le.printStackTrace();
                    System.err.println("mcl library was found, but its functions cannot be called properly");
                }
                return;
            }
            isInitialized = true;
            g1 = new MclGroup1Impl();
            g2 = new MclGroup2Impl();
            gt = new MclGroupTImpl();
            hashIntoG1 = new MclHashIntoG1Impl(g1);
            hashIntoG2 = new MclHashIntoG2Impl(g2);
        }
    }

    @Override
    public MclGroup1Impl getG1() {
        return g1;
    }

    @Override
    public MclGroup2Impl getG2() {
        return g2;
    }

    @Override
    public MclGroupTImpl getGT() {
        return gt;
    }

    @Override
    public BilinearMapImpl getBilinearMap() {
        return new MclPairing(this);
    }

    @Override
    public GroupHomomorphismImpl getHomomorphismG2toG1() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("No homomorphism (type 3)");
    }

    @Override
    public HashIntoGroupImpl getHashIntoG1() throws UnsupportedOperationException {
        return hashIntoG1;
    }

    @Override
    public HashIntoGroupImpl getHashIntoG2() throws UnsupportedOperationException {
        return hashIntoG2;
    }

    @Override
    public HashIntoGroupImpl getHashIntoGT() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("No hash available.");
    }

    @Override
    public Integer getSecurityLevel() {
        return 100;
    }

    @Override
    public BilinearGroup.Type getPairingType() {
        return BilinearGroup.Type.TYPE_3;
    }

    @Override
    public Representation getRepresentation() {
        return new StringRepresentation("bn254");
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MclBilinearGroupImpl;
    }
}
