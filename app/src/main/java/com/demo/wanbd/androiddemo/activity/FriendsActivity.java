package com.demo.wanbd.androiddemo.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.demo.wanbd.androiddemo.dao.ContactDao;
import com.demo.wanbd.androiddemo.model.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends ListActivity {

    private ListView mListView;
    private List<ContactModel> mContacts = new ArrayList<>();

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_friends);
        initData();
        initView();
    }

    private void initView() {
        mListView = getListView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mContacts = ContactDao.getAllContact(FriendsActivity.this);
            }
        }).start();
    }
}
