package de.upb.crypto.math.pairings.mcl;

import com.herumi.mcl.Bn256;
import com.herumi.mcl.Fr;
import com.herumi.mcl.G1;
import de.upb.crypto.math.interfaces.structures.GroupElement;
import de.upb.crypto.math.interfaces.structures.PowProductExpression;
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
        return createElement(getInternalObjectFromString(string));
    }

    @Override
    public GroupElement getElement(Representation repr) {
        return new MclGroup1Element(this, repr);
    }

    @Override
    protected G1 getInternalObjectFromString(String str) {
        G1 res = new G1();
        res.setStr(str);
        return (G1) res;
    }

    protected MclGroup1Element createElement(G1 g1) {
        return new MclGroup1Element(this, g1);
    }

    @Override
    public MclGroup1Element getNeutralElement() {
        return getElement("0");
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
        res.hashAndMapToG1("some arbitrary element");
        return generator = createElement(res);
    }

    @Override
    public MclGroup1Element evaluate(PowProductExpression expr) throws IllegalArgumentException {
        G1 result = new G1();
        G1 intermediate = new G1();
        Fr intermediateExp = new Fr();
        result.setStr("0");

        expr = expr.dynamicOptimization();
        expr.getExpression().forEach((g, k) -> {
            intermediateExp.setStr(k.toString(10));
            Bn256.mul(intermediate, ((MclGroup1Element) g).getElement(), intermediateExp);
            Bn256.add(result, result, intermediate);
        });

        return createElement(result);
    }
}
