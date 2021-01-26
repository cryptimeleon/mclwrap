package de.upb.crypto.math.pairings.mcl;

import com.herumi.mcl.G1;
import com.herumi.mcl.Mcl;
import de.upb.crypto.math.serialization.Representation;
import de.upb.crypto.math.structures.groups.GroupElementImpl;
import de.upb.crypto.math.structures.groups.mappings.impl.HashIntoGroupImpl;

public class MclHashIntoG1Impl implements HashIntoGroupImpl {
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
}
