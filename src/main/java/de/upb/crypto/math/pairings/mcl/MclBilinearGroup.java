package de.upb.crypto.math.pairings.mcl;

import com.herumi.mcl.Bn256;
import de.upb.crypto.math.factory.BilinearGroup;
import de.upb.crypto.math.interfaces.hash.HashIntoStructure;
import de.upb.crypto.math.interfaces.mappings.BilinearMap;
import de.upb.crypto.math.interfaces.mappings.GroupHomomorphism;
import de.upb.crypto.math.serialization.Representation;
import de.upb.crypto.math.serialization.StringRepresentation;
import de.upb.crypto.math.structures.zn.HashIntoZn;

public class MclBilinearGroup implements BilinearGroup {
    private static boolean isInitialized = false;
    protected static MclGroup1 g1;
    protected static MclGroup2 g2;
    protected static MclGroupT gt;

    protected static MclHashIntoG1 hashIntoG1 = new MclHashIntoG1(g1);


    public MclBilinearGroup() {
        init(true);
    }

    public MclBilinearGroup(Representation repr) {
        this();
        if (!repr.str().get().equals("bn256")) {
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

    protected static void init(boolean printError) {
        if (!isInitialized) {
            String lib = "mclbn256";
            try {
                System.loadLibrary(lib);
            } catch (UnsatisfiedLinkError le) {
                if (printError) {
                    le.printStackTrace();
                    String libName = System.mapLibraryName(lib);
                    System.err.println("If you get this error, you need to put the file " + libName + " into one of the lib directories:");
                    System.err.println(System.getProperty("java.library.path"));
                }
                return;
            }
            try {
                Bn256.SystemInit();
            } catch (UnsatisfiedLinkError le) {
                if (printError) {
                    le.printStackTrace();
                    System.err.println("mcl library was found, but its functions cannot be called properly");
                }
                return;
            }
            isInitialized = true;
            g1 = new MclGroup1();
            g2 = new MclGroup2();
            gt = new MclGroupT();
        }
    }

    @Override
    public MclGroup1 getG1() {
        return g1;
    }

    @Override
    public MclGroup2 getG2() {
        return g2;
    }

    @Override
    public MclGroupT getGT() {
        return gt;
    }

    @Override
    public BilinearMap getBilinearMap() {
        return new MclPairing(this);
    }

    @Override
    public GroupHomomorphism getHomomorphismG2toG1() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("No homomorphism (type 3)");
    }

    @Override
    public HashIntoStructure getHashIntoG1() throws UnsupportedOperationException {
        return hashIntoG1;
    }

    @Override
    public HashIntoStructure getHashIntoG2() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("No hash available.");
    }

    @Override
    public HashIntoStructure getHashIntoGT() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("No hash available.");
    }

    @Override
    public HashIntoStructure getHashIntoZGroupExponent() throws UnsupportedOperationException {
        return new HashIntoZn(g1.size());
    }

    @Override
    public Representation getRepresentation() {
        return new StringRepresentation("bn256");
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MclBilinearGroup;
    }
}
