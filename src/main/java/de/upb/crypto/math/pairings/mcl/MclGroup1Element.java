package de.upb.crypto.math.pairings.mcl;

import com.herumi.mcl.Fr;
import com.herumi.mcl.G1;
import com.herumi.mcl.Mcl;
import de.upb.crypto.math.interfaces.structures.Element;
import de.upb.crypto.math.serialization.Representation;
import de.upb.crypto.math.structures.zn.Zn;

import java.math.BigInteger;

public class MclGroup1Element extends MclGroupElement {

    public MclGroup1Element(MclGroup1 group, Representation repr) {
        super(group, repr);
    }

    public MclGroup1Element(MclGroup1 group, G1 elem) {
        super(group, elem);
    }

    @Override
    protected G1 getElement() {
        return (G1) super.getElement();
    }

    @Override
    public MclGroup1 getStructure() {
        return (MclGroup1) super.getStructure();
    }

    @Override
    public MclGroup1Element inv() {
        G1 res = new G1();
        Mcl.neg(res, getElement());
        return getStructure().createElement(res);
    }

    @Override
    public MclGroup1Element op(Element e) throws IllegalArgumentException {
        G1 res = new G1();
        if (e == this)
            Mcl.dbl(res, getElement());
        else
            Mcl.add(res, getElement(), ((MclGroup1Element) e).getElement());
        return getStructure().createElement(res);
    }

    @Override
    public MclGroup1Element pow(BigInteger k) {
        return pow(Zn.valueOf(k, getStructure().size()));
    }

    @Override
    public MclGroup1Element pow(Zn.ZnElement k) {
        G1 res = new G1();
        Fr exponent = new Fr();
        exponent.setStr(k.getInteger().toString());
        Mcl.mul(res, getElement(), exponent);
        return getStructure().createElement(res);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MclGroup1Element && getElement().equals(((MclGroup1Element) obj).getElement());
    }

    @Override
    public int hashCode() {
        return getElement().toString().hashCode();
    }
}
