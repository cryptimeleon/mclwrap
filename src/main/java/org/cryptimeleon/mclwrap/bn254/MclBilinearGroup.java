package org.cryptimeleon.mclwrap.bn254;

import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.lazy.LazyBilinearGroup;

/**
 * A wrapper (with lazy evaluation of operations) around the efficient type 3 Barreto-Naehrig or BLS pairing implementation
 * provided by the Mcl library.
 * <p>
 * Operation evaluation is done lazily via {@link LazyBilinearGroup}.
 * This class should be preferred over {@link MclBasicBilinearGroup} due to potential performance advantages.
 *
 * @see <a href="https://github.com/herumi/mcl">Mcl library on Github</a>
 */
public class MclBilinearGroup extends LazyBilinearGroup {

    public enum GroupChoice {
        BN254(0),
        BLS12_381(5);
        //, SECP256K1(102)

        final int mclconstant;

        GroupChoice(int mclconstant) {
            this.mclconstant = mclconstant;
        }
    }

    static final MclBilinearGroup.GroupChoice defaultGroup = MclBilinearGroup.GroupChoice.BN254;

    public MclBilinearGroup() {
        this(defaultGroup);
    }

    public MclBilinearGroup(GroupChoice groupChoice) {
        super(new MclBilinearGroupImpl(groupChoice));
    }

    public MclBilinearGroup(Representation repr) {
        super(repr);
    }

    /**
     * Usually, you can only instantiate one of the Mcl groups (BN/BLS).
     * If you try to instantiate the second one within the same JVM, you'll be met with an exception.
     * This is a known limitation right now https://github.com/cryptimeleon/mclwrap/issues/5.
     *
     * Calling this method allows you to reset the group so that you can instantiate the "other" one.
     * Please only call this if you know what you're doing. Existing GroupElements will be reinterpreted for the new group,
     * so everything will behave pretty weirdly if you keep using them.
     *
     * This should be fine to do if you don't ever use the old Group/GroupElement/etc. objects anymore after calling this.
     */
    public static void resetMclInitializationAndAcceptErrors() {
        MclBilinearGroupImpl.initializedGroup = null;
        System.err.println("Resetting mcl initialization. Hope you know what you're doing.");
    }
}
