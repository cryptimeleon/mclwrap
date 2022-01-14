package org.cryptimeleon.mclwrap.bn254;

import org.cryptimeleon.math.pairings.PairingTests;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BLSPairingTest extends PairingTests {
    public BLSPairingTest(BilinearMap pairing) {
        super(pairing);
        MclBilinearGroup.resetMclInitializationAndAcceptErrors();
        MclBilinearGroupImpl.init(MclBilinearGroup.GroupChoice.BLS12_381);
    }

    @Parameterized.Parameters(name = "Test: {0}")
    public static Collection<BilinearMap[]> data() {
        MclBilinearGroup.resetMclInitializationAndAcceptErrors();
        BilinearMap[][] params = new BilinearMap[][]{{
                new MclBilinearGroup(MclBilinearGroup.GroupChoice.BLS12_381).getBilinearMap()
        }};
        return Arrays.asList(params);
    }
}
