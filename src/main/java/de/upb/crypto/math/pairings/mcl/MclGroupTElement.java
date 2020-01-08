package de.upb.crypto.math.pairings.mcl;

import com.herumi.mcl.Fr;
import com.herumi.mcl.GT;
import com.herumi.mcl.Mcl;
import de.upb.crypto.math.interfaces.structures.Element;
import de.upb.crypto.math.serialization.Representation;
import de.upb.crypto.math.structures.zn.Zn;

import java.math.BigInteger;

public class MclGroupTElement extends MclGroupElement {

    public MclGroupTElement(MclGroupT group, Representation repr) {
        super(group, repr);
    }

    public MclGroupTElement(MclGroupT group, GT elem) {
        super(group, elem);
    }

    @Override
    protected GT getElement() {
        return (GT) super.getElement();
    }

    @Override
    public MclGroupT getStructure() {
        return (MclGroupT) super.getStructure();
    }

    @Override
    public MclGroupTElement inv() {
        GT res = new GT();
        //Mcl.inv(res, getElement());
        //return getStructure().createElement(res);
        Fr minusOne = new Fr("-1");
        Mcl.pow(res, getElement(), minusOne);
        return getStructure().createElement(res);
        //return pow(getStructure().size().subtract(BigInteger.ONE)); //TODO change!
    }

    @Override
    public MclGroupTElement op(Element e) throws IllegalArgumentException {
        GT res = new GT();
        Mcl.mul(res, getElement(), ((MclGroupTElement) e).getElement());
        return getStructure().createElement(res);
    }

    @Override
    public MclGroupTElement pow(BigInteger k) {
        return pow(Zn.valueOf(k, getStructure().size()));
    }

    @Override
    public MclGroupTElement pow(Zn.ZnElement k) {
        GT res = new GT();
        Fr exponent = new Fr();
        exponent.setStr(k.getInteger().toString());
        Mcl.pow(res, getElement(), exponent);
        return getStructure().createElement(res);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MclGroupTElement && getElement().equals(((MclGroupTElement) obj).getElement());
    }

    @Override
    public int hashCode() {
        return getElement().toString().hashCode();
    }
}
