package com.ruffian.android.mvvm;


import android.app.Application;

import com.ruffian.android.framework.http.RHttp;

public class RApp extends Application {

    /*http请求基础路径*/
    public static final String BASE_API = "https://www.wanandroid.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        //必须初始化
        RHttp.Configure.get()
                .baseUrl(BASE_API)                   //基础URL
                .init(this);                        //初始化
    }

}