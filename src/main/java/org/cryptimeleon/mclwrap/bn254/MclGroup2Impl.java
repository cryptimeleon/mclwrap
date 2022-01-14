package org.cryptimeleon.mclwrap.bn254;

import com.herumi.mcl.G2;
import com.herumi.mcl.Mcl;
import org.cryptimeleon.math.random.RandomGenerator;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;

class MclGroup2Impl extends MclGroupImpl {
    protected MclGroup2ElementImpl generator = null;

    public MclGroup2Impl(MclBilinearGroup.GroupChoice groupChoice) {
        super(groupChoice);
    }

    public MclGroup2Impl(Representation repr) {
        super(repr);
    }

    @Override
    public MclGroup2ElementImpl getElement(String string) {
        return createElement(getInternalObjectFromString(string));
    }

    @Override
    public GroupElementImpl restoreElement(Representation repr) {
        return new MclGroup2ElementImpl(this, repr);
    }

    @Override
    protected G2 getInternalObjectFromString(String str) {
        G2 result = new G2();
        result.setStr(str);
        return result;
    }

    protected MclGroup2ElementImpl createElement(G2 G2) {
        return new MclGroup2ElementImpl(this, G2);
    }

    @Override
    public MclGroup2ElementImpl getNeutralElement() {
        return getElement("0");
    }

    @Override
    public MclGroup2ElementImpl getUniformlyRandomElement() throws UnsupportedOperationException {
        return getGenerator().pow(RandomGenerator.getRandomNumber(size()));
    }

    @Override
    public MclGroup2ElementImpl getGenerator() throws UnsupportedOperationException {
        if (generator != null)
            return generator;
        G2 res = new G2();
        Mcl.hashAndMapToG2(res, "some arbitrary element".getBytes());
        return generator = createElement(res);
    }

    @Override
    public double estimateCostInvPerOp() {
        switch (groupChoice) {
            case BN254:
                return 2.3;
            case BLS12_381:
                return 3.3;
        }
        throw new IllegalArgumentException("Unknown cost estimate.");
    }
}
