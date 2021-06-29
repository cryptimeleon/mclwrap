package org.cryptimeleon.mclwrap.bn254;

import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.basic.BasicBilinearGroup;

/**
 * A wrapper (with naive evaluation of operations) around the efficient type 3 Barreto-Naehrig pairing implementation
 * with a group order of 254 bits provided by the Mcl library.
 * <p>
 * Operation evaluation is done naively via {@link BasicBilinearGroup}.
 *
 * @see <a href="https://github.com/herumi/mcl">Mcl library on Github</a>
 */
public class MclBasicBilinearGroup extends BasicBilinearGroup {

    public MclBasicBilinearGroup() {
        super(new MclBilinearGroupImpl());
    }

    public MclBasicBilinearGroup(Representation repr) {
        super(repr);
    }
}
