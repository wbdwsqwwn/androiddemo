package com.demo.wanbd.androiddemo.Application;

import android.app.Application;

import org.xutils.x;

/**
 * Created by wanbd on 2016/5/13.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
