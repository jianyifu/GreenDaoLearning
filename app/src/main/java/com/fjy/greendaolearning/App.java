package com.fjy.greendaolearning;

import android.app.Application;

import com.fjy.greendaolearning.db.DBManager;

/**
 * Created by fujianyi on 2017/5/4.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.getInstance().init(this);
    }
}
