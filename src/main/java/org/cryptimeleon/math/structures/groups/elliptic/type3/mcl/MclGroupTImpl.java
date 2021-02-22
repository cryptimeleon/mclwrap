package org.cryptimeleon.math.structures.groups.elliptic.type3.mcl;

import com.herumi.mcl.G1;
import com.herumi.mcl.G2;
import com.herumi.mcl.GT;
import com.herumi.mcl.Mcl;
import org.cryptimeleon.math.random.RandomGenerator;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;

public class MclGroupTImpl extends MclGroupImpl {
    protected MclGroupTElementImpl generator = null;

    public MclGroupTImpl() {
        super();
    }

    public MclGroupTImpl(Representation repr) {
        super(repr);
    }

    @Override
    public MclGroupElementImpl getElement(String string) {
        return createElement(getInternalObjectFromString(string));
    }

    @Override
    public GroupElementImpl getElement(Representation repr) {
        return new MclGroupTElementImpl(this, repr);
    }

    @Override
    protected GT getInternalObjectFromString(String str) {
        GT result = new GT();
        result.setStr(str);
        return result;
    }

    protected MclGroupTElementImpl createElement(GT GT) {
        return new MclGroupTElementImpl(this, GT);
    }


    @Override
    public GroupElementImpl getNeutralElement() {
        return getElement("1 0 0 0 0 0 0 0 0 0 0 0");
    }

    @Override
    public GroupElementImpl getUniformlyRandomElement() throws UnsupportedOperationException {
        return getGenerator().pow(RandomGenerator.getRandomNumber(size()));
    }

    @Override
    public GroupElementImpl getGenerator() throws UnsupportedOperationException {
        if (generator != null)
            return generator;

        G1 g = new MclGroup1Impl().getGenerator().getElement();
        G2 h = new MclGroup2Impl().getGenerator().getElement();

        GT res = new GT();
        Mcl.pairing(res, g, h);

        return generator = createElement(res);
    }

    @Override
    public double estimateCostInvPerOp() {
        return 3.4;
    }
}
