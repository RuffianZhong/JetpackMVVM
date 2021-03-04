package com.ruffian.android.module.main.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.ruffian.android.library.common.config.RouterConfig;
import com.ruffian.android.library.common.utils.RouterUtils;

/**
 * SplashActivity启动闪屏页面
 * 根据业务需求实现具体功能，此处直接跳转到登录页面
 *
 * @author ZhongDaFeng
 */
public class SplashActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RouterUtils.navigation(RouterConfig.path_act_login);
        finish();
    }

}