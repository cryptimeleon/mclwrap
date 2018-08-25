package de.upb.crypto.math.pairings.mcl.test;

import de.upb.crypto.math.pairings.mcl.MclGroup1;
import de.upb.crypto.math.pairings.mcl.MclGroup2;
import de.upb.crypto.math.pairings.mcl.MclGroupT;
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
                {new TestParams(new MclGroup1())},
                {new TestParams(new MclGroup2())},
                {new TestParams(new MclGroupT())}
        };
        return Arrays.asList(params);
    }
}
