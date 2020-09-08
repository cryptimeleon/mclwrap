package de.upb.crypto.math.pairings.mcl;

import com.herumi.mcl.Fr;
import com.herumi.mcl.G2;
import com.herumi.mcl.Mcl;
import de.upb.crypto.math.interfaces.structures.Element;
import de.upb.crypto.math.interfaces.structures.group.impl.GroupElementImpl;
import de.upb.crypto.math.serialization.Representation;
import de.upb.crypto.math.structures.zn.Zn;

import java.math.BigInteger;

public class MclGroup2Element extends MclGroupElement {

    public MclGroup2Element(MclGroup2 group, Representation repr) {
        super(group, repr);
    }

    public MclGroup2Element(MclGroup2 group, G2 elem) {
        super(group, elem);
    }

    @Override
    protected G2 getElement() {
        return (G2) super.getElement();
    }

    @Override
    public MclGroup2 getStructure() {
        return (MclGroup2) super.getStructure();
    }

    @Override
    public MclGroup2Element inv() {
        G2 res = new G2();
        Mcl.neg(res, getElement());
        return getStructure().createElement(res);
    }

    @Override
    public MclGroup2Element op(GroupElementImpl e) throws IllegalArgumentException {
        G2 res = new G2();
        if (e == this)
            Mcl.dbl(res, getElement());
        else
            Mcl.add(res, getElement(), ((MclGroup2Element) e).getElement());
        return getStructure().createElement(res);
    }

    @Override
    public MclGroup2Element pow(BigInteger k) {
        return pow(Zn.valueOf(k, getStructure().size()));
    }
    
    public MclGroup2Element pow(Zn.ZnElement k) {
        G2 res = new G2();
        Fr exponent = new Fr();
        exponent.setStr(k.getInteger().toString());
        Mcl.mul(res, getElement(), exponent);
        return getStructure().createElement(res);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MclGroup2Element && getElement().equals(((MclGroup2Element) obj).getElement());
    }

    @Override
    public int hashCode() {
        return getElement().toString().hashCode();
    }
}
