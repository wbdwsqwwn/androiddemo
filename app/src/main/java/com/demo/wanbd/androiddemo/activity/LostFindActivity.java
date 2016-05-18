package com.demo.wanbd.androiddemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demo.wanbd.androiddemo.R;
import com.demo.wanbd.androiddemo.utils.MyConstant;
import com.demo.wanbd.androiddemo.utils.SPUtils;

public class LostFindActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SPUtils.getBoolean(getApplicationContext(), MyConstant.SETUPSUCCENSSED, false)) {
            // 设置完成
            initView();
        } else {
            // 没有设置
            Intent intent = new Intent(this, Setup1Activity.class);
            startActivity(intent);
            finish();
        }

    }

    private void initView() {
        setContentView(R.layout.activity_lost_find);
    }
}
