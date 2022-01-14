package org.cryptimeleon.mclwrap.serialization.standalone;

import org.cryptimeleon.math.serialization.standalone.StandaloneReprTest;
import org.cryptimeleon.mclwrap.bn254.MclBilinearGroup;

public class MclwrapStandaloneReprTest extends StandaloneReprTest {
    public MclwrapStandaloneReprTest() {
        super("org.cryptimeleon.mclwrap");
    }

    @Override
    public void testStandaloneRepresentablesWithParameterlessConstructors() {
        MclBilinearGroup.resetMclInitializationAndAcceptErrors();
        super.testStandaloneRepresentablesWithParameterlessConstructors();
    }
}
