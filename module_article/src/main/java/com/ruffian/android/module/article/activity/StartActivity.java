package com.ruffian.android.module.article.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.ruffian.android.framework.mvvm.presenter.IPresenter;
import com.ruffian.android.framework.mvvm.view.IMVVMView;
import com.ruffian.android.library.common.base.BaseActivity;
import com.ruffian.android.library.common.config.RouterConfig;
import com.ruffian.android.library.common.utils.RouterUtils;
import com.ruffian.android.module.article.R;
import com.ruffian.android.module.article.databinding.StartDataBinding;
import com.ruffian.android.module.article.fragment.ModuleMainFragment;
import com.ruffian.android.module.article.fragment.ModuleOfficialAccountFragment;

/**
 * StartActivity
 * 备注：用于模块开发调试时作为Fragment的宿主，属于测试代码
 *
 * @author ZhongDaFeng
 */
public class StartActivity extends BaseActivity<IMVVMView, IPresenter<IMVVMView>, StartDataBinding> {

    private ModuleMainFragment mainFragment;
    private ModuleOfficialAccountFragment officialAccountFragment;

    @Override
    protected int layoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewDataBinding().tvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(0);
            }
        });
        getViewDataBinding().tvOffAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(1);
            }
        });
    }


    private void showFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideTabViews(transaction);
        switch (position) {
            case 0:
                if (mainFragment == null) {
                    mainFragment = RouterUtils.build(RouterConfig.path_fmt_main);
                    transaction.add(R.id.layout_content, mainFragment, "mainFragment");
                } else {
                    transaction.show(mainFragment);
                }
                break;
            case 1:
                if (officialAccountFragment == null) {
                    officialAccountFragment = RouterUtils.build(RouterConfig.path_fmt_official_account);
                    transaction.add(R.id.layout_content, officialAccountFragment, "officialAccountFragment");
                } else {
                    transaction.show(officialAccountFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();// 提交更改
    }

    private void hideTabViews(FragmentTransaction transaction) {
        if (transaction != null) {
            if (mainFragment != null) transaction.hide(mainFragment);
            if (officialAccountFragment != null) transaction.hide(officialAccountFragment);
        }
    }
}