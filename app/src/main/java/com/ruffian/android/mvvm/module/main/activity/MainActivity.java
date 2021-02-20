package com.ruffian.android.mvvm.module.main.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.ruffian.android.framework.http.utils.LogUtils;
import com.ruffian.android.mvvm.R;
import com.ruffian.android.mvvm.common.BaseActivity;
import com.ruffian.android.mvvm.databinding.MainDataBinding;
import com.ruffian.android.mvvm.module.account.entity.UserBean;
import com.ruffian.android.mvvm.module.account.viewmodel.UserViewModel;
import com.ruffian.android.mvvm.module.main.fragment.ModuleKnowledgeFragment;
import com.ruffian.android.mvvm.module.main.fragment.ModuleMainFragment;
import com.ruffian.android.mvvm.module.main.fragment.ModuleNavigationFragment;
import com.ruffian.android.mvvm.module.main.fragment.ModuleOfficialAccountFragment;
import com.ruffian.android.mvvm.module.main.fragment.ModuleProjectFragment;
import com.ruffian.android.mvvm.module.main.presenter.MainPresenter;
import com.ruffian.android.mvvm.module.main.view.IMainView;
import com.ruffian.android.mvvm.utils.AppUtils;

public class MainActivity extends BaseActivity<IMainView, MainPresenter> implements IMainView {

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

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSavedInstanceState = savedInstanceState;
        //viewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
       /* userViewModel.getUserBean().observe(this, new Observer<UserBean>() {
            @Override
            public void onChanged(UserBean userBean) {
                //userBean变化了   // update UI
                //LogUtils.e("==========userBean变化了==========");
                //((MainDataBinding) getViewDataBinding()).setUserBean(userViewModel.getUserBean().getValue());
            }
        });*/


        getViewDataBinding().setLifecycleOwner(this);
        ((MainDataBinding) getViewDataBinding()).setUserViewModel(userViewModel);
        ((MainDataBinding) getViewDataBinding()).setMainView(this);
        init();
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

    private void init() {
        getMVVMPresenter().getUserBean();
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
    public void onTabCheckedChanged(RadioGroup group, int checkedId) {
        int position = 0;
        switch (checkedId) {
            case R.id.tab_main:
                position = 0;
                break;
            case R.id.tab_knowledge:
                position = 1;
                break;
            case R.id.tab_official_account:
                position = 2;
                break;
            case R.id.tab_navigation:
                position = 3;
                break;
            case R.id.tab_project:
                position = 4;
                break;
        }
        showTabView(position);
    }

    @Override
    public void onUserInfoGot(UserBean userBean) {
        //((MainDataBinding) getViewDataBinding()).setUserBean(userBean);
        //更新 ViewModel (系统将会回调onChanged/直接更新UI)
        userViewModel.getUserBean().setValue(userBean);
    }

    @Override
    public MainPresenter makePresenter() {
        return new MainPresenter();
    }

}