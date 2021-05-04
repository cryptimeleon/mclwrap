package org.cryptimeleon.mclwrap.bn254;

import com.herumi.mcl.G2;
import com.herumi.mcl.Mcl;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.groups.mappings.impl.HashIntoGroupImpl;

import java.util.Objects;

class MclHashIntoG2Impl implements HashIntoGroupImpl {
    protected MclGroup2Impl group;

    public MclHashIntoG2Impl(MclGroup2Impl group) {
        this.group = group;
    }

    public MclHashIntoG2Impl(Representation repr) {
        group = new MclGroup2Impl(repr);
    }

    @Override
    public GroupElementImpl hashIntoGroupImpl(byte[] x) {
        G2 result = new G2();
        Mcl.hashAndMapToG2(result, x);
        return group.createElement(result);
    }

    @Override
    public Representation getRepresentation() {
        return group.getRepresentation();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MclHashIntoG2Impl that = (MclHashIntoG2Impl) o;
        return group.equals(that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group);
    }
}
