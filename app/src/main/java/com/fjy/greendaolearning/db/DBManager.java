package com.fjy.greendaolearning.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by fujianyi on 2017/5/4.
 */

public class DBManager {
    private final static String DB_NAME = "push_msg_db";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper helper;

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private DBManager() {
    }

    public void init(Context context) {
        helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    /**
     * 获取单例引用
     *
     * @return
     */
    public static DBManager getInstance() {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager();
                }
            }
        }
        return mInstance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
