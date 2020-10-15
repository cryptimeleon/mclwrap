package de.upb.crypto.math.pairings.mcl;

import com.herumi.mcl.Fr;
import com.herumi.mcl.G1;
import com.herumi.mcl.Mcl;
import de.upb.crypto.math.interfaces.structures.group.impl.GroupElementImpl;
import de.upb.crypto.math.pairings.debug.DebugGroupImpl;
import de.upb.crypto.math.serialization.Representation;
import de.upb.crypto.math.structures.zn.Zn;

import java.math.BigInteger;
import java.util.Objects;

public class MclGroup1ElementImpl extends MclGroupElementImpl {

    public MclGroup1ElementImpl(MclGroup1Impl group, Representation repr) {
        super(group, repr);
    }

    public MclGroup1ElementImpl(MclGroup1Impl group, G1 elem) {
        super(group, elem);
    }

    @Override
    protected G1 getElement() {
        return (G1) super.getElement();
    }

    @Override
    public MclGroup1Impl getStructure() {
        return (MclGroup1Impl) super.getStructure();
    }

    @Override
    public MclGroup1ElementImpl inv() {
        G1 res = new G1();
        Mcl.neg(res, getElement());
        return getStructure().createElement(res);
    }

    @Override
    public MclGroup1ElementImpl op(GroupElementImpl e) throws IllegalArgumentException {
        G1 res = new G1();
        if (e == this)
            Mcl.dbl(res, getElement());
        else
            Mcl.add(res, getElement(), ((MclGroup1ElementImpl) e).getElement());
        return getStructure().createElement(res);
    }

    @Override
    public MclGroup1ElementImpl pow(BigInteger k) {
        return pow(Zn.valueOf(k, getStructure().size()));
    }
    
    public MclGroup1ElementImpl pow(Zn.ZnElement k) {
        G1 res = new G1();
        Fr exponent = new Fr(k.getInteger().toString());
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
        MclGroup1ElementImpl that = (MclGroup1ElementImpl) other;
        return getElement().equals(that.getElement()) // need to use this method since G1 does not override equals
                && Objects.equals(super.group, that.group);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
