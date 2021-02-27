package com.ruffian.android.mvvm;


import android.content.Context;

import androidx.multidex.MultiDex;

import com.ruffian.android.library.common.base.BaseApp;


public class RApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}