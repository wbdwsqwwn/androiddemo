package com.demo.wanbd.androiddemo;

import android.test.ApplicationTestCase;

import com.demo.wanbd.androiddemo.dao.ContactDao;
import com.demo.wanbd.androiddemo.model.ContactModel;
import com.demo.wanbd.androiddemo.utils.LogUtils;

import java.util.List;

/**
 * Created by wanbd on 16/5/24.
 */
public class ContactDaoTest extends ApplicationTestCase {
    public ContactDaoTest(Class applicationClass) {
        super(applicationClass);
    }

    public void testGetAllContact() {
        List<ContactModel> list = ContactDao.getAllContact(getContext());
        LogUtils.i("test  test test ====>>>>", list.toString());
    }
}
