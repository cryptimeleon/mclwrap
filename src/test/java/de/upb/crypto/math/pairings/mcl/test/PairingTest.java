package de.upb.crypto.math.pairings.mcl.test;

import de.upb.crypto.math.pairings.generic.BilinearMap;
import de.upb.crypto.math.pairings.mcl.MclBilinearGroup;
import de.upb.crypto.math.pairings.test.PairingTests;
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
