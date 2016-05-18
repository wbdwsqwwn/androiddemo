package com.demo.wanbd.androiddemo.utils;

import android.util.Log;

/**
 * Created by wanbd on 16/5/18.
 */
public class LogUtils {
    private static final boolean isShowLog = true;

    public static void d(String tag, String msg) {
        if (isShowLog) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isShowLog) {
            Log.i(tag, msg);
        }
    }
}
