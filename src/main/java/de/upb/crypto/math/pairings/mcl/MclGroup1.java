package de.upb.crypto.math.pairings.mcl;

import com.herumi.mcl.G1;
import com.herumi.mcl.Mcl;
import de.upb.crypto.math.random.interfaces.RandomGeneratorSupplier;
import de.upb.crypto.math.serialization.Representation;

public class MclGroup1 extends MclGroup {
    protected MclGroup1Element generator = null;

    public MclGroup1() {
        super();
    }

    public MclGroup1(Representation repr) {
        super(repr);
    }

    @Override
    public MclGroup1Element getElement(String string) {
        G1 res = new G1();
        res.setStr(string);
        return createElement(res);
    }

    @Override
    protected G1 getEmptyInternalObject() {
        return new G1();
    }

    protected MclGroup1Element createElement(G1 g1) {
        return new MclGroup1Element(this, g1);
    }

    private MclGroup1Element createElement(String str) {
        G1 result = new G1();
        result.setStr(str);
        return createElement(result);
    }

    @Override
    public MclGroup1Element getNeutralElement() {
        return createElement("0");
    }

    @Override
    public MclGroup1Element getUniformlyRandomElement() throws UnsupportedOperationException {
        return getGenerator().pow(RandomGeneratorSupplier.getRnd().getRandomElement(size()));
    }

    @Override
    public MclGroup1Element getGenerator() throws UnsupportedOperationException {
        if (generator != null)
            return generator;
        G1 res = new G1();
        Mcl.hashAndMapToG1(res, "some arbitrary element".getBytes());
        return generator = createElement(res);
    }
}
