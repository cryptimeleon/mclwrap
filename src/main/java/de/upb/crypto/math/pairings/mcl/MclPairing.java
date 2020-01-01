package de.upb.crypto.math.pairings.mcl;

import com.herumi.mcl.Fr;
import com.herumi.mcl.G1;
import com.herumi.mcl.GT;
import com.herumi.mcl.Mcl;
import de.upb.crypto.math.interfaces.mappings.BilinearMap;
import de.upb.crypto.math.interfaces.mappings.PairingProductExpression;
import de.upb.crypto.math.interfaces.structures.GroupElement;
import de.upb.crypto.math.serialization.Representation;

import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;

public class MclPairing implements BilinearMap {

    private MclBilinearGroup bilinearGroup;

    public MclPairing(MclBilinearGroup bilinearGroup) {
        this.bilinearGroup = bilinearGroup;
    }

    public MclPairing(Representation repr) {
        this(new MclBilinearGroup(repr));
    }

    @Override
    public MclGroup1 getG1() {
        return bilinearGroup.getG1();
    }

    @Override
    public MclGroup2 getG2() {
        return bilinearGroup.getG2();
    }

    @Override
    public MclGroupT getGT() {
        return bilinearGroup.getGT();
    }

    @Override
    public GroupElement apply(GroupElement g1, GroupElement g2, BigInteger exponent) {
        GT result = new GT();
        G1 g1Exponentiated = new G1();
        exponent = exponent.mod(bilinearGroup.getG1().size());

        Fr exp = new Fr();
        exp.setStr(exponent.toString(10));
        Mcl.mul(g1Exponentiated, ((MclGroup1Element) g1).getElement(), exp);
        Mcl.pairing(result, g1Exponentiated, ((MclGroup2Element) g2).getElement());
        return getGT().createElement(result);
    }

    @Override
    public GroupElement evaluate(PairingProductExpression expr) {
        GroupElement result = getGT().getNeutralElement();

        for (Map.Entry<PairingProductExpression.AtomicExpression, BigInteger> x : expr.getExpression().entrySet()) {
            result = result.op(apply(x.getKey().getGExpression().pow(x.getValue()).evaluate(), x.getKey().getH(), BigInteger.ONE));
        }
        return result;
        /*//expr = expr.dynamicOptimization();
        GT result = new GT();
        result.setStr("1 0 0 0 0 0 0 0 0 0 0 0");

        G1 lhs = new G1();
        G2 rhs = new G2();
        G1 tmp = new G1();
        G2 tmp2 = new G2();
        GT intermediateT = new GT(); //one pairing result e(g,h)^x (result is product of those)
        Fr tmpExponent = new Fr();

        expr.forEach((base, exponent) -> { //for each e(g,h)^x, do:
            lhs.setStr("0");
            base.getGExpression().pow(exponent).forEach((gbase, gexp) -> { //compute g^x
                tmpExponent.setStr(gexp.toString(10));
                Mcl.mul(tmp, ((MclGroup1Element) gbase).getElement(), tmpExponent);
                Mcl.add(lhs, lhs, tmp);
            });

            rhs.setStr("0");
            base.getHExpression().pow(exponent).forEach((hbase, hexp) -> { //compute h
                tmpExponent.setStr(hexp.toString(10));
                Bn256.mul(tmp2, ((MclGroup2Element) hbase).getElement(), tmpExponent);
                Bn256.add(rhs, rhs, tmp2);
            });

            //Pair g^x and h
            Bn256.pairing(intermediateT, lhs, rhs);
            Bn256.mul(result, result, intermediateT);
        });
        return getGT().createElement(result);*/
    }

    @Override
    public boolean isSymmetric() {
        return false;
    }

    @Override
    public Representation getRepresentation() {
        return bilinearGroup.getRepresentation();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MclPairing that = (MclPairing) o;
        return Objects.equals(bilinearGroup, that.bilinearGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bilinearGroup);
    }
}
