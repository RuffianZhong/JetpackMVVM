package com.ruffian.android.mvvm;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ruffian.android.mvvm.common.BaseActivity;
import com.ruffian.android.mvvm.databinding.MainDataBinding;
import com.ruffian.android.mvvm.utils.AppUtils;

public class MainActivity extends BaseActivity implements IMainView {

    private static final String KEY_TAG = "fragment_tag";
    private final String[] mFragmentTags = new String[]{"fragment_tags_0", "fragment_tags_1", "fragment_tags_2", "fragment_tags_3", "fragment_tags_4"};

    private Bundle mSavedInstanceState;

    private int mCachePosition = 0;
    private ModuleMainFragment mainFragment;
    private ModuleKnowledgeFragment knowledgeFragment;
    private ModuleOfficialAccountFragment officialAccountFragment;
    private ModuleNavigationFragment navigationFragment;
    private ModuleProjectFragment projectFragment;

    //上次回退事件时间
    private long mLastBackEventTime = 0;
    private static final long TARGET_EXIT_TIME = 1000L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSavedInstanceState = savedInstanceState;
        MainDataBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        dataBinding.setMainView(this);
        //  setContentView(R.layout.activity_main);

        initShowTabView();
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
            navigationFragment = (ModuleNavigationFragment) fragmentManager.findFragmentByTag(mFragmentTags[3]);
            projectFragment = (ModuleProjectFragment) fragmentManager.findFragmentByTag(mFragmentTags[4]);
        }
        showTabView(mCachePosition);
    }

    private void showTabView(int position) {
        if (mCachePosition == position) return;
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
            case 3://ModuleNavigationFragment
                if (navigationFragment == null) {
                    navigationFragment = new ModuleNavigationFragment();
                    transaction.add(R.id.layout_content, navigationFragment, mFragmentTags[position]);
                } else {
                    transaction.show(navigationFragment);
                }
                break;
            case 4://ModuleProjectFragment
                if (projectFragment == null) {
                    projectFragment = new ModuleProjectFragment();
                    transaction.add(R.id.layout_content, projectFragment, mFragmentTags[position]);
                } else {
                    transaction.show(projectFragment);
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
            if (navigationFragment != null) transaction.hide(navigationFragment);
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
    public void onTabClickEvent(int position) {
        showTabView(position);
    }
}