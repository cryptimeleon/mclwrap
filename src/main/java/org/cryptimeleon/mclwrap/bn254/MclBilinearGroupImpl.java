package org.cryptimeleon.mclwrap.bn254;

import com.herumi.mcl.Mcl;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.serialization.StringRepresentation;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearGroup;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearGroupImpl;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearMapImpl;
import org.cryptimeleon.math.structures.groups.mappings.impl.GroupHomomorphismImpl;
import org.cryptimeleon.math.structures.groups.mappings.impl.HashIntoGroupImpl;

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

    protected static void init(boolean printError) {
        if (!isInitialized) {
            String lib = "mcljava";
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
                // only offer BN254, offering both difficult since it is done statically inside Mcl
                Mcl.SystemInit(Mcl.BN254);
            } catch (UnsatisfiedLinkError le) {
                if (printError) {
                    le.printStackTrace();
                    System.err.println("mcl library was found, but its functions cannot be called properly");
                }
                return;
            }
            Mcl.verifyOrderG1(true);
            Mcl.verifyOrderG2(true);
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
