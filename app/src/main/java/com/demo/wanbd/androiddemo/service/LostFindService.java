package com.demo.wanbd.androiddemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.demo.wanbd.androiddemo.utils.LogUtils;

public class LostFindService extends Service {
    public LostFindService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.i("LostFindService", "lost find service start");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i("LostFindService", "lost find service destory");
    }
}
