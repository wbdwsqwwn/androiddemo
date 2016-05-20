package com.demo.wanbd.androiddemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.demo.wanbd.androiddemo.R;
import com.demo.wanbd.androiddemo.utils.LogUtils;

/**
 * Created by wanbd on 2016/5/19.
 */
public abstract class BaseSetupActivity extends AppCompatActivity {

    private GestureDetector mGd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initView();
        initData();
        initEvent();
        initAnimation();
        initGesture();
    }

    private void initGesture() {
        mGd = new GestureDetector(this, new myOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (Math.abs(e1.getX() - e2.getX()) > Math.abs(e2.getY()- e1.getY())) {
                    //横向滑动
                    //判断速度
                    if (Math.abs(velocityX) > 50){//
                        //判断方向
                        if (velocityX < 0) {
                            //从左往右滑
                            prePage(null);
                        } else {
                            //从右往左滑
                            nextPage(null);
                        }
                    }
                }
                return true;
            }
        });
    }

    public void initAnimation() {

    }

    public void initEvent() {

    }

    public void initData() {

    }

    public void initView() {

    }

    public void nextPage(View v) {
        LogUtils.i("BaseSetupActivity", "nextPage");
        startNextPage();
        nextPageAnimation();
    }

    private void nextPageAnimation() {
        overridePendingTransition(R.anim.next_enter_anim, R.anim.next_exit_anim);
    }

    public void prePage(View v) {
        LogUtils.i("BaseSetupActivity", "prePage");
        startPrePage();
        prePageAnimation();
    }

    private void prePageAnimation() {
        overridePendingTransition(R.anim.pre_enter_anim, R.anim.pre_exit_anim);
    }

    public abstract void startNextPage();
    public abstract void startPrePage();

    public void startPage(Class target) {
        Intent intent = new Intent(this, target);
        startActivity(intent);
        finish();
    }

    private class myOnGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }
}
