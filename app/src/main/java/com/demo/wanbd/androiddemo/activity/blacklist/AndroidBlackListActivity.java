package com.demo.wanbd.androiddemo.activity.blacklist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.demo.wanbd.androiddemo.R;
import com.demo.wanbd.androiddemo.activity.FriendsActivity;
import com.demo.wanbd.androiddemo.dao.BlackListDao;
import com.demo.wanbd.androiddemo.db.BlackListDB;
import com.demo.wanbd.androiddemo.model.BlackListModel;
import com.demo.wanbd.androiddemo.utils.LogUtils;
import com.demo.wanbd.androiddemo.utils.MyConstant;
import com.demo.wanbd.androiddemo.utils.ScreenUtils;
import com.demo.wanbd.androiddemo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class AndroidBlackListActivity extends AppCompatActivity {
    private ImageView mIv_black_add;
    private LinearLayout mLl_progress_root;
    private ListView mLv_black_showdata;
    private ImageView mIv_black_nodata;
    private List<BlackListModel> mBlackListData = new ArrayList<>();
    private BlackListDao mBlackListDao;

    private static final int FINISH = 0;
    private static final int LOADING = 1;
    private static final int PERMISSION_CONTACTS = 1;
    private static final int PERMISSION_SMS = 2;
    private static final int PERMISSION_CALLLOG = 3;

    private BlackListAdapter mBlackListAdapter;
    private PopupWindow mPopupWindow;
    private Animation mPopupAnimation;
    private View mPopupCOntentView;
    private AlertDialog mDg_addblack;
    private EditText mEt_addblack_phone;
    private CheckBox mCb_addblack_sms;
    private CheckBox mCb_addblack_phone;
    private Button mBt_addblack_cancel;
    private Button mBt_addblack_sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）

        LogUtils.i("屏幕属性--->>>>", "屏幕宽度（像素)"+width+"屏幕高度（像素）"+height+"屏幕密度"+density+"屏幕密度DPI"+densityDpi);
        LogUtils.i("屏幕属性--->>>>", "屏幕宽度（dp)"+ ScreenUtils.px2dip(this, width)+"屏幕高度（dp）"+ScreenUtils.px2dip(this, height));

        initView();
        initData();
        initEvent();
        initPopupupWindow();
        initAddBlackDialog();
    }

    private void initAddBlackDialog() {
        final View mDialogAddblack = View.inflate(this, R.layout.dialog_addblack_comfirm, null);
        mEt_addblack_phone = (EditText) mDialogAddblack.findViewById(R.id.et_addblack_phone);
        mCb_addblack_sms = (CheckBox) mDialogAddblack.findViewById(R.id.cb_addblack_sms);
        mCb_addblack_phone = (CheckBox) mDialogAddblack.findViewById(R.id.cb_addblack_phone);
        mBt_addblack_cancel = (Button) mDialogAddblack.findViewById(R.id.bt_addblack_cancel);
        mBt_addblack_sure = (Button) mDialogAddblack.findViewById(R.id.bt_addblack_sure);
        mBt_addblack_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击取消按钮
                mDg_addblack.dismiss();
            }
        });
        mBt_addblack_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击确定按钮
                String blackPhone = mEt_addblack_phone.getText().toString().trim();
                if (TextUtils.isEmpty(blackPhone)) {
                    ToastUtils.showShortToast(getApplicationContext(), "黑名单号码不能为空");
                    return;
                }
                if (!mCb_addblack_phone.isChecked() && !mCb_addblack_sms.isChecked()) {
                    ToastUtils.showShortToast(getApplicationContext(), "拦截模式不能为空");
                    return;
                }
                BlackListModel blackListModel = new BlackListModel();
                blackListModel.setPhone(blackPhone);
                int mode = 0;
                if (mCb_addblack_sms.isChecked()) {
                    mode |= BlackListDB.SMS_MODEL;
                }
                if (mCb_addblack_phone.isChecked()) {
                    mode |= BlackListDB.PHONE_MODEL;
                }
                blackListModel.setModel(mode);
                // 添加到数据库中
                mBlackListDao.update(blackListModel);
                // 刷新界面数据
                initData();
                mDg_addblack.dismiss();
            }
        });
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setView(mDialogAddblack);
        mDg_addblack = ab.create();
    }

    private void showAddBlackDialog(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            mEt_addblack_phone.setText(phone);
        }
        mDg_addblack.show();
        mPopupWindow.dismiss();
    }

    private void showAddBlackDialog() {
        showAddBlackDialog("");
    }

    private void initPopupupWindow() {
        mPopupCOntentView = View.inflate(this, R.layout.popupwindow_addblack, null);
        mPopupWindow = new PopupWindow(mPopupCOntentView, ScreenUtils.dip2px(this, 130), ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(true);
        mPopupAnimation = new ScaleAnimation(1.0f, 1.0f, .0f, 1.0f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .0f);
        mPopupAnimation.setDuration(400);
    }

    private void initEvent() {
        mIv_black_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                } else {
                    mPopupWindow.showAsDropDown(v);
                    mPopupCOntentView.setAnimation(mPopupAnimation);
                }
            }
        });
    }

    public void manualAdd(View view) {
        LogUtils.i("BlackListActivity", "手动添加");
        showAddBlackDialog();
    }
    public void smsAdd(View view) {
        LogUtils.i("BlackListActivity", "短信添加");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS}, PERMISSION_SMS);
        } else {
            Intent intent = new Intent(this, SMSAddBlackActivity.class);
            startActivityForResult(intent, 0);
        }
    }

    public void callAdd(View view) {
        LogUtils.i("BlackListActivity", "通话添加");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG}, PERMISSION_CALLLOG);
        } else {
            Intent intent = new Intent(this, CallAddBlackActivity.class);
            startActivityForResult(intent, 0);
        }
    }

    public void friendsAdd(View view) {
        LogUtils.i("BlackListActivity", "朋友添加");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, PERMISSION_CONTACTS);
        } else {
            Intent intent = new Intent(this, FriendsActivity.class);
            startActivityForResult(intent, 0);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(this, FriendsActivity.class);
                startActivityForResult(intent, 0);
            } else {
                ToastUtils.showShortToast(getApplicationContext(), "读取联系人权限被拒绝");
            }
        } else if (requestCode == PERMISSION_SMS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(this, SMSAddBlackActivity.class);
                startActivityForResult(intent, 0);
            } else {
                ToastUtils.showShortToast(getApplicationContext(), "读取短信权限被拒绝");
            }
        } else if (requestCode == PERMISSION_CALLLOG) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(this, CallAddBlackActivity.class);
                startActivityForResult(intent, 0);
            } else {
                ToastUtils.showShortToast(getApplicationContext(), "读取通话记录权限被拒绝");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String blackPhone = data.getStringExtra(MyConstant.SAFENUMBER);
            showAddBlackDialog(blackPhone);
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOADING:
                    mLv_black_showdata.setVisibility(View.GONE);
                    mLl_progress_root.setVisibility(View.VISIBLE);
                    mIv_black_nodata.setVisibility(View.GONE);
                    break;
                case FINISH:
                    if (mBlackListData.size() > 0) {
                        mLl_progress_root.setVisibility(View.GONE);
                        mIv_black_nodata.setVisibility(View.GONE);
                        mLv_black_showdata.setVisibility(View.VISIBLE);
                        mBlackListAdapter.notifyDataSetInvalidated();
                    } else {
                        mLv_black_showdata.setVisibility(View.GONE);
                        mLl_progress_root.setVisibility(View.GONE);
                        mIv_black_nodata.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    };

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.obtainMessage(LOADING).sendToTarget();
                // 这里从数据库里读取数据
                mBlackListData = mBlackListDao.findAll();
//                SystemClock.sleep(1000);
                mHandler.obtainMessage(FINISH).sendToTarget();
            }
        }).start();
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_blacklist);

        mIv_black_add = (ImageView) findViewById(R.id.iv_black_add);
        mLl_progress_root = (LinearLayout) findViewById(R.id.ll_progress_root);
        mLv_black_showdata = (ListView) findViewById(R.id.lv_black_showdata);
        mIv_black_nodata = (ImageView) findViewById(R.id.iv_black_nodata);
        mBlackListAdapter = new BlackListAdapter();
        mLv_black_showdata.setAdapter(mBlackListAdapter);
        mBlackListDao = new BlackListDao(this);
        if (mBlackListData.size() > 0) {
            mLl_progress_root.setVisibility(View.GONE);
            mIv_black_nodata.setVisibility(View.GONE);
            mLv_black_showdata.setVisibility(View.VISIBLE);
            mBlackListAdapter.notifyDataSetChanged();
        } else {
            mLv_black_showdata.setVisibility(View.GONE);
            mLl_progress_root.setVisibility(View.GONE);
            mIv_black_nodata.setVisibility(View.VISIBLE);
        }
    }

    private class BlackListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mBlackListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mBlackListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, android.view.View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(AndroidBlackListActivity.this, R.layout.item_blacklist_lv, null);
                holder = new ViewHolder();
                holder.iv_delButton = (ImageView) convertView.findViewById(R.id.iv_delete);
                holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_delete_phone);
                holder.tv_model = (TextView) convertView.findViewById(R.id.tv_delete_model);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final BlackListModel blackListModel = (BlackListModel) getItem(position);
            holder.tv_phone.setText(blackListModel.getPhone());
            switch (blackListModel.getModel()) {

                case BlackListDB.PHONE_MODEL:

                    holder.tv_model.setText("电话拦截");
                    break;
                case BlackListDB.SMS_MODEL:
                    holder.tv_model.setText("短信拦截");
                    break;
                case BlackListDB.ALL_MODEL:
                    holder.tv_model.setText("全部拦截");
                    break;
                default:
                    holder.tv_model.setText("拦截模式异常");
                    break;
            }
            holder.iv_delButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBlackListData.remove(blackListModel);
                    mBlackListDao.delete(blackListModel.getPhone());
                    mBlackListAdapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }

    private class ViewHolder {
        TextView tv_phone;
        TextView tv_model;
        ImageView iv_delButton;
    }
}
