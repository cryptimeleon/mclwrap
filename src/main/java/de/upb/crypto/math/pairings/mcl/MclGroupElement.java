package de.upb.crypto.math.pairings.mcl;

import de.upb.crypto.math.interfaces.hash.ByteAccumulator;
import de.upb.crypto.math.interfaces.structures.Group;
import de.upb.crypto.math.interfaces.structures.GroupElement;
import de.upb.crypto.math.serialization.Representation;
import de.upb.crypto.math.serialization.StringRepresentation;

import java.util.Objects;

public abstract class MclGroupElement implements GroupElement {
    protected MclGroup group;
    protected Object element;

    public MclGroupElement(MclGroup group, Object element) {
        this.group = group;
        this.element = element;
    }

    public MclGroupElement(MclGroup group, Representation repr) {
        this(group, group.getElement(repr.str().get()));
    }

    /**
     * Returns this element as an object of mcl.G1, mcl.G2, or mcl.GT
     */
    protected Object getElement() {
        return element;
    }

    @Override
    public Group getStructure() {
        return group;
    }

    @Override
    public ByteAccumulator updateAccumulator(ByteAccumulator accumulator) {
        accumulator.escapeAndAppend(getElement().toString());
        return accumulator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MclGroupElement)) return false;
        MclGroupElement that = (MclGroupElement) o;
        return Objects.equals(group, that.group) &&
                Objects.equals(element, that.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, element);
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
