package de.upb.crypto.math.pairings.mcl;

import com.herumi.mcl.Fp;
import com.herumi.mcl.Fr;
import com.herumi.mcl.G2;
import com.herumi.mcl.Mcl;
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
        G2 res = new G2();
        res.setStr(string);
        return createElement(res);
    }

    @Override
    protected G2 getEmptyInternalObject() {
        return new G2();
    }

    protected MclGroup2Element createElement(G2 G2) {
        return new MclGroup2Element(this, G2);
    }

    private MclGroup2Element createElement(String str) {
        G2 result = new G2();
        result.setStr(str);
        return createElement(result);
    }

    @Override
    public MclGroup2Element getNeutralElement() {
        return createElement("0");
    }

    @Override
    public MclGroup2Element getUniformlyRandomElement() throws UnsupportedOperationException {
        return getGenerator().pow(RandomGeneratorSupplier.getRnd().getRandomElement(size()));
    }

    @Override
    public MclGroup2Element getGenerator() throws UnsupportedOperationException {
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
    public MclGroup2Element evaluate(PowProductExpression expr) throws IllegalArgumentException {
        G2 result = new G2();
        G2 intermediate = new G2();
        Fr intermediateExp = new Fr();
        result.setStr("0");

        expr = expr.dynamicOptimization();
        expr.getExpression().forEach((g, k) -> {
            intermediateExp.setStr(k.toString(10));
            Mcl.mul(intermediate, ((MclGroup2Element) g).getElement(), intermediateExp);
            Mcl.add(result, result, intermediate);
        });

        return createElement(result);
    }
}
