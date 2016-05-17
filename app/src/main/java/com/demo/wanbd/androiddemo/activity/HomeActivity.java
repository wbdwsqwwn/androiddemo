package com.demo.wanbd.androiddemo.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.wanbd.androiddemo.R;
import com.demo.wanbd.androiddemo.utils.MyConstant;
import com.demo.wanbd.androiddemo.utils.SPUtils;

/**
 * Created by wanbd on 16/5/12.
 */
public class HomeActivity extends Activity {

    private static final String[] names = {"手机防盗", "通讯卫士", "软件管家", "进程管理", "流量统计", "病毒查杀", "缓存清理", "高级工具"};
    private static final String[] descs = {"手机丢失好找", "防骚扰防监听", "方便管理软件", "保持手机通畅", "注意流量超标", "手机安全保障", "手机快步如飞", "特性处理更好"};
    private static final int[] resIds = {R.mipmap.sjfd, R.mipmap.srlj, R.mipmap.rjgj, R.mipmap.jcgl,
            R.mipmap.lltj, R.mipmap.sjsd, R.mipmap.hcql, R.mipmap.szzx};
    private GridView mHomeGv;
    private ImageView mHomeLogo;
    private ImageView mHomeSetting;
    private AlertDialog mSetPwdDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化view
        initView();
        // 初始化数据
        initData();
        // 初始化事件
        initEvent();
        // 初始化动画
        initAnimation();
    }

    private void initAnimation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mHomeLogo, "rotationY", 0,60,90,120,180,240,300,360);
        objectAnimator.setDuration(2000);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.start();
    }

    private void initEvent() {
        mHomeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        String password = SPUtils.getString(getApplicationContext(), MyConstant.PASSWORD, null);
                        if (TextUtils.isEmpty(password)) {
                            showSetPasswordDialog();
                        } else {
                            showEnterPasswordDialog();
                        }
                        break;
                }
            }
        });
    }

    private void showSetPasswordDialog() {

    }

    private void showEnterPasswordDialog() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);

        View layout = View.inflate(this, R.layout.dialog_setpassword, null);
        TextView tv_title = (TextView) layout.findViewById(R.id.dg_setpwd_title);
        EditText et_password1 = (EditText) layout.findViewById(R.id.dg_setpwd_password1);
        EditText et_password2 = (EditText) layout.findViewById(R.id.dg_setpwd_password2);
        Button bt_cancel = (Button) layout.findViewById(R.id.dg_setpwd_cancel);
        Button bt_sure = (Button) layout.findViewById(R.id.dg_setpwd_sure);

        ab.setView(layout);
        mSetPwdDialog = ab.create();
        mSetPwdDialog.show();
    }

    private void initData() {
        mHomeGv.setAdapter(new GrideViewAdapter());
    }

    private void initView() {
        setContentView(R.layout.activity_home);
        mHomeGv = (GridView) findViewById(R.id.home_gv);
        mHomeLogo = (ImageView) findViewById(R.id.home_top_iv);
        mHomeSetting = (ImageView) findViewById(R.id.home_setting);
    }

    private class GrideViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // item
            View layout = View.inflate(getApplicationContext(), R.layout.grideview_item_home, null);
            // 子view
            ImageView imageView = (ImageView) layout.findViewById(R.id.home_gv_iv);
            TextView name = (TextView) layout.findViewById(R.id.home_gv_name);
            TextView desc = (TextView) layout.findViewById(R.id.home_gv_desc);
            imageView.setImageResource(resIds[position]);
            name.setText(names[position]);
            desc.setText(descs[position]);
            return layout;
        }
    }
}
