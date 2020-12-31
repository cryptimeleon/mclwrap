package de.upb.crypto.math.pairings.mcl.test;

import de.upb.crypto.math.pairings.generic.BilinearGroup;
import de.upb.crypto.math.pairings.generic.BilinearMap;
import de.upb.crypto.math.pairings.mcl.MclBilinearGroupImpl;
import de.upb.crypto.math.structures.groups.lazy.LazyBilinearGroup;
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
        pairings.add(new LazyBilinearGroup(new MclBilinearGroupImpl()).getBilinearMap());

        return pairings;
    }
}
