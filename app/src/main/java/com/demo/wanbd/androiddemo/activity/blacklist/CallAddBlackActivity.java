package com.demo.wanbd.androiddemo.activity.blacklist;

import com.demo.wanbd.androiddemo.dao.ContactDao;
import com.demo.wanbd.androiddemo.model.ContactModel;

import java.util.List;

/**
 * Created by wanbd on 16/6/6.
 */
public class CallAddBlackActivity extends BaseCallSMSBlackActivity {
    @Override
    public List<ContactModel> getContacts() {
        return ContactDao.getCallContact(this);
    }
}
