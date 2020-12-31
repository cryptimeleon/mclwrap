package de.upb.crypto.math.pairings.mcl.test;

import de.upb.crypto.math.pairings.generic.BilinearGroup;
import de.upb.crypto.math.pairings.generic.BilinearMap;
import de.upb.crypto.math.pairings.mcl.MclBilinearGroupImpl;
import de.upb.crypto.math.pairings.test.PairingTests;
import de.upb.crypto.math.structures.groups.lazy.LazyBilinearGroup;
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
                new LazyBilinearGroup(new MclBilinearGroupImpl()).getBilinearMap()
        }};
        return Arrays.asList(params);
    }
}
