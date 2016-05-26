package com.demo.wanbd.androiddemo;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.demo.wanbd.androiddemo.dao.ContactDao;
import com.demo.wanbd.androiddemo.model.ContactModel;
import com.demo.wanbd.androiddemo.utils.LogUtils;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testGetAllContact() {
        List<ContactModel> list = ContactDao.getAllContact(getContext());
        LogUtils.i("test  test test ====>>>>", list.toString());
        System.out.println("test  test test ====>>>>" + list.toString());
        assertEquals(117, list.size());
    }
}