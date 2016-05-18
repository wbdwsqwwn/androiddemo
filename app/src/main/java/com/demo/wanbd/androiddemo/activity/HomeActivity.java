package com.demo.wanbd.androiddemo.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
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
import com.demo.wanbd.androiddemo.utils.ToastUtils;

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


    private void initAnimation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mHomeLogo, "rotationY", 0, 60, 90, 120, 180, 240, 300, 360);
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
        showMyDialog(false);
    }

    private void showEnterPasswordDialog() {
        showMyDialog(true);
    }

    private void showMyDialog(final boolean isSetPassword) {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);

        View layout = View.inflate(this, R.layout.dialog_setpassword, null);
        TextView tv_title = (TextView) layout.findViewById(R.id.dg_setpwd_title);
        final EditText et_password1 = (EditText) layout.findViewById(R.id.dg_setpwd_password1);
        final EditText et_password2 = (EditText) layout.findViewById(R.id.dg_setpwd_password2);
        final Button bt_cancel = (Button) layout.findViewById(R.id.dg_setpwd_cancel);
        final Button bt_sure = (Button) layout.findViewById(R.id.dg_setpwd_sure);
        if (isSetPassword) {
            et_password2.setVisibility(View.GONE);
            tv_title.setText("输入密码");
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == bt_cancel) {
                    // 点击取消
                    mSetPwdDialog.dismiss();
                } else if (v == bt_sure) {
                    // 点击确定
                    String password1 = et_password1.getText().toString().trim();
                    String password2 = et_password2.getText().toString().trim();
                    if (isSetPassword) {
                        // 设置一个默认值
                        password2 = "111";
                    }
                    if (isSetPassword) {
                        // 已经设置过密码需要与保存的密码比较
                        if (password1.equals(SPUtils.getString(getApplicationContext(), MyConstant.PASSWORD, ""))) {
                            // 密码相同 跳转到手机防盗界面
                            Intent intent = new Intent(HomeActivity.this, LostFindActivity.class);
                            startActivity(intent);
                        } else {
                            // 密码不对
//                            Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT);
                            ToastUtils.showShortToast(getApplicationContext(), "密码错误");
                            return;
                        }
                    } else {
                        // 没有设置过密码 判断2次密码是否相同
                        if (password1.equals(password2)) {
                            SPUtils.putString(getApplicationContext(), MyConstant.PASSWORD, password1);
//                            Toast.makeText(getApplicationContext(), "密码设置成功", Toast.LENGTH_SHORT);
                            ToastUtils.showShortToast(getApplicationContext(), "密码设置成功");
                        } else {
//                            Toast.makeText(getApplicationContext(), "两次密码输入不一致", Toast.LENGTH_SHORT);
                            ToastUtils.showShortToast(getApplicationContext(), "两次密码输入不一致");
                            return;
                        }

                    }

                }
                mSetPwdDialog.dismiss();
            }
        };
        bt_cancel.setOnClickListener(listener);
        bt_sure.setOnClickListener(listener);
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
