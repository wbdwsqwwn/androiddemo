package com.demo.wanbd.androiddemo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.demo.wanbd.androiddemo.R;
import com.demo.wanbd.androiddemo.service.LostFindService;
import com.demo.wanbd.androiddemo.utils.MyConstant;
import com.demo.wanbd.androiddemo.utils.SPUtils;
import com.demo.wanbd.androiddemo.utils.ServiceUtils;
import com.demo.wanbd.androiddemo.utils.ToastUtils;

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

    public void setupFinish(View view) {
        if (mCb_startsafe.isChecked()) {
            startPage(LostFindActivity.class);
            SPUtils.putBoolean(getApplicationContext(), MyConstant.SETUPSUCCENSSED, true);
        } else {
            ToastUtils.showShortToast(getApplicationContext(), "必须开启防盗保护");
        }

    }

    @Override
    public void startPrePage() {
        startPage(Setup3Activity.class);
    }

    @Override
    public void startNextPage() {

    }
}

