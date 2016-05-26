package com.demo.wanbd.androiddemo.activity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.wanbd.androiddemo.R;
import com.demo.wanbd.androiddemo.dao.ContactDao;
import com.demo.wanbd.androiddemo.model.ContactModel;
import com.demo.wanbd.androiddemo.utils.LogUtils;
import com.demo.wanbd.androiddemo.utils.MyConstant;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends ListActivity {

    private static final int LOADING = 1;
    private static final int FINISH = 2;

    private ListView mListView;
    private List<ContactModel> mContacts = new ArrayList<>();
    private ContactAdapter mContactAdapter;

    private Handler mHandler = new Handler(){
        private ProgressDialog pd = null;
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case LOADING:
                    mListView.setVisibility(View.GONE);
                    pd = new ProgressDialog(FriendsActivity.this);
                    pd.setTitle("提示");
                    pd.setMessage("正在拼命加载中...");
                    pd.show();
                    break;
                case FINISH:
                    pd.dismiss();
                    mListView.setVisibility(View.VISIBLE);
                    mContactAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_friends);
        initData();
        initView();
        initEvent();
    }

    private void initEvent() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactModel model = (ContactModel) mListView.getItemAtPosition((int) mListView.getItemIdAtPosition(position));
                Intent intent = new Intent();
                intent.putExtra(MyConstant.SAFENUMBER, model.getPhone());
                setResult(1, intent);
                finish();
            }
        });
    }

    private void initView() {
        mListView = getListView();
        mContactAdapter = new ContactAdapter();
        mListView.setAdapter(mContactAdapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 获取数据前开始显示进度条
                mHandler.obtainMessage(LOADING).sendToTarget();
                mContacts = ContactDao.getAllContact(FriendsActivity.this);
                LogUtils.i("FriendsActivity", "数据的大小====>>>>>>" + mContacts.size());
//                SystemClock.sleep(1000);
                // 获取数据后让进度条消失
                mHandler.obtainMessage(FINISH).sendToTarget();
            }
        }).start();
    }

    private class ContactAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mContacts.size();
        }

        @Override
        public Object getItem(int position) {
            return mContacts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, android.view.View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_friends_contact_lv, null);
                holder = new ViewHolder();
                holder.tv_name = (TextView) convertView.findViewById(R.id.friends_contact_tv_name);
                holder.tv_phone = (TextView) convertView.findViewById(R.id.friends_contact_tv_phone);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ContactModel model = (ContactModel) getItem(position);
            holder.tv_name.setText(model.getName());
            holder.tv_phone.setText(model.getPhone());
            return convertView;
        }
    }

    private class ViewHolder {
        TextView tv_name;
        TextView tv_phone;
    }
}
