package de.upb.crypto.math.pairings.mcl;

import com.herumi.mcl.G1;
import com.herumi.mcl.G2;
import com.herumi.mcl.GT;
import com.herumi.mcl.Mcl;
import de.upb.crypto.math.interfaces.structures.group.impl.GroupElementImpl;
import de.upb.crypto.math.random.interfaces.RandomGeneratorSupplier;
import de.upb.crypto.math.serialization.Representation;

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
        GT res = new GT();
        res.setStr(string);
        return createElement(res);
    }

    @Override
    protected GT getEmptyInternalObject() {
        return new GT();
    }

    protected MclGroupTElementImpl createElement(GT GT) {
        return new MclGroupTElementImpl(this, GT);
    }

    private MclGroupElementImpl createElement(String str) {
        GT result = new GT();
        result.setStr(str);
        return createElement(result);
    }

    @Override
    public GroupElementImpl getNeutralElement() {
        return createElement("1 0 0 0 0 0 0 0 0 0 0 0");
    }

    @Override
    public GroupElementImpl getUniformlyRandomElement() throws UnsupportedOperationException {
        return getGenerator().pow(RandomGeneratorSupplier.getRnd().getRandomElement(size()));
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
}
