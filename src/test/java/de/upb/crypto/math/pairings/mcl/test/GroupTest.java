package de.upb.crypto.math.pairings.mcl.test;

import com.herumi.mcl.Mcl;
import de.upb.crypto.math.pairings.mcl.MclGroup1Impl;
import de.upb.crypto.math.pairings.mcl.MclGroup2Impl;
import de.upb.crypto.math.pairings.mcl.MclGroupTImpl;
import de.upb.crypto.math.structures.groups.basic.BasicGroup;
import de.upb.crypto.math.structures.test.GroupTests;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class GroupTest extends GroupTests {

    public GroupTest(TestParams params) {
        super(params);
    }

    @Parameterized.Parameters(name = "Test: {0}")
    // add (name="Test: {0}") for jUnit 4.12+ to print group's name to test
    public static Collection<TestParams[]> data() {
        // Collect parameters
        TestParams params[][] = new TestParams[][]{
                {new TestParams(new BasicGroup(new MclGroup1Impl()))},
                {new TestParams(new BasicGroup(new MclGroup2Impl()))},
                {new TestParams(new BasicGroup(new MclGroupTImpl()))}
        };
        return Arrays.asList(params);
    }
}
