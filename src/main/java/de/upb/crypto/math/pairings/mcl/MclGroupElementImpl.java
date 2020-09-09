package de.upb.crypto.math.pairings.mcl;

import de.upb.crypto.math.interfaces.hash.ByteAccumulator;
import de.upb.crypto.math.interfaces.structures.group.impl.GroupElementImpl;
import de.upb.crypto.math.interfaces.structures.group.impl.GroupImpl;
import de.upb.crypto.math.serialization.Representation;
import de.upb.crypto.math.serialization.StringRepresentation;

import java.util.Objects;

public abstract class MclGroupElementImpl implements GroupElementImpl {
    protected MclGroupImpl group;
    protected Object element;

    public MclGroupElementImpl(MclGroupImpl group, Object element) {
        this.group = group;
        this.element = element;
    }

    public MclGroupElementImpl(MclGroupImpl group, Representation repr) {
        this(group, group.getElement(repr.str().get()));
    }

    /**
     * Returns this element as an object of mcl.G1, mcl.G2, or mcl.GT
     */
    protected Object getElement() {
        return element;
    }

    @Override
    public GroupImpl getStructure() {
        return group;
    }

    @Override
    public ByteAccumulator updateAccumulator(ByteAccumulator accumulator) {
        accumulator.escapeAndAppend(getElement().toString());
        return accumulator;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || this.getClass() != other.getClass()) return false;
        MclGroup1ElementImpl that = (MclGroup1ElementImpl) other;
        return getElement().equals(that.getElement()) // need to use this method since G1 does not override equals
                && Objects.equals(group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, element.toString());
    }

    @Override
    public Representation getRepresentation() {
        return new StringRepresentation(getElement().toString());
    }

    @Override
    public String toString() {
        return getElement().toString();
    }
}
