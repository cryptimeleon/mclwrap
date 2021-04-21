package org.cryptimeleon.math.structures.groups.elliptic.type3.mcl;

import com.herumi.mcl.Fp;
import com.herumi.mcl.G2;
import org.cryptimeleon.math.random.RandomGenerator;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;

class MclGroup2Impl extends MclGroupImpl {
    protected MclGroup2ElementImpl generator = null;

    public MclGroup2Impl() {
        super();
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
        Fp xa = new Fp("12723517038133731887338407189719511622662176727675373276651903807414909099441");
        Fp xb = new Fp("4168783608814932154536427934509895782246573715297911553964171371032945126671");
        Fp ya = new Fp("13891744915211034074451795021214165905772212241412891944830863846330766296736");
        Fp yb = new Fp("7937318970632701341203597196594272556916396164729705624521405069090520231616");

        G2 res = new G2(xa, xb, ya, yb);

        return generator = createElement(res);
    }

    @Override
    public double estimateCostInvPerOp() {
        return 2.4;
    }
}
