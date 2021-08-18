package org.cryptimeleon.mclwrap.bn254;

import org.cryptimeleon.math.serialization.BigIntegerRepresentation;
import org.cryptimeleon.math.serialization.ListRepresentation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;


public class MembershipTest {
    @Test
    public void testOnCurve() {
        MclBilinearGroupImpl bilGroupImpl = new MclBilinearGroupImpl();
        MclGroup2Impl groupG2Impl = bilGroupImpl.getG2();
        BigInteger[] coords = new BigInteger[5];
        coords[0] = new BigInteger("1");
        coords[1] = new BigInteger("10599278435418681622810665769941266812667319675599408820180893057347165702478");
        coords[2] = new BigInteger("5582223682282056256096077371797887602975186621861422240082223070902431790978");
        coords[3] = new BigInteger("15460700595750929643941411310687174139843649931729260652222255335996967235678");
        coords[4] = new BigInteger("16696288540357646523389079979160608706340150042719992903313950531521707534741");
        coords[1] = coords[1].add(BigInteger.ONE);
        ListRepresentation newListRepr = new ListRepresentation();
        for (BigInteger bigInt : coords) {
            newListRepr.add(new BigIntegerRepresentation(bigInt));
        }
        Assertions.assertThrows(RuntimeException.class, () -> {
            new MclGroup2ElementImpl(groupG2Impl, newListRepr);
        });
    }
}
