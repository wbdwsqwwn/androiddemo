package com.demo.wanbd.androiddemo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.demo.wanbd.androiddemo.db.BlackListDB;
import com.demo.wanbd.androiddemo.model.BlackListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanbd on 16/6/1.
 */
public class BlackListDao {
    private BlackListDB mDB;

    public BlackListDao(Context context) {
        mDB = new BlackListDB(context);
    }

    /**
     * 添加数据
     * @param phone 手机号
     * @param model 拦截模式
     */
    public void add(String phone, int model) {
        SQLiteDatabase database = mDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BlackListDB.PHONE, phone);
        values.put(BlackListDB.MODEL, model);
        database.insert(BlackListDB.TB_NAME, null, values);
        database.close();
    }


    /**
     * 添加数据
     * @param model 黑名单model
     */
    public void add(BlackListModel model) {
        add(model.getPhone(), model.getModel());
    }

    /**
     * 获取所有黑名单数据
     * @return
     */
    public List<BlackListModel> findAll() {
        List<BlackListModel> data = new ArrayList<>();
        SQLiteDatabase database = mDB.getWritableDatabase();
        Cursor cursor = database.query(BlackListDB.TB_NAME, new String[]{BlackListDB.PHONE, BlackListDB.MODEL}, null, null, null, null, BlackListDB.ID);
        BlackListModel model;
        while (cursor.moveToNext()) {
            model = new BlackListModel();
            model.setPhone(cursor.getString(0));
            model.setModel(cursor.getInt(1));
            data.add(model);
        }
        database.close();
        return data;
    }

    /**
     * 删除数据
     * @param phone 手机号吗
     * @return
     */
    public boolean delete(String phone) {
        SQLiteDatabase database = mDB.getWritableDatabase();
        int count = database.delete(BlackListDB.TB_NAME, BlackListDB.PHONE + "=?", new String[]{phone});
        database.close();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更新数据
     * @param phone 手机号码
     * @param model 拦截模式
     */
    public void update(String phone, int model) {
        delete(phone);
        add(phone, model);
    }
}

