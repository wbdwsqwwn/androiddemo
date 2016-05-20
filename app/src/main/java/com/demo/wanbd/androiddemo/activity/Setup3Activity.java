package com.demo.wanbd.androiddemo.activity;

import com.demo.wanbd.androiddemo.R;

public class Setup3Activity extends BaseSetupActivity {

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_setup3);
    }

    @Override
    public void startNextPage() {
        startPage(Setup4Activity.class);
    }

    @Override
    public void startPrePage() {
        startPage(Setup2Activity.class);
    }
}
