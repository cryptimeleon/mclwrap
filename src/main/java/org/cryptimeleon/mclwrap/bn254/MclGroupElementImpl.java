package org.cryptimeleon.mclwrap.bn254;

import org.cryptimeleon.math.hash.ByteAccumulator;
import org.cryptimeleon.math.serialization.BigIntegerRepresentation;
import org.cryptimeleon.math.serialization.ListRepresentation;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.groups.GroupImpl;

import java.math.BigInteger;
import java.util.Objects;
import java.util.stream.Collectors;

abstract class MclGroupElementImpl implements GroupElementImpl {
    protected MclGroupImpl group;
    protected Object element;

    public MclGroupElementImpl(MclGroupImpl group, Object element) {
        this.group = group;
        this.element = element;
    }

    public MclGroupElementImpl(MclGroupImpl group, Representation repr) {
        this.group = group;
        this.element = group.getInternalObjectFromString(repr.list().stream().map(r -> r.bigInt().get().toString()).collect(Collectors.joining(" ")));
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
        return getElement().equals(that.getElement()) // need to use this method since Gx does not override equals
                && Objects.equals(group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, element.toString());
    }

    @Override
    public Representation getRepresentation() {
        ListRepresentation result = new ListRepresentation();
        String repr = getElement().toString(); //bunch of decimal numbers separated by spaces.
        for (String str : repr.split(" "))
            result.put(new BigIntegerRepresentation(new BigInteger(str)));

        return result;
    }

    @Override
    public String toString() {
        return getElement().toString();
    }
}
