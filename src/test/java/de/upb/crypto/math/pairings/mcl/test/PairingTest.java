package de.upb.crypto.math.pairings.mcl.test;

import de.upb.crypto.math.factory.BilinearGroup;
import de.upb.crypto.math.factory.BilinearGroupRequirement;
import de.upb.crypto.math.interfaces.mappings.BilinearMap;
import de.upb.crypto.math.pairings.mcl.MclBilinearGroupProvider;
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
            new MclBilinearGroupProvider().provideBilinearGroup(
                        128, new BilinearGroupRequirement(BilinearGroup.Type.TYPE_3, 1)
                ).getBilinearMap()
        }};
        return Arrays.asList(params);
    }
}
