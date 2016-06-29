package com.moor.imkf.mp3recorder.util;

/**
 * Created by Duanyy on 2016/6/24.
 */
public class LameUtil {
    public LameUtil() {
    }

    static {
        System.loadLibrary("moormp3");
    }

    public static native void init(int var0, int var1, int var2, int var3, int var4);

    public static native int encode(short[] var0, short[] var1, int var2, byte[] var3);

    public static native int flush(byte[] var0);

    public static native void close();



    }
