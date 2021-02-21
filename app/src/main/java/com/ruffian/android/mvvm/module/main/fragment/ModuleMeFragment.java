package com.ruffian.android.mvvm.module.main.fragment;

import android.os.Bundle;
import android.view.View;

import com.ruffian.android.mvvm.R;
import com.ruffian.android.mvvm.common.BaseFragment;
import com.ruffian.android.mvvm.databinding.FmtMainDataBinding;
import com.ruffian.android.mvvm.databinding.MainDataBinding;
import com.ruffian.android.mvvm.databinding.MeDataBinding;
import com.ruffian.android.mvvm.module.account.entity.UserBean;
import com.ruffian.android.mvvm.module.account.viewmodel.UserViewModel;
import com.ruffian.android.mvvm.module.main.presenter.MePresenter;
import com.ruffian.android.mvvm.module.main.view.IMeView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

/**
 * ModuleMeFragment
 *
 * @author ZhongDaFeng
 */
public class ModuleMeFragment extends BaseFragment<IMeView, MePresenter> implements IMeView {

    private UserViewModel userViewModel;

    @Override
    protected int layoutId() {
        return R.layout.fmt_module_me;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        getViewDataBinding().setUserViewModel(userViewModel);

        init();
    }


    private void init() {
        getMVVMPresenter().getUserBean();
    }

    @Override
    public void onUserInfoGot(UserBean userBean) {
        //((MainDataBinding) getViewDataBinding()).setUserBean(userBean);
        //更新 ViewModel (系统将会回调onChanged/直接更新UI)
        userViewModel.getUserBean().setValue(userBean);
    }

    @Override
    public MePresenter makePresenter() {
        return new MePresenter();
    }

    @Override
    public MeDataBinding getViewDataBinding() {
        return (MeDataBinding) super.getViewDataBinding();
    }
}