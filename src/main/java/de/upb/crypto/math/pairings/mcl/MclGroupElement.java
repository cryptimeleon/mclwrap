package de.upb.crypto.math.pairings.mcl;

import de.upb.crypto.math.interfaces.hash.ByteAccumulator;
import de.upb.crypto.math.interfaces.structures.Group;
import de.upb.crypto.math.interfaces.structures.GroupElement;
import de.upb.crypto.math.serialization.Representation;
import de.upb.crypto.math.serialization.StringRepresentation;

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
    public Representation getRepresentation() {
        return new StringRepresentation(getElement().toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !this.getClass().equals(obj.getClass()))
            return false;
        return this.op((MclGroupElement) obj).isNeutralElement();
    }

    @Override
    public String toString() {
        return getElement().toString();
    }
}
