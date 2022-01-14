package org.cryptimeleon.mclwrap.bn254;

import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.serialization.StringRepresentation;
import org.cryptimeleon.math.structures.groups.GroupImpl;

import java.math.BigInteger;
import java.util.Optional;

abstract class MclGroupImpl implements GroupImpl {
    public final MclBilinearGroup.GroupChoice groupChoice;

    public MclGroupImpl(MclBilinearGroup.GroupChoice groupChoice) {
        this.groupChoice = groupChoice;
        MclBilinearGroupImpl.init(groupChoice);
    }

    public MclGroupImpl(Representation repr) {
        this(MclBilinearGroup.GroupChoice.valueOf(repr.str().get()));
    }

    @Override
    public BigInteger size() throws UnsupportedOperationException {
        switch (groupChoice) {
            case BN254:
                return new BigInteger("16798108731015832284940804142231733909759579603404752749028378864165570215949");
            case BLS12_381:
                return new BigInteger("73eda753299d7d483339d80809a1d80553bda402fffe5bfeffffffff00000001", 16);
        }

        throw new IllegalStateException("Unknown size");
    }

    @Override
    public boolean hasPrimeSize() throws UnsupportedOperationException {
        return true;
    }

    protected abstract MclGroupElementImpl getElement(String string);

    /**
     * Outputs an object of type mcl.G1, mcl.G2, or mcl.GT
     */
    protected abstract Object getInternalObjectFromString(String str);

    @Override
    public Optional<Integer> getUniqueByteLength() {
        return Optional.empty(); //TODO replace with actual value for better performance
    }

    @Override
    public boolean isCommutative() {
        return true;
    }

    @Override
    public Representation getRepresentation() {
        return new StringRepresentation(groupChoice.name());
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.getClass().equals(obj.getClass()) && ((MclGroupImpl) obj).groupChoice == groupChoice;
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
