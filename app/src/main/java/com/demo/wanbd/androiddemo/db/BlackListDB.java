package com.demo.wanbd.androiddemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wanbd on 16/6/1.
 */
public class BlackListDB extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String TB_NAME = "BLACKLIST_TB";
    public static final String DB_NAME = "blacklistdb.db";
    public static final String ID = "_id";
    public static final String PHONE = "PHONE";
    public static final String MODEL = "MODEL";
    public static final int SMS_MODEL = 1<<0; // 01
    public static final int PHONE_MODEL = 1<<1; // 10
    public static final int ALL_MODEL = SMS_MODEL | PHONE_MODEL;

    public BlackListDB(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE BLACKLIST_TB(_id integer PRIMARY KEY AUTOINCREMENT, PHONE text, MODEL integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE BLACKLIST_TB");
        onCreate(db);
    }
}
