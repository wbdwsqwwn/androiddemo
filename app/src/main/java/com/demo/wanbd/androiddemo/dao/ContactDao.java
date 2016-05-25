package com.demo.wanbd.androiddemo.dao;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.demo.wanbd.androiddemo.model.ContactModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanbd on 16/5/24.
 */
public class ContactDao {

    public static List<ContactModel> getAllContact(Context context) {
        List<ContactModel> contacts = new ArrayList();
        Uri uriContacts = Uri.parse("content://com.android.contacts/contacts");
        Uri uriData = Uri.parse("content://com.android.contacts/data");
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
