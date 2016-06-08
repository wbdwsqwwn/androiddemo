package com.demo.wanbd.androiddemo.dao;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.demo.wanbd.androiddemo.model.ContactModel;
import com.demo.wanbd.androiddemo.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanbd on 16/5/24.
 */
public class ContactDao {

    public static List<ContactModel> getSMSContact(Context context) {
        List<ContactModel> contacts = new ArrayList<>();
        Uri smsUri = Uri.parse("content://sms");
        Cursor cursor = context.getContentResolver().query(smsUri, new String[]{"address"}, null, null, null);
        ContactModel model;
        while (cursor.moveToNext()) {
            model = new ContactModel();
            model.setName("sms");
            model.setPhone(cursor.getString(0));
            contacts.add(model);
        }
        cursor.close();
        return contacts;
    }

    public static List<ContactModel> getCallContact(Context context) {
        List<ContactModel> contacts = new ArrayList<>();
        Uri callUri = Uri.parse("content://call_log/calls");
        Cursor cursor = context.getContentResolver().query(callUri, new String[]{"name", "number"}, null, null, null);
        ContactModel model;
        while (cursor.moveToNext()) {
            model = new ContactModel();
            model.setName(cursor.getString(0));
            model.setPhone(cursor.getString(1));
            contacts.add(model);
        }
        cursor.close();
        return contacts;
    }

    public static List<ContactModel> getAllContact(Context context) {
        List<ContactModel> contacts = new ArrayList();
        Uri uriContacts = Uri.parse("content://com.android.contacts/contacts");
        Uri uriData = Uri.parse("content://com.android.contacts/data");
//        Uri uriContacts = ContactsContract.Contacts.CONTENT_URI;
//        Uri uriData = ContactsContract.Data.CONTENT_URI;
        LogUtils.i("ContactDao", "COntacts URI = >>>>>>" + uriContacts);
        LogUtils.i("ContactDao", "Data URi = >>>>>>>" + uriData);
        ContactModel contactModel = null;
        String contact_id = null;
        Cursor cursor = context.getContentResolver().query(uriContacts, new String[]{"name_raw_contact_id"}, null, null, null);
        while (cursor.moveToNext()) {
            contactModel = new ContactModel();
            contact_id = cursor.getString(0);
            Cursor cursor1 = context.getContentResolver().query(uriData, new String[]{"data1", "mimetype"}, "raw_contact_id=?", new String[]{contact_id}, null);
            while (cursor1.moveToNext()) {
                String data = cursor1.getString(0);
                String mimeType = cursor1.getString(1);
                if (mimeType.equals("vnd.android.cursor.item/phone_v2")){
                    //电话
                    contactModel.setPhone(data);
                } else if (mimeType.equals("vnd.android.cursor.item/name")) {
                    //名字
                    contactModel.setName(data);
                }
            }
            cursor1.close();
            contacts.add(contactModel);
        }
        cursor.close();
        return contacts;
    }

}
