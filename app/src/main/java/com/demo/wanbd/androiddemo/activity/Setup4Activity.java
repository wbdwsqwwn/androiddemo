package com.demo.wanbd.androiddemo.activity;

import com.demo.wanbd.androiddemo.R;

public class Setup4Activity extends BaseSetupActivity {

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_setup4);
    }

    @Override
    public void startNextPage() {
        startPage(LostFindActivity.class);
    }

    @Override
    public void startPrePage() {
        startPage(Setup3Activity.class);
    }
}

