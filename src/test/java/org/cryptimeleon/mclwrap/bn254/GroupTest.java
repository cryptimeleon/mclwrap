package org.cryptimeleon.mclwrap.bn254;

import org.cryptimeleon.math.structures.GroupTests;
import org.cryptimeleon.math.structures.groups.basic.BasicGroup;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class GroupTest extends GroupTests {

    public GroupTest(GroupTests.TestParams params) {
        super(params);
        MclBilinearGroup.resetMclInitializationAndAcceptErrors();
        MclBilinearGroupImpl.init(MclBilinearGroup.GroupChoice.BN254);
    }

    @Parameterized.Parameters(name = "Test: {0}")
    // add (name="Test: {0}") for jUnit 4.12+ to print group's name to test
    public static Collection<TestParams[]> data() {
        MclBilinearGroup.resetMclInitializationAndAcceptErrors();
        // Collect parameters
        TestParams[][] params = new TestParams[][]{
                {new TestParams(new BasicGroup(new MclGroup1Impl(MclBilinearGroup.GroupChoice.BN254)))},
                {new TestParams(new BasicGroup(new MclGroup2Impl(MclBilinearGroup.GroupChoice.BN254)))},
                {new TestParams(new BasicGroup(new MclGroupTImpl(MclBilinearGroup.GroupChoice.BN254)))}
        };
        return Arrays.asList(params);
    }
}
