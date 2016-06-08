package com.demo.wanbd.androiddemo.activity;

import com.demo.wanbd.androiddemo.activity.blacklist.BaseCallSMSBlackActivity;
import com.demo.wanbd.androiddemo.dao.ContactDao;
import com.demo.wanbd.androiddemo.model.ContactModel;

import java.util.List;

public class FriendsActivity extends BaseCallSMSBlackActivity {


    @Override
    public List<ContactModel> getContacts() {
        return ContactDao.getAllContact(FriendsActivity.this);
    }
}
