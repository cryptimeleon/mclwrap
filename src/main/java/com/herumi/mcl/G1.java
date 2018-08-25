/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.8
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.herumi.mcl;

public class G1 {
    private transient long swigCPtr;
    protected transient boolean swigCMemOwn;

    protected G1(long cPtr, boolean cMemoryOwn) {
        swigCMemOwn = cMemoryOwn;
        swigCPtr = cPtr;
    }

    protected static long getCPtr(G1 obj) {
        return (obj == null) ? 0 : obj.swigCPtr;
    }

    protected void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (swigCPtr != 0) {
            if (swigCMemOwn) {
                swigCMemOwn = false;
                Bn256JNI.delete_G1(swigCPtr);
            }
            swigCPtr = 0;
        }
    }

    public G1() {
        this(Bn256JNI.new_G1__SWIG_0(), true);
    }

    public G1(G1 rhs) {
        this(Bn256JNI.new_G1__SWIG_1(G1.getCPtr(rhs), rhs), true);
    }

    public G1(String x, String y) {
        this(Bn256JNI.new_G1__SWIG_2(x, y), true);
    }

    public boolean equals(G1 rhs) {
        return Bn256JNI.G1_equals(swigCPtr, this, G1.getCPtr(rhs), rhs);
    }

    public void set(String x, String y) {
        Bn256JNI.G1_set(swigCPtr, this, x, y);
    }

    public void hashAndMapToG1(String m) {
        Bn256JNI.G1_hashAndMapToG1(swigCPtr, this, m);
    }

    public void clear() {
        Bn256JNI.G1_clear(swigCPtr, this);
    }

    public void setStr(String str) {
        Bn256JNI.G1_setStr(swigCPtr, this, str);
    }

    public String toString() {
        return Bn256JNI.G1_toString(swigCPtr, this);
    }

}