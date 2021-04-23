package org.cryptimeleon.mclwrap.bn254;

import com.herumi.mcl.Fr;
import com.herumi.mcl.GT;
import com.herumi.mcl.Mcl;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.rings.zn.Zn;

import java.math.BigInteger;
import java.util.Objects;

class MclGroupTElementImpl extends MclGroupElementImpl {

    public MclGroupTElementImpl(MclGroupTImpl group, Representation repr) {
        super(group, repr);
    }

    public MclGroupTElementImpl(MclGroupTImpl group, GT elem) {
        super(group, elem);
    }

    @Override
    protected GT getElement() {
        return (GT) super.getElement();
    }

    @Override
    public MclGroupTImpl getStructure() {
        return (MclGroupTImpl) super.getStructure();
    }

    @Override
    public MclGroupTElementImpl inv() {
        GT res = new GT();
        Mcl.inv(res, getElement());
        return getStructure().createElement(res);
    }

    @Override
    public MclGroupTElementImpl op(GroupElementImpl e) throws IllegalArgumentException {
        GT res = new GT();
        Mcl.mul(res, getElement(), ((MclGroupTElementImpl) e).getElement());
        return getStructure().createElement(res);
    }

    @Override
    public MclGroupTElementImpl pow(BigInteger k) {
        return pow(Zn.valueOf(k, getStructure().size()));
    }

    public MclGroupTElementImpl pow(Zn.ZnElement k) {
        GT res = new GT();
        Fr exponent = new Fr(k.getInteger().toString());
        Mcl.pow(res, getElement(), exponent);
        return getStructure().createElement(res);
    }

    @Override
    public boolean isNeutralElement() {
        return getElement().isOne();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || this.getClass() != other.getClass()) return false;
        MclGroupTElementImpl that = (MclGroupTElementImpl) other;
        return getElement().equals(that.getElement()) // need to use this method since GT does not override equals
                && Objects.equals(super.group, that.group);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
