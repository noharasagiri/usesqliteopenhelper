package com.example.usesqliteopenhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "info.db";//数据库名称
    public static final int DB_VERSION = 1;//数据库版本号
    public static final String TABLE_NAME = "student";//表名
    public static final String ID = "id";//表字段
    public static final String NAME = "name";//表字段
    public static final String COURSE = "course";//表字段
    public static final String SCORE = "score";//表字段

    public MySqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + "(" +
                ID + " integer primary key autoincrement," +
                NAME + " varchar(20)," +
                COURSE + " varchar(30)," +
                SCORE + " integer)";
        db.execSQL(sql);//执行SQL语句
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
