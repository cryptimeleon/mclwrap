package org.cryptimeleon.math.structures.groups.elliptic.type3.mcl;

import com.herumi.mcl.Fr;
import com.herumi.mcl.G1;
import com.herumi.mcl.GT;
import com.herumi.mcl.Mcl;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearMapImpl;

import java.math.BigInteger;
import java.util.Objects;

class MclPairing implements BilinearMapImpl {

    private MclBilinearGroupImpl bilinearGroup;

    public MclPairing(MclBilinearGroupImpl bilinearGroup) {
        this.bilinearGroup = bilinearGroup;
    }

    public MclPairing(Representation repr) {
        this(new MclBilinearGroupImpl(repr));
    }

    @Override
    public GroupElementImpl apply(GroupElementImpl g1, GroupElementImpl g2, BigInteger exponent) {
        GT result = new GT();
        G1 g1Exponentiated = new G1();
        exponent = exponent.mod(bilinearGroup.getG1().size());

        Fr exp = new Fr();
        exp.setStr(exponent.toString(10));
        Mcl.mul(g1Exponentiated, ((MclGroup1ElementImpl) g1).getElement(), exp);
        Mcl.pairing(result, g1Exponentiated, ((MclGroup2ElementImpl) g2).getElement());
        return bilinearGroup.getGT().createElement(result);
    }

    @Override
    public boolean isSymmetric() {
        return false;
    }

    public Representation getRepresentation() {
        return bilinearGroup.getRepresentation();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MclPairing that = (MclPairing) o;
        return Objects.equals(bilinearGroup, that.bilinearGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bilinearGroup);
    }
}
