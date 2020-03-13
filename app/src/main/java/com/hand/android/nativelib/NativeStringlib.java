package com.hand.android.nativelib;

public class NativeStringlib {

    static{
        System.loadLibrary("native-lib");
    }


    public static native String stringFromJNI();
}
