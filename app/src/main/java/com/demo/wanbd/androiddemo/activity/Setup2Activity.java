package com.demo.wanbd.androiddemo.activity;

import com.demo.wanbd.androiddemo.R;

public class Setup2Activity extends BaseSetupActivity {

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_setup2);
    }

    @Override
    public void startNextPage() {
        startPage(Setup3Activity.class);
    }

    @Override
    public void startPrePage() {
        startPage(Setup1Activity.class);
    }
}
