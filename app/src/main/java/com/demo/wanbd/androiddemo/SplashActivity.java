package com.demo.wanbd.androiddemo;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private RelativeLayout splash_rl_root;
    private TextView versionCode;
    private TextView versionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_splash);

        // 初始化view
        initView();

        // 初始化数据
        initData();
        // 初始化
        initAnimation();

    }

    /**
     * 初始化view
     */
    private void initView() {
        splash_rl_root = (RelativeLayout) findViewById(R.id.splash_rl_root);
        versionCode = (TextView) findViewById(R.id.splah_tv_version_code);
        versionName = (TextView) findViewById(R.id.splash_tv_version_name);
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        AnimationSet as = new AnimationSet(true);

        // 添加透明度动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1000);
        as.addAnimation(alphaAnimation);

        // 添加旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        as.addAnimation(rotateAnimation);

        // 添加缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        as.addAnimation(scaleAnimation);

        // 添加动画集
        splash_rl_root.startAnimation(as);
        as.setAnimationListener(new SplashAnimationListener());
    }

    /**
     * 初始化数据
     */
    private void initData() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            System.out.println("======versionName>>>>>>>" + info.versionName);
            System.out.println("======versionCode>>>>>>>" + info.versionCode);
            versionName.setText(info.versionName);
            versionCode.setText(info.versionCode + "");
        } catch (PackageManager.NameNotFoundException e) {
            // 报名没有找到
            e.printStackTrace();
        }
    }

    private class SplashAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            // 动画开始
            // 联网获取版本信息并比较版本
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            // 动画结束
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // 动画重复
        }
    }
}
