package de.upb.crypto.math.pairings.mcl;

import com.herumi.mcl.G1;
import de.upb.crypto.math.interfaces.hash.HashIntoStructure;
import de.upb.crypto.math.serialization.Representation;

import java.util.Base64;

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
        result.hashAndMapToG1(Base64.getEncoder().encodeToString(x));
        return group.createElement(result);
    }

    @Override
    public Representation getRepresentation() {
        return group.getRepresentation();
    }
}
