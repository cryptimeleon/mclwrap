package de.upb.crypto.math.pairings.mcl;

import com.herumi.mcl.G1;
import com.herumi.mcl.G2;
import com.herumi.mcl.GT;
import com.herumi.mcl.Mcl;
import de.upb.crypto.math.interfaces.structures.GroupElement;
import de.upb.crypto.math.random.interfaces.RandomGeneratorSupplier;
import de.upb.crypto.math.serialization.Representation;

public class MclGroupT extends MclGroup {
    protected MclGroupTElement generator = null;

    public MclGroupT() {
        super();
    }

    public MclGroupT(Representation repr) {
        super(repr);
    }

    @Override
    public MclGroupElement getElement(String string) {
        GT res = new GT();
        res.setStr(string);
        return createElement(res);
    }

    @Override
    protected GT getEmptyInternalObject() {
        return new GT();
    }

    protected MclGroupTElement createElement(GT GT) {
        return new MclGroupTElement(this, GT);
    }

    private MclGroupElement createElement(String str) {
        GT result = new GT();
        result.setStr(str);
        return createElement(result);
    }

    @Override
    public GroupElement getNeutralElement() {
        return createElement("1 0 0 0 0 0 0 0 0 0 0 0");
    }

    @Override
    public GroupElement getUniformlyRandomElement() throws UnsupportedOperationException {
        return getGenerator().pow(RandomGeneratorSupplier.getRnd().getRandomElement(size()));
    }

    @Override
    public GroupElement getGenerator() throws UnsupportedOperationException {
        if (generator != null)
            return generator;

        G1 g = new MclGroup1().getGenerator().getElement();
        G2 h = new MclGroup2().getGenerator().getElement();

        GT res = new GT();
        Mcl.pairing(res, g, h);

        return generator = createElement(res);
    }

    @Override
    public int estimateCostOfInvert() {
        return 200;
    }
}
