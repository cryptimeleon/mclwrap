package org.cryptimeleon.mclwrap.bn254;

import com.herumi.mcl.Fr;
import com.herumi.mcl.G2;
import com.herumi.mcl.Mcl;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.rings.zn.Zn;

import java.math.BigInteger;
import java.util.Objects;

class MclGroup2ElementImpl extends MclGroupElementImpl {

    public MclGroup2ElementImpl(MclGroup2Impl group, Representation repr) {
        super(group, repr);
    }

    public MclGroup2ElementImpl(MclGroup2Impl group, G2 elem) {
        super(group, elem);
    }

    @Override
    protected G2 getElement() {
        return (G2) super.getElement();
    }

    @Override
    public MclGroup2Impl getStructure() {
        return (MclGroup2Impl) super.getStructure();
    }

    @Override
    public MclGroup2ElementImpl inv() {
        G2 res = new G2();
        Mcl.neg(res, getElement());
        return getStructure().createElement(res);
    }

    @Override
    public MclGroup2ElementImpl op(GroupElementImpl e) throws IllegalArgumentException {
        G2 res = new G2();
        if (e == this)
            Mcl.dbl(res, getElement());
        else
            Mcl.add(res, getElement(), ((MclGroup2ElementImpl) e).getElement());
        return getStructure().createElement(res);
    }

    @Override
    public MclGroup2ElementImpl pow(BigInteger k) {
        return pow(Zn.valueOf(k, getStructure().size()));
    }
    
    public MclGroup2ElementImpl pow(Zn.ZnElement k) {
        G2 res = new G2();
        Fr exponent = new Fr(k.asInteger().toString());
        Mcl.mul(res, getElement(), exponent);
        return getStructure().createElement(res);
    }

    @Override
    public boolean isNeutralElement() {
        return getElement().isZero();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || this.getClass() != other.getClass()) return false;
        MclGroup2ElementImpl that = (MclGroup2ElementImpl) other;
        return getElement().equals(that.getElement()) // need to use this method since G2 does not override equals
                && Objects.equals(super.group, that.group);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
