package org.cryptimeleon.mclwrap.bn254;

import org.cryptimeleon.math.structures.GroupTests;
import org.cryptimeleon.math.structures.groups.basic.BasicGroup;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BLSGroupTest extends GroupTests {

    public BLSGroupTest(TestParams params) {
        super(params);
        MclBilinearGroup.resetMclInitializationAndAcceptErrors();
        MclBilinearGroupImpl.init(MclBilinearGroup.GroupChoice.BLS12_381);
    }

    @Parameterized.Parameters(name = "Test: {0}")
    // add (name="Test: {0}") for jUnit 4.12+ to print group's name to test
    public static Collection<TestParams[]> data() {
        MclBilinearGroup.resetMclInitializationAndAcceptErrors();
        // Collect parameters
        TestParams[][] params = new TestParams[][]{
                {new TestParams(new BasicGroup(new MclGroup1Impl(MclBilinearGroup.GroupChoice.BLS12_381)))},
                {new TestParams(new BasicGroup(new MclGroup2Impl(MclBilinearGroup.GroupChoice.BLS12_381)))},
                {new TestParams(new BasicGroup(new MclGroupTImpl(MclBilinearGroup.GroupChoice.BLS12_381)))}
        };
        return Arrays.asList(params);
    }
}
