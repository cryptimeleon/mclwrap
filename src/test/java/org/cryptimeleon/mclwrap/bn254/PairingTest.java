package org.cryptimeleon.mclwrap.bn254;

import org.cryptimeleon.math.pairings.PairingTests;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class PairingTest extends PairingTests {
    public PairingTest(BilinearMap pairing) {
        super(pairing);
    }

    @Parameterized.Parameters(name = "Test: {0}")
    public static Collection<BilinearMap[]> data() {
        BilinearMap[][] params = new BilinearMap[][]{{
                new MclBilinearGroup().getBilinearMap()
        }};
        return Arrays.asList(params);
    }
}
