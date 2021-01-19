package de.upb.crypto.math.pairings.mcl;

import de.upb.crypto.math.interfaces.structures.group.impl.GroupImpl;
import de.upb.crypto.math.serialization.Representation;
import de.upb.crypto.math.serialization.StringRepresentation;

import java.math.BigInteger;
import java.util.Optional;

public abstract class MclGroupImpl implements GroupImpl {

    public MclGroupImpl() {
        MclBilinearGroupImpl.init(true);
    }

    public MclGroupImpl(Representation repr) {
        this();
        //Nothing to do
    }

    @Override
    public BigInteger size() throws UnsupportedOperationException {
        return new BigInteger("16798108731015832284940804142231733909759579603404752749028378864165570215949");
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
        return new StringRepresentation("BN254");
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.getClass().equals(obj.getClass());
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
