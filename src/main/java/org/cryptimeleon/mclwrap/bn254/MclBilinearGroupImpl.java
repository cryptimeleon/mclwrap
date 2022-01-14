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
 * A wrapper around the efficient type 3 Barreto-Naehrig or BLS pairing implementation provided by the Mcl library.
 *
 * @see <a href="https://github.com/herumi/mcl">Mcl library on Github</a>
 */
class MclBilinearGroupImpl implements BilinearGroupImpl {

    protected static MclBilinearGroup.GroupChoice initializedGroup = null;
    protected MclGroup1Impl g1;
    protected MclGroup2Impl g2;
    protected MclGroupTImpl gt;

    protected MclHashIntoG1Impl hashIntoG1;
    protected MclHashIntoG2Impl hashIntoG2;

    public MclBilinearGroupImpl(MclBilinearGroup.GroupChoice choice) {
        init(choice);
        g1 = new MclGroup1Impl(choice);
        g2 = new MclGroup2Impl(choice);
        gt = new MclGroupTImpl(choice);
        hashIntoG1 = new MclHashIntoG1Impl(g1);
        hashIntoG2 = new MclHashIntoG2Impl(g2);
    }

    public MclBilinearGroupImpl(Representation repr) {
        this(MclBilinearGroup.GroupChoice.valueOf(repr.str().get()));
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
            if(platformArch.equals("amd64") || platformArch.equals("x86_64")){
                requiredLibraryName="mcljava-win-x64.dll";
            }
        }
        // OS name on Linux / BSDs is as returned by uname(2).
        // it is unclear whether the naming on linux is stable when it comes to Intel 32 bit
        if(platformName.contains("linux")){
            if(platformArch.equals("x86") || platformArch.equals("i386")){
                requiredLibraryName="mcljava-linux-x86.so";
            }
            if(platformArch.equals("amd64") || platformArch.equals("x86_64")){
                requiredLibraryName="mcljava-linux-x64.so";
            }
        }

        if(platformName.contains("mac")){
            if(platformArch.equals("amd64") || platformArch.equals("x86_64")){
                requiredLibraryName="mcljava-mac-x64.dylib";
            }
        }

        if(requiredLibraryName == null){
            System.err.println("Could not find precompiled library for "+platformName+" "+platformArch);
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

    protected static synchronized void init(MclBilinearGroup.GroupChoice groupChoice) {
        if (initializedGroup == groupChoice)
            return;
        if (initializedGroup != null)
            throw new IllegalArgumentException("mcl has already been initialized with " + initializedGroup + ", you're trying to instantiate " + groupChoice + ". " +
                    "We currently do not support running multiple mcl settings at once. It's a known limitation of mcl's Java wrapper. " +
                    "If you promise you won't use the old GroupElements anymore (or don't fear undefined behavior), you can call MclBilinearGroupImpl.resetMclInitialization() and then retry.");
        //If you really, really want to make the two settings work concurrently, feel free to pull request that. It's nontrivial though.
        //You'd need to adapt the Java wrapper in this project and the corresponding C code in mcl, see discussion here: https://github.com/cryptimeleon/mclwrap/issues/5.

        String lib = "mcljava";
        try {
            System.loadLibrary(lib);
        } catch (UnsatisfiedLinkError le) {
            boolean couldLoadProvidedLibrary = loadIncludedLibrary();
            if (!couldLoadProvidedLibrary) {
                le.printStackTrace();
                String libName = System.mapLibraryName(lib);
                System.err.println("If you get this error, the required native library " + libName + " was not found and none of the included libraries could be used!");
                System.err.println("You need to retrieve the native mcljava library that is appropriate for your platform and install it into one of the lib directories:");
                System.err.println(System.getProperty("java.library.path"));
                System.err.println("See README.md");
                return;
            } else {
                System.err.println("The required native mcl library was not found on this system, but one of the included pre-compiled libraries could be used.");
                System.err.println("mclwrap will work as expected, but for optimal run-time performance, please compile the mcljava library from source and install it into one of the lib directories:");
                System.err.println(System.getProperty("java.library.path"));
                System.err.println("See README.md");
            }
        }
        try {
            Mcl.SystemInit(groupChoice.mclconstant);
        } catch (UnsatisfiedLinkError le) {
            throw new RuntimeException("mcl library was loaded, but cannot be initialized", le);
        }

        initializedGroup = groupChoice;
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
        switch (g1.groupChoice) {
            case BN254:
                return 100; //https://tools.ietf.org/id/draft-yonezawa-pairing-friendly-curves-00.html "BN256"
            case BLS12_381:
                return 127; //https://tools.ietf.org/id/draft-yonezawa-pairing-friendly-curves-00.html
        }
        throw new IllegalArgumentException("unknown security level");
    }

    @Override
    public BilinearGroup.Type getPairingType() {
        return BilinearGroup.Type.TYPE_3;
    }

    @Override
    public Representation getRepresentation() {
        return new StringRepresentation(g1.groupChoice.name());
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MclBilinearGroupImpl && ((MclBilinearGroupImpl) obj).g1.equals(g1);
    }

    @Override
    public String toString() {
        return g1.groupChoice.toString();
    }
}
