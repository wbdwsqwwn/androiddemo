package com.demo.wanbd.androiddemo.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.demo.wanbd.androiddemo.utils.LogUtils;

/**
 * Created by wanbd on 2016/5/19.
 */
public class BaseSetupActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        initView();
        initData();
        initEvent();
        initAnimation();
    }

    private void initAnimation() {

    }

    private void initEvent() {

    }

    private void initData() {

    }

    private void initView() {

    }

    public void nextPage(View v) {
        LogUtils.i("BaseSetupActivity", "nextPage");
    }

    public void prePage(View v) {
        LogUtils.i("BaseSetupActivity", "prePage");
    }

}
