package com.example.aw_library.log;

import android.util.Log;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LogLevel {
    public static final int A = Log.ASSERT;
    public static final int D = Log.DEBUG;
    public static final int I = Log.INFO;
    public static final int E = Log.ERROR;
    public static final int V = Log.VERBOSE;
    public static final int W = Log.WARN;


    @IntDef({A, D, I, E, V, W})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LEVEL {

    }

}
