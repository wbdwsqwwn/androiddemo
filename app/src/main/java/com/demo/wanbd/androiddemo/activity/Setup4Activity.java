package com.demo.wanbd.androiddemo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.demo.wanbd.androiddemo.R;
import com.demo.wanbd.androiddemo.service.LostFindService;
import com.demo.wanbd.androiddemo.utils.LogUtils;
import com.demo.wanbd.androiddemo.utils.MyConstant;
import com.demo.wanbd.androiddemo.utils.SPUtils;
import com.demo.wanbd.androiddemo.utils.ServiceUtils;
import com.demo.wanbd.androiddemo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Setup4Activity extends BaseSetupActivity {

    private CheckBox mCb_startsafe;
    private TextView mTv_startsafe;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_setup4);

        mCb_startsafe = (CheckBox) findViewById(R.id.cb_startsafe);
        mTv_startsafe = (TextView) findViewById(R.id.tv_startsafe);
    }


    @Override
    public void initData() {
        super.initData();
        if (ServiceUtils.isServiceRunning(getApplicationContext(), "com.demo.wanbd.androiddemo.service.LostFindService")) {
            mCb_startsafe.setChecked(true);
            mTv_startsafe.setText("防盗保护已经开启");
        } else {
            mCb_startsafe.setChecked(false);
            mTv_startsafe.setText("防盗保护没有开启");
        }
    }

    @Override
    public void initEvent() {
        super.initEvent();
        mCb_startsafe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mTv_startsafe.setText("防盗保护已经开启");
                    Intent intent = new Intent(Setup4Activity.this, LostFindService.class);
                    startService(intent);
                } else {
                    mTv_startsafe.setText("防盗保护没有开启");
                    Intent intent = new Intent(Setup4Activity.this, LostFindService.class);
                    stopService(intent);
                }
            }
        });
    }

//    public void setupFinish(View view) {
//        if (mCb_startsafe.isChecked()) {
//            // 开启防盗保护需要提前授予权限  发送接收短信  获取地理位置  开机完成
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)  != PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
//                    ToastUtils.showShortToast(getApplicationContext(), "这个权限是必须的");
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
//                } else {
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
//                }
//            } else {
//                setupHasFinidhed();
//            }
//
//        } else {
//            ToastUtils.showShortToast(getApplicationContext(), "必须开启防盗保护");
//        }
//
//    }

    public void setupFinish(View view) {
        List<String> permissionsNeeded = new ArrayList<>();
        permissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsNeeded.add(Manifest.permission.SEND_SMS);
        permissionsNeeded.add(Manifest.permission.READ_SMS);
        permissionsNeeded.add(Manifest.permission.RECEIVE_BOOT_COMPLETED);
        permissionsNeeded.add(Manifest.permission.RECEIVE_SMS);

        if (mCb_startsafe.isChecked()) {
            // 开启防盗保护需要提前授予权限  发送接收短信  获取地理位置  开机完成
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[permissionsNeeded.size()]), 1);
            } else {
                setupHasFinidhed();
            }
        } else {
            ToastUtils.showShortToast(getApplicationContext(), "必须开启防盗保护");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LogUtils.i("Setup4Activity", "返回回来的状态码数组是------>>>>>" + Arrays.toString(grantResults));
        LogUtils.i("Setup4Activity", "返回回来的权限数组是------>>>>>" + Arrays.toString(permissions));
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupHasFinidhed();
            } else {
                ToastUtils.showShortToast(getApplicationContext(), "permission_group.SMS 权限被拒绝");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setupHasFinidhed() {
        startPage(LostFindActivity.class);
        SPUtils.putBoolean(getApplicationContext(), MyConstant.SETUPSUCCENSSED, true);
        SPUtils.putBoolean(getApplicationContext(), MyConstant.BOOTCOMPLETE, false);
    }

    @Override
    public void startPrePage() {
        startPage(Setup3Activity.class);
    }

    @Override
    public void startNextPage() {

    }
}

