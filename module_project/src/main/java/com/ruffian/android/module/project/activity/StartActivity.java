package com.ruffian.android.module.project.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.ruffian.android.library.common.base.BaseActivity;
import com.ruffian.android.library.common.config.RouterConfig;
import com.ruffian.android.library.common.utils.RouterUtils;
import com.ruffian.android.module.project.R;
import com.ruffian.android.module.project.fragment.ModuleProjectFragment;

/**
 * StartActivity
 * 备注：用于模块开发调试时作为Fragment的宿主，属于测试代码
 *
 * @author ZhongDaFeng
 */
public class StartActivity extends BaseActivity {

    private ModuleProjectFragment fragment;

    @Override
    protected int layoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showFragment();
    }

    private void showFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment == null) {
            fragment = RouterUtils.build(RouterConfig.path_fmt_project);
            transaction.add(R.id.layout_content, fragment, "fragment");
        } else {
            transaction.show(fragment);
        }
        transaction.commitAllowingStateLoss();// 提交更改
    }

}