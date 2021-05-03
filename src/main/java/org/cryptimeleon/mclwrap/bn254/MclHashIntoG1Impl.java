package org.cryptimeleon.mclwrap.bn254;

import com.herumi.mcl.G1;
import com.herumi.mcl.Mcl;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.groups.mappings.impl.HashIntoGroupImpl;

import java.util.Objects;

class MclHashIntoG1Impl implements HashIntoGroupImpl {
    protected MclGroup1Impl group;

    public MclHashIntoG1Impl(MclGroup1Impl group) {
        this.group = group;
    }

    public MclHashIntoG1Impl(Representation repr) {
        group = new MclGroup1Impl(repr);
    }

    @Override
    public GroupElementImpl hashIntoGroupImpl(byte[] x) {
        G1 result = new G1();
        Mcl.hashAndMapToG1(result, x);
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
        MclHashIntoG1Impl that = (MclHashIntoG1Impl) o;
        return group.equals(that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group);
    }
}
