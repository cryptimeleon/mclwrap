/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.8
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.herumi.mcl;

public class Bn256JNI {
    public final static native void SystemInit();

    public final static native void neg__SWIG_0(long jarg1, Fr jarg1_, long jarg2, Fr jarg2_);

    public final static native void add__SWIG_0(long jarg1, Fr jarg1_, long jarg2, Fr jarg2_, long jarg3, Fr jarg3_);

    public final static native void sub__SWIG_0(long jarg1, Fr jarg1_, long jarg2, Fr jarg2_, long jarg3, Fr jarg3_);

    public final static native void mul__SWIG_0(long jarg1, Fr jarg1_, long jarg2, Fr jarg2_, long jarg3, Fr jarg3_);

    public final static native void mul__SWIG_1(long jarg1, G1 jarg1_, long jarg2, G1 jarg2_, long jarg3, Fr jarg3_);

    public final static native void mul__SWIG_2(long jarg1, G2 jarg1_, long jarg2, G2 jarg2_, long jarg3, Fr jarg3_);

    public final static native void div(long jarg1, Fr jarg1_, long jarg2, Fr jarg2_, long jarg3, Fr jarg3_);

    public final static native void pow(long jarg1, GT jarg1_, long jarg2, GT jarg2_, long jarg3, Fr jarg3_);

    public final static native long new_Fr__SWIG_0();

    public final static native long new_Fr__SWIG_1(long jarg1, Fr jarg1_);

    public final static native long new_Fr__SWIG_2(int jarg1);

    public final static native long new_Fr__SWIG_3(String jarg1);

    public final static native boolean Fr_equals(long jarg1, Fr jarg1_, long jarg2, Fr jarg2_);

    public final static native void Fr_setStr(long jarg1, Fr jarg1_, String jarg2);

    public final static native void Fr_setInt(long jarg1, Fr jarg1_, int jarg2);

    public final static native void Fr_clear(long jarg1, Fr jarg1_);

    public final static native void Fr_setRand(long jarg1, Fr jarg1_);

    public final static native String Fr_toString(long jarg1, Fr jarg1_);

    public final static native void delete_Fr(long jarg1);

    public final static native void neg__SWIG_1(long jarg1, G1 jarg1_, long jarg2, G1 jarg2_);

    public final static native void dbl__SWIG_0(long jarg1, G1 jarg1_, long jarg2, G1 jarg2_);

    public final static native void add__SWIG_1(long jarg1, G1 jarg1_, long jarg2, G1 jarg2_, long jarg3, G1 jarg3_);

    public final static native void sub__SWIG_1(long jarg1, G1 jarg1_, long jarg2, G1 jarg2_, long jarg3, G1 jarg3_);

    public final static native void pairing(long jarg1, GT jarg1_, long jarg2, G1 jarg2_, long jarg3, G2 jarg3_);

    public final static native long new_G1__SWIG_0();

    public final static native long new_G1__SWIG_1(long jarg1, G1 jarg1_);

    public final static native long new_G1__SWIG_2(String jarg1, String jarg2);

    public final static native boolean G1_equals(long jarg1, G1 jarg1_, long jarg2, G1 jarg2_);

    public final static native void G1_set(long jarg1, G1 jarg1_, String jarg2, String jarg3);

    public final static native void G1_hashAndMapToG1(long jarg1, G1 jarg1_, String jarg2);

    public final static native void G1_clear(long jarg1, G1 jarg1_);

    public final static native void G1_setStr(long jarg1, G1 jarg1_, String jarg2);

    public final static native String G1_toString(long jarg1, G1 jarg1_);

    public final static native void delete_G1(long jarg1);

    public final static native void neg__SWIG_2(long jarg1, G2 jarg1_, long jarg2, G2 jarg2_);

    public final static native void dbl__SWIG_1(long jarg1, G2 jarg1_, long jarg2, G2 jarg2_);

    public final static native void add__SWIG_2(long jarg1, G2 jarg1_, long jarg2, G2 jarg2_, long jarg3, G2 jarg3_);

    public final static native void sub__SWIG_2(long jarg1, G2 jarg1_, long jarg2, G2 jarg2_, long jarg3, G2 jarg3_);

    public final static native long new_G2__SWIG_0();

    public final static native long new_G2__SWIG_1(long jarg1, G2 jarg1_);

    public final static native long new_G2__SWIG_2(String jarg1, String jarg2, String jarg3, String jarg4);

    public final static native boolean G2_equals(long jarg1, G2 jarg1_, long jarg2, G2 jarg2_);

    public final static native void G2_set(long jarg1, G2 jarg1_, String jarg2, String jarg3, String jarg4,
                                           String jarg5);

    public final static native void G2_clear(long jarg1, G2 jarg1_);

    public final static native void G2_setStr(long jarg1, G2 jarg1_, String jarg2);

    public final static native String G2_toString(long jarg1, G2 jarg1_);

    public final static native void delete_G2(long jarg1);

    public final static native void mul__SWIG_3(long jarg1, GT jarg1_, long jarg2, GT jarg2_, long jarg3, GT jarg3_);

    public final static native long new_GT__SWIG_0();

    public final static native long new_GT__SWIG_1(long jarg1, GT jarg1_);

    public final static native boolean GT_equals(long jarg1, GT jarg1_, long jarg2, GT jarg2_);

    public final static native void GT_clear(long jarg1, GT jarg1_);

    public final static native void GT_setStr(long jarg1, GT jarg1_, String jarg2);

    public final static native String GT_toString(long jarg1, GT jarg1_);

    public final static native void delete_GT(long jarg1);
}