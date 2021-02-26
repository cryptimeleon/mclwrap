package org.cryptimeleon.math.structures.groups.elliptic.type3.mcl;

import com.herumi.mcl.G1;
import com.herumi.mcl.Mcl;
import org.cryptimeleon.math.random.RandomGenerator;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;

public class MclGroup1Impl extends MclGroupImpl {
    protected MclGroup1ElementImpl generator = null;

    public MclGroup1Impl() {
        super();
    }

    public MclGroup1Impl(Representation repr) {
        super(repr);
    }

    @Override
    public MclGroup1ElementImpl getElement(String string) {
        return createElement(getInternalObjectFromString(string));
    }
    
    public GroupElementImpl restoreElement(Representation repr) {
        return new MclGroup1ElementImpl(this, repr);
    }

    @Override
    protected G1 getInternalObjectFromString(String str) {
        G1 result = new G1();
        result.setStr(str);
        return result;
    }

    protected MclGroup1ElementImpl createElement(G1 g1) {
        return new MclGroup1ElementImpl(this, g1);
    }

    @Override
    public MclGroup1ElementImpl getNeutralElement() {
        return getElement("0");
    }

    @Override
    public MclGroup1ElementImpl getUniformlyRandomElement() throws UnsupportedOperationException {
        return getGenerator().pow(RandomGenerator.getRandomNumber(size()));
    }

    @Override
    public MclGroup1ElementImpl getGenerator() throws UnsupportedOperationException {
        if (generator != null)
            return generator;
        G1 res = new G1();
        Mcl.hashAndMapToG1(res, "some arbitrary element".getBytes());
        return generator = createElement(res);
    }

    @Override
    public double estimateCostInvPerOp() {
        return 1.875;
    }
}
