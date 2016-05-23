package com.demo.wanbd.androiddemo.activity;

import android.graphics.drawable.Drawable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.demo.wanbd.androiddemo.R;
import com.demo.wanbd.androiddemo.utils.LogUtils;
import com.demo.wanbd.androiddemo.utils.MyConstant;
import com.demo.wanbd.androiddemo.utils.SPUtils;
import com.demo.wanbd.androiddemo.utils.ToastUtils;

public class Setup2Activity extends BaseSetupActivity {

    private Button mBt_bindSIM;
    private String mSIMNumber;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_setup2);
        mBt_bindSIM = (Button) findViewById(R.id.bt_bindSIM);
    }

    @Override
    public void initData() {
        super.initData();
        mSIMNumber = SPUtils.getString(getApplicationContext(), MyConstant.SIMNUMBER, null);
        if (TextUtils.isEmpty(mSIMNumber)) {
            // 没有保存过sim号码
            setButtonDrawableRight(R.mipmap.unlock);
        } else {
            // 保存过sim号码
            setButtonDrawableRight(R.mipmap.lock);
        }
    }

    @Override
    public void startNextPage() {
        if (TextUtils.isEmpty(mSIMNumber)) {
            // 没有保存过sim号码
            ToastUtils.showShortToast(getApplicationContext(), "请先绑定SIM卡");
        } else {
            // 保存过sim号码
            startPage(Setup3Activity.class);
        }
    }

    @Override
    public void startPrePage() {
        startPage(Setup1Activity.class);
    }


    @Override
    public void bindSIM(View v) {
        super.bindSIM(v);
        LogUtils.i("Setup2Activity", "bindSIM");
        if (TextUtils.isEmpty(mSIMNumber)) {
            // 没有保存过sim号码
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            String simSerialNumber = tm.getSimSerialNumber();
            mSIMNumber = simSerialNumber;
            LogUtils.i("Setup2Activity", "simSerialNumber=" + simSerialNumber);
            SPUtils.putString(getApplicationContext(), MyConstant.SIMNUMBER, simSerialNumber);
            setButtonDrawableRight(R.mipmap.lock);

        } else {
            // 已经保存过sim号码
            SPUtils.putString(getApplicationContext(), MyConstant.SIMNUMBER, "");
            setButtonDrawableRight(R.mipmap.unlock);
            mSIMNumber = "";
        }
    }

    private void setButtonDrawableRight(int resID) {
        Drawable drawbale_right = getResources().getDrawable(resID);
        drawbale_right.setBounds(0, 0, drawbale_right.getIntrinsicWidth(), drawbale_right.getMinimumHeight());
        mBt_bindSIM.setCompoundDrawables(null, null, drawbale_right, null);
    }
}
