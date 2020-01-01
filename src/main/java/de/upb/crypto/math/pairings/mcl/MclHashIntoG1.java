package de.upb.crypto.math.pairings.mcl;

import com.herumi.mcl.G1;
import com.herumi.mcl.Mcl;
import de.upb.crypto.math.interfaces.hash.HashIntoStructure;
import de.upb.crypto.math.serialization.Representation;

public class MclHashIntoG1 implements HashIntoStructure {
    protected MclGroup1 group;

    public MclHashIntoG1(MclGroup1 group) {
        this.group = group;
    }

    public MclHashIntoG1(Representation repr) {
        group = new MclGroup1(repr);
    }

    @Override
    public MclGroup1Element hashIntoStructure(byte[] x) {
        G1 result = new G1();
        Mcl.hashAndMapToG1(result, x);
        return group.createElement(result);
    }

    @Override
    public Representation getRepresentation() {
        return group.getRepresentation();
    }
}
