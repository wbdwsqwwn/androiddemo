package com.demo.wanbd.androiddemo.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.demo.wanbd.androiddemo.R;
import com.demo.wanbd.androiddemo.utils.LogUtils;
import com.demo.wanbd.androiddemo.utils.MyConstant;
import com.demo.wanbd.androiddemo.utils.SPUtils;
import com.demo.wanbd.androiddemo.utils.ToastUtils;

public class Setup3Activity extends BaseSetupActivity {

    private EditText mInputSafeNumber;
    private String mSafeNumber;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_setup3);
        mInputSafeNumber = (EditText) findViewById(R.id.et_inputSafeNumber);
    }

    @Override
    public void initData() {
        super.initData();
        mSafeNumber = SPUtils.getString(getApplicationContext(), MyConstant.SAFENUMBER, null);
        if (!TextUtils.isEmpty(mSafeNumber)) {
            mInputSafeNumber.setText(mSafeNumber);
            mInputSafeNumber.setSelection(mSafeNumber.length());
        }
    }

    @Override
    public void startNextPage() {
        String safeNumber = mInputSafeNumber.getText().toString().trim();
        if (TextUtils.isEmpty(safeNumber)) {
            ToastUtils.showShortToast(getApplicationContext(), "请输入安全号码");
        } else {
            SPUtils.putString(getApplicationContext(), MyConstant.SAFENUMBER, safeNumber);
            mSafeNumber = safeNumber;
            startPage(Setup4Activity.class);
        }
    }

    @Override
    public void startPrePage() {
        startPage(Setup2Activity.class);
    }

    public void selectSafeNumber(View view) {
        LogUtils.i("Setup3Activity", "selectSafeNumber");
    }
}
