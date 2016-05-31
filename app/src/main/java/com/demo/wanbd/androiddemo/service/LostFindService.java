package com.demo.wanbd.androiddemo.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.telephony.SmsMessage;

import com.demo.wanbd.androiddemo.R;
import com.demo.wanbd.androiddemo.utils.LogUtils;

public class LostFindService extends Service {

    private SmsReceiver mSmsReceiver;

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
        mSmsReceiver = new SmsReceiver();
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(mSmsReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i("LostFindService", "lost find service destory");
        unregisterReceiver(mSmsReceiver);
    }

    private class SmsReceiver extends BroadcastReceiver {
        private boolean isPlaying = false;
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.i("LostFindService", "收到了sms广播");
            Object[] smsDatas = (Object[]) intent.getExtras().get("pdus");
            for (Object data : smsDatas) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) data);
                String body = smsMessage.getDisplayMessageBody();
                LogUtils.i("LostFindService", "收到了短信内容是---->>>>" + body);
                if ("#*music*#".equals(body)) {
                    if (!isPlaying) {
                        MediaPlayer player = MediaPlayer.create(context, R.raw.qqqg);
                        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                isPlaying = false;
                            }
                        });
                        player.start();
                        isPlaying = true;
                    }
                    abortBroadcast();
                } else if ("#*gps*#".equals(body)) {

                } else if ("#*wipedata*#".equals(body)) {

                }else if ("#*lockscreen*#".equals(body)) {

                } else {

                }
            }
        }
    }
}
