package de.upb.crypto.math.pairings.mcl;

import com.herumi.mcl.G1;
import com.herumi.mcl.G2;
import com.herumi.mcl.Mcl;
import de.upb.crypto.math.interfaces.mappings.impl.HashIntoGroupImpl;
import de.upb.crypto.math.interfaces.structures.group.impl.GroupElementImpl;
import de.upb.crypto.math.serialization.Representation;

public class MclHashIntoG2Impl implements HashIntoGroupImpl {
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
}