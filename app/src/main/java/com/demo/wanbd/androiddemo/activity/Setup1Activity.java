package com.demo.wanbd.androiddemo.activity;

import com.demo.wanbd.androiddemo.R;

public class Setup1Activity extends BaseSetupActivity {

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_setup1);
    }

    @Override
    public void startNextPage() {
        startPage(Setup2Activity.class);
    }

    @Override
    public void startPrePage() {
        // 没有上一个按钮 不用处理
    }


}
