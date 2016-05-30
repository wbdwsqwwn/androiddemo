package com.demo.wanbd.androiddemo.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.demo.wanbd.androiddemo.service.LostFindService;
import com.demo.wanbd.androiddemo.utils.LogUtils;
import com.demo.wanbd.androiddemo.utils.MyConstant;
import com.demo.wanbd.androiddemo.utils.SPUtils;

public class BootCompleteReceiver extends BroadcastReceiver {
    public BootCompleteReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        LogUtils.i("BootCompleteReceiver", "接收到系统启动完毕的广播");
        // 获取SIM卡安全号码
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String currentSIMNumber = tm.getSimSerialNumber() + "1";
        if (!currentSIMNumber.equals(SPUtils.getString(context, MyConstant.SIMNUMBER, null))) {
            // 获取到的号码与保存的号码不一致 发送报警短信
            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(SPUtils.getString(context, MyConstant.SAFENUMBER, "18612490558"), null, "换号了", null, null);
        }
        // 系统启动完毕 默认启动lostfindservice
        if (SPUtils.getBoolean(context, MyConstant.BOOTCOMPLETE, true)) {
            LogUtils.i("BootCompleteReceiver", "代码执行到了开启丢失服务的地方");
            Intent lostFindIntent = new Intent(context, LostFindService.class);
            context.startService(lostFindIntent);
        }
//        throw new UnsupportedOperationException("Not yet implemented >>>>>>>>>>>>");
    }
}
