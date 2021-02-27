package com.ruffian.android.module.main.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ruffian.android.framework.mvvm.presenter.MVVMPresenter;
import com.ruffian.android.framework.mvvm.view.IMVVMView;
import com.ruffian.android.library.common.base.BaseActivity;
import com.ruffian.android.library.common.config.RouterConfig;
import com.ruffian.android.library.common.utils.AppUtils;
import com.ruffian.android.module.main.R;
import com.ruffian.android.module.main.databinding.MainDataBinding;
import com.ruffian.android.module.main.fragment.ModuleKnowledgeFragment;
import com.ruffian.android.module.main.fragment.ModuleMainFragment;
import com.ruffian.android.module.main.fragment.ModuleMeFragment;
import com.ruffian.android.module.main.fragment.ModuleOfficialAccountFragment;
import com.ruffian.android.module.main.fragment.ModuleProjectFragment;
import com.ruffian.android.module.main.view.IMainView;

/**
 * 主页
 */
@Route(path = RouterConfig.path_main)
public class MainActivity extends BaseActivity<IMVVMView, MVVMPresenter<IMVVMView>, MainDataBinding> implements IMainView {

    private static final String KEY_TAG = "fragment_tag";
    private final String[] mFragmentTags = new String[]{"fragment_tags_0", "fragment_tags_1", "fragment_tags_2", "fragment_tags_3", "fragment_tags_4"};

    private Bundle mSavedInstanceState;

    private int mCachePosition = -1;
    private ModuleMainFragment mainFragment;
    private ModuleKnowledgeFragment knowledgeFragment;
    private ModuleOfficialAccountFragment officialAccountFragment;
    private ModuleProjectFragment projectFragment;
    private ModuleMeFragment meFragment;

    //上次回退事件时间
    private long mLastBackEventTime = 0;
    private static final long TARGET_EXIT_TIME = 1000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSavedInstanceState = savedInstanceState;

        getViewDataBinding().setLifecycleOwner(this);
        getViewDataBinding().setMainView(this);
        initShowTabView();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_TAG, mCachePosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCachePosition = savedInstanceState.getInt(KEY_TAG);
        showTabView(mCachePosition);
    }

    private void initShowTabView() {
        if (mSavedInstanceState != null) { //Activity被重新创建
            mCachePosition = mSavedInstanceState.getInt(KEY_TAG);
            FragmentManager fragmentManager = getSupportFragmentManager();
            mainFragment = (ModuleMainFragment) fragmentManager.findFragmentByTag(mFragmentTags[0]);
            knowledgeFragment = (ModuleKnowledgeFragment) fragmentManager.findFragmentByTag(mFragmentTags[1]);
            officialAccountFragment = (ModuleOfficialAccountFragment) fragmentManager.findFragmentByTag(mFragmentTags[2]);
            projectFragment = (ModuleProjectFragment) fragmentManager.findFragmentByTag(mFragmentTags[3]);
            meFragment = (ModuleMeFragment) fragmentManager.findFragmentByTag(mFragmentTags[4]);
        }
        showTabView(0);
    }

    private void showTabView(int position) {
        if (mCachePosition == position) return;
        if (mCachePosition == -1) mCachePosition = 0;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideTabViews(transaction);
        switch (position) {
            case 0://ModuleMainFragment
                if (mainFragment == null) {
                    mainFragment = new ModuleMainFragment();
                    transaction.add(R.id.layout_content, mainFragment, mFragmentTags[position]);
                } else {
                    transaction.show(mainFragment);
                }
                break;
            case 1://ModuleKnowledgeFragment
                if (knowledgeFragment == null) {
                    knowledgeFragment = new ModuleKnowledgeFragment();
                    transaction.add(R.id.layout_content, knowledgeFragment, mFragmentTags[position]);
                } else {
                    transaction.show(knowledgeFragment);
                }
                break;
            case 2://ModuleOfficialAccountFragment
                if (officialAccountFragment == null) {
                    officialAccountFragment = new ModuleOfficialAccountFragment();
                    transaction.add(R.id.layout_content, officialAccountFragment, mFragmentTags[position]);
                } else {
                    transaction.show(officialAccountFragment);
                }
                break;
            case 3://ModuleProjectFragment
                if (projectFragment == null) {
                    projectFragment = new ModuleProjectFragment();
                    transaction.add(R.id.layout_content, projectFragment, mFragmentTags[position]);
                } else {
                    transaction.show(projectFragment);
                }
                break;
            case 4://ModuleMeFragment
                if (meFragment == null) {
                    meFragment = new ModuleMeFragment();
                    transaction.add(R.id.layout_content, meFragment, mFragmentTags[position]);
                } else {
                    transaction.show(meFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();// 提交更改
        mCachePosition = position;
    }

    private void hideTabViews(FragmentTransaction transaction) {
        if (transaction != null) {
            if (mainFragment != null) transaction.hide(mainFragment);
            if (knowledgeFragment != null) transaction.hide(knowledgeFragment);
            if (officialAccountFragment != null) transaction.hide(officialAccountFragment);
            if (meFragment != null) transaction.hide(meFragment);
            if (projectFragment != null) transaction.hide(projectFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - mLastBackEventTime) >= TARGET_EXIT_TIME) {
                mLastBackEventTime = currentTime;
                Toast.makeText(this, R.string.tips_exit_confirm, Toast.LENGTH_LONG).show();
            } else {
                AppUtils.exit();
            }
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onTabCheckedChanged(RadioGroup group, int checkedId) {
        //作为library时无法使用switch(此时R.id.tab_xx不是final常量)
        int position = 0;
        if (checkedId == R.id.tab_main) position = 0;
        if (checkedId == R.id.tab_knowledge) position = 1;
        if (checkedId == R.id.tab_official_account) position = 2;
        if (checkedId == R.id.tab_project) position = 3;
        if (checkedId == R.id.tab_me) position = 4;
        showTabView(position);
    }

}