package org.cryptimeleon.mclwrap.bn254;

import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.lazy.LazyBilinearGroup;

/**
 * A wrapper (with lazy evaluation of operations) around the efficient type 3 Barreto-Naehrig pairing implementation
 * with a group order of 254 bits provided by the Mcl library.
 * <p>
 * Operation evaluation is done lazily via {@link LazyBilinearGroup}.
 * This class should be preferred over {@link MclBasicBilinearGroup} due to potential performance advantages.
 *
 * @see <a href="https://github.com/herumi/mcl">Mcl library on Github</a>
 */
public class MclBilinearGroup extends LazyBilinearGroup {

    public MclBilinearGroup() {
        super(new MclBilinearGroupImpl());
    }

    public MclBilinearGroup(Representation repr) {
        super(repr);
    }
}
