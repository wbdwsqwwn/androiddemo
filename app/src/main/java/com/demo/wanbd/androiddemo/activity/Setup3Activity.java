package com.demo.wanbd.androiddemo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.demo.wanbd.androiddemo.R;
import com.demo.wanbd.androiddemo.utils.MyConstant;
import com.demo.wanbd.androiddemo.utils.SPUtils;
import com.demo.wanbd.androiddemo.utils.ToastUtils;

public class Setup3Activity extends BaseSetupActivity {

    private EditText mInputSafeNumber;
    private String mSafeNumber;

    private static final int READ_CONTACT_PERMISSION = 1;

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
//        LogUtils.i("Setup3Activity", "selectSafeNumber");
//        intentToActivity();
        // 这里测试检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // 没有权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACT_PERMISSION);
        } else {
            // 有权限
            intentToActivity();
        }

    }

    private void intentToActivity() {
        Intent intent = new Intent(this, FriendsActivity.class);
        startActivityForResult(intent, 0);
    }

    // 权限处理的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READ_CONTACT_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                intentToActivity();
            } else {
                ToastUtils.showShortToast(this, "权限被拒绝");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1 && data != null) {
            String phone = data.getStringExtra(MyConstant.SAFENUMBER);
            mInputSafeNumber.setText(phone);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
