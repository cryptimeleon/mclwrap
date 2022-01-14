package org.cryptimeleon.mclwrap.bn254;

import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.basic.BasicBilinearGroup;

/**
 * A wrapper (with naive evaluation of operations) around the efficient type 3 Barreto-Naehrig or BLS pairing implementation
 * provided by the Mcl library.
 * <p>
 * Operation evaluation is done naively via {@link BasicBilinearGroup}.
 *
 * @see <a href="https://github.com/herumi/mcl">Mcl library on Github</a>
 */
public class MclBasicBilinearGroup extends BasicBilinearGroup {

    public MclBasicBilinearGroup() {
        this(MclBilinearGroup.defaultGroup);
    }

    public MclBasicBilinearGroup(MclBilinearGroup.GroupChoice groupChoice) {
        super(new MclBilinearGroupImpl(groupChoice));
    }

    public MclBasicBilinearGroup(Representation repr) {
        super(repr);
    }
}
