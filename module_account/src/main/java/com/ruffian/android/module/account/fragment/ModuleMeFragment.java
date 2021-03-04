package com.ruffian.android.module.account.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ruffian.android.library.common.base.BaseFragment;
import com.ruffian.android.library.common.config.RouterConfig;
import com.ruffian.android.module.account.R;
import com.ruffian.android.module.account.databinding.MeDataBinding;
import com.ruffian.android.module.account.entity.UserBean;
import com.ruffian.android.module.account.presenter.MePresenter;
import com.ruffian.android.module.account.view.IMeView;
import com.ruffian.android.module.account.viewmodel.UserViewModel;

/**
 * ModuleMeFragment
 *
 * @author ZhongDaFeng
 */
@Route(path = RouterConfig.path_fmt_me)
public class ModuleMeFragment extends BaseFragment<IMeView, MePresenter, MeDataBinding> implements IMeView {

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
        /*userViewModel.getUserBean().observe(this, new Observer<UserBean>() {
            @Override
            public void onChanged(UserBean userBean) {
                //userBean变化了   // update UI
                //LogUtils.e("==========userBean变化了=========="+userViewModel.getUserBean().getValue().getUsername());
                //((MainDataBinding) getViewDataBinding()).setUserBean(userViewModel.getUserBean().getValue());
            }
        });*/

        getViewDataBinding().setLifecycleOwner(this);
        getViewDataBinding().setUserViewModel(userViewModel);

        init();
    }


    private void init() {
        if (userViewModel.getUserBean().getValue() == null) {//ViewModel数据不为空（Fragment重建不需要再次获取数据）
            getMVVMPresenter().getUserBean();
        }
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

}