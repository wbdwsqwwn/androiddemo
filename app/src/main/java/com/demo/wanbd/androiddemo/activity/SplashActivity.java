package com.demo.wanbd.androiddemo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.wanbd.androiddemo.R;
import com.demo.wanbd.androiddemo.model.VersionModel;
import com.demo.wanbd.androiddemo.utils.MyConstant;
import com.demo.wanbd.androiddemo.utils.SPUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    private static final int LOADMAIN = 1;
    private static final int UPDATE = 3;

    private RelativeLayout splash_rl_root;
    private TextView versionCode;
    private TextView versionName;
    private int mCurrentVersionCode;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADMAIN:
                    // 进主界面
                    startHome();
                    break;
                case UPDATE:
                    // 显示是否下载对话框
                    showIsDownloadDialog();
                    break;
                default:

                    break;
            }
        }
    };
    private VersionModel mVersionModel;

    private void showIsDownloadDialog() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("提示");
        ab.setMessage("有新版本是否下载:\n新版本功能:" + mVersionModel.getDesc());
        ab.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 点击下载 开始下载apk
                downloadNewApk();
            }
        });
        ab.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 点击取消进主界面
                startHome();
            }
        });
        ab.show();
    }

    private void downloadNewApk() {
        final String apkName = "newApk.apk";
        HttpUtils httpUtils = new HttpUtils(5000);
        File sdDir = Environment.getExternalStorageDirectory();
        File apkFile = new File(sdDir, apkName);
        if (apkFile.exists()) {
            apkFile.delete();
        }
        httpUtils.download(mVersionModel.getUrl(), sdDir.getAbsolutePath() + "/" + apkName, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                // 下载完成 跳到安装界面
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");
                File file = new File(Environment.getExternalStorageDirectory(), apkName);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                startActivityForResult(intent, 1);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                // 下载失败 进入主界面
                startHome();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        startHome();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // TODO 模拟数据
        SPUtils.putBoolean(getApplicationContext(), MyConstant.AUTOUPDATE, true);

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
            mCurrentVersionCode = info.versionCode;
            versionName.setText(info.versionName);
            versionCode.setText(mCurrentVersionCode + "");
        } catch (PackageManager.NameNotFoundException e) {
            // 包名没有找到
            e.printStackTrace();
        }
    }



    private class SplashAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            // 动画开始
            // 联网获取版本信息并比较版本
            if (SPUtils.getBoolean(getApplicationContext(), MyConstant.AUTOUPDATE, false)) {
                // 检查版本
                checkVersion();
            } else {
                // 不做处理
            }
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            // 动画结束
            if (SPUtils.getBoolean(getApplicationContext(), MyConstant.AUTOUPDATE, false)) {
                // 自动升级 在 checkVersion 中处理了 这里不用处理
            } else {
                // 进主界面
                startHome();
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // 动画重复
        }
    }

    private void startHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void checkVersion() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    readUrlData();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void readUrlData() throws IOException, JSONException {
        Message msg = mHandler.obtainMessage();
        URL url = new URL(getResources().getString(R.string.versionurl));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        int code = conn.getResponseCode();
        if (code == 200) {
            System.out.println("返回码 == 200");
            InputStream is = conn.getInputStream();
            String json = stream2Json(is);
            mVersionModel = json2Model(json);
            if (mCurrentVersionCode == Integer.parseInt(mVersionModel.getCode())) {
                // 版本号相同 进入界面
                msg.what = LOADMAIN;
            } else {
                // 版本号不同 提示是否下载新版本
                msg.what = UPDATE;
            }
        } else {
            System.out.println("返回码 不是200");
        }
        mHandler.sendMessage(msg);
    }

    private VersionModel json2Model(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        VersionModel model = new VersionModel();
        model.setCode(object.getString("versioncode"));
        model.setName(object.getString("versionname"));
        model.setDesc(object.getString("desc"));
        model.setUrl(object.getString("url"));
        return model;
    }

    private String stream2Json(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = reader.readLine();
        while (line != null) {
            sb.append(line);
            line = reader.readLine();
        }
        return sb.toString();
    }
}
