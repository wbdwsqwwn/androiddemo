package com.demo.wanbd.androiddemo.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;


/**
 * Created by wanbd on 16/5/27.
 */
public class ServiceUtils {

    public static boolean isServiceRunning(Context context, String serviceName) {
        boolean result = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
            result = runningServiceInfo.service.getClassName().equals(serviceName);
            if (result) {
                break;
            }
        }
        return result;
    }
}

