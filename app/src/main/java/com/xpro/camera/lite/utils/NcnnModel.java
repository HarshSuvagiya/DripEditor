package com.xpro.camera.lite.utils;

import android.graphics.Bitmap;

public class NcnnModel {
    public native String GetFace(Bitmap bitmap, int[] iArr, int[] iArr2);

    public native int GetFaceFeature(Bitmap bitmap, int[] iArr, int[] iArr2);

    public native String GetPersonRectRefineContour(Bitmap bitmap, int[] iArr, int[] iArr2);

    public native String GetStyle(Bitmap bitmap, int[] iArr, int i);

    public native boolean InitFaceDet(String str);

    public native boolean InitFaceFeatureDet(String str);

    public native boolean InitPerson(String str, String str2);

    public native boolean InitString(String str, String str2);

    public native void grabcut(int[] iArr, int[] iArr2, int[] iArr3, int i, int i2, int i3, int i4, int[] iArr4, int i5);

    public native boolean setMinSize(int i);

    static {
        System.loadLibrary("nativeai");
    }
}
