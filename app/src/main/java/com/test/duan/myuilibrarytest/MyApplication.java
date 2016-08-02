package com.test.duan.myuilibrarytest;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Duanyy on 2016/8/2.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
