package de.upb.crypto.math.pairings.mcl;

import com.herumi.mcl.Bn256;
import com.herumi.mcl.Fr;
import com.herumi.mcl.G2;
import de.upb.crypto.math.interfaces.structures.GroupElement;
import de.upb.crypto.math.interfaces.structures.PowProductExpression;
import de.upb.crypto.math.random.interfaces.RandomGeneratorSupplier;
import de.upb.crypto.math.serialization.Representation;

public class MclGroup2 extends MclGroup {
    protected MclGroup2Element generator = null;

    public MclGroup2() {
        super();
    }

    public MclGroup2(Representation repr) {
        super(repr);
    }

    @Override
    public MclGroup2Element getElement(String string) {
        return createElement(getInternalObjectFromString(string));
    }

    @Override
    public GroupElement getElement(Representation repr) {
        return new MclGroup2Element(this, repr);
    }

    @Override
    protected G2 getInternalObjectFromString(String str) {
        G2 res = new G2();
        res.setStr(str);
        return (G2) res;
    }

    protected MclGroup2Element createElement(G2 G2) {
        return new MclGroup2Element(this, G2);
    }

    @Override
    public MclGroup2Element getNeutralElement() {
        return getElement("0");
    }

    @Override
    public MclGroup2Element getUniformlyRandomElement() throws UnsupportedOperationException {
        return getGenerator().pow(RandomGeneratorSupplier.getRnd().getRandomElement(size()));
    }

    @Override
    public MclGroup2Element getGenerator() throws UnsupportedOperationException {
        if (generator != null)
            return generator;
        String xa = "12723517038133731887338407189719511622662176727675373276651903807414909099441";
        String xb = "4168783608814932154536427934509895782246573715297911553964171371032945126671";
        String ya = "13891744915211034074451795021214165905772212241412891944830863846330766296736";
        String yb = "7937318970632701341203597196594272556916396164729705624521405069090520231616";

        G2 res = new G2(xa, xb, ya, yb);

        return generator = createElement(res);
    }

    @Override
    public MclGroup2Element evaluate(PowProductExpression expr) throws IllegalArgumentException {
        G2 result = new G2();
        G2 intermediate = new G2();
        Fr intermediateExp = new Fr();
        result.setStr("0");

        expr = expr.dynamicOptimization();
        expr.getExpression().forEach((g, k) -> {
            intermediateExp.setStr(k.toString(10));
            Bn256.mul(intermediate, ((MclGroup2Element) g).getElement(), intermediateExp);
            Bn256.add(result, result, intermediate);
        });

        return createElement(result);
    }
}
