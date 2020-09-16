package de.upb.crypto.math.pairings.mcl.test;

import de.upb.crypto.math.factory.BilinearGroup;
import de.upb.crypto.math.factory.BilinearGroupRequirement;
import de.upb.crypto.math.interfaces.mappings.BilinearMap;
import de.upb.crypto.math.pairings.mcl.MclBilinearGroupProvider;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class PairingPerformanceTest extends de.upb.crypto.math.performance.pairing.PairingPerformanceTest {
    public PairingPerformanceTest(BilinearMap pairing) {
        super(pairing);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<BilinearMap> initializePairings() {
        ArrayList<BilinearMap> pairings = new ArrayList<>();
        pairings.add(
                new MclBilinearGroupProvider().provideBilinearGroup(
                        128, new BilinearGroupRequirement(BilinearGroup.Type.TYPE_3, 1)
                ).getBilinearMap()
        );

        return pairings;
    }
}
