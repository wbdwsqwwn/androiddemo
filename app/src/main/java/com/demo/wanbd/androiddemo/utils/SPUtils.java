package com.demo.wanbd.androiddemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wanbd on 16/5/11.
 */
public class SPUtils {

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(MyConstant.SPFILENAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(MyConstant.SPFILENAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static String getString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(MyConstant.SPFILENAME, Context.MODE_PRIVATE);
        return sp.getString(key, value);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(MyConstant.SPFILENAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }
}
