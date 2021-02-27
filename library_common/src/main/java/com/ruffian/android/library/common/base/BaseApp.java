package com.ruffian.android.library.common.base;

import android.app.Application;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ruffian.android.framework.http.RHttp;
import com.ruffian.android.library.common.BuildConfig;

/**
 * BaseApp
 *
 * @author ZhongDaFeng
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initRHttp();
        initARouter();
    }

    /**
     * 初始化 ARouter
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) {    // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    /**
     * 初始化 RHttp
     */
    private void initRHttp() {
        /*http请求基础路径*/
        final String BASE_API = "https://www.wanandroid.com/";

        //必须初始化
        RHttp.Configure.get()
                .baseUrl(BASE_API)                   //基础URL
                .showLog(BuildConfig.DEBUG)         //日志
                .init(this);                        //初始化
    }

}