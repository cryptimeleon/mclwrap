package org.cryptimeleon.math.structures.groups.elliptic.type3.mcl;

import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.lazy.LazyBilinearGroup;

/**
 * Offers a less verbose way to instantiate the Mcl bilinear group which uses lazy evaluation.
 * <p>
 * Essentially just a {@link LazyBilinearGroup} wrapper around {@link MclBilinearGroup}.
 *
 * @see MclBilinearGroupImpl
 */
public class MclBilinearGroup extends LazyBilinearGroup {

    public MclBilinearGroup() {
        super(new MclBilinearGroupImpl());
    }

    public MclBilinearGroup(Representation repr) {
        super(repr);
    }
}
