package com.demo.wanbd.androiddemo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wanbd on 16/5/18.
 */
public class ToastUtils {
    private static Toast mToast = null;

    public static void showShortToast(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
}
