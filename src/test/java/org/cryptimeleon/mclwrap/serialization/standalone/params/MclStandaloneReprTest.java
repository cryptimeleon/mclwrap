package org.cryptimeleon.mclwrap.serialization.standalone.params;

import org.cryptimeleon.math.serialization.standalone.StandaloneReprSubTest;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearGroup;
import org.cryptimeleon.mclwrap.bn254.MclBasicBilinearGroup;
import org.cryptimeleon.mclwrap.bn254.MclBilinearGroup;

public class MclStandaloneReprTest extends StandaloneReprSubTest {
    public void testBilinearGroup(BilinearGroup bilGroup) {
        test(bilGroup);
        test(bilGroup.getG1());
        test(bilGroup.getG2());
        test(bilGroup.getGT());
        try {
            test(bilGroup.getHashIntoG1());
        } catch (UnsupportedOperationException ignored) {}
        try {
            test(bilGroup.getHashIntoG2());
        } catch (UnsupportedOperationException ignored) {}
        try {
            test(bilGroup.getHashIntoGT());
        } catch (UnsupportedOperationException ignored) {}
        try {
            test(bilGroup.getHomomorphismG2toG1());
        } catch (UnsupportedOperationException ignored) {}
    }

    public void testMclBilinearGroup() {
        testBilinearGroup(new MclBilinearGroup());
        testBilinearGroup(new MclBasicBilinearGroup());
    }
}
