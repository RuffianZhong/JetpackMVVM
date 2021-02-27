package com.ruffian.android.module.account.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ruffian.android.library.common.base.BaseActivity;
import com.ruffian.android.library.common.config.RouterConfig;
import com.ruffian.android.library.common.utils.RouterUtils;
import com.ruffian.android.module.account.R;
import com.ruffian.android.module.account.databinding.LoginDataBinding;
import com.ruffian.android.module.account.entity.UserBean;
import com.ruffian.android.module.account.presenter.LoginPresenter;
import com.ruffian.android.module.account.view.IAccountView;

/**
 * 登录
 *
 * @author ZhongDaFeng
 */
@Route(path = RouterConfig.path_login)
public class LoginActivity extends BaseActivity<IAccountView.ILoginView, LoginPresenter, LoginDataBinding> implements IAccountView.ILoginView {


    @Override
    public LoginPresenter makePresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dataBinding
        getViewDataBinding().setLoginView(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }


    @Override
    public void loginSuccess(UserBean userBean) {
        RouterUtils.navigation(RouterConfig.path_main);
    }

    @Override
    public void onError(int code, String desc) {
        showToast(desc);
    }

    @Override
    public void onLoginClickEvent(String account, String psw) {
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(psw)) return;
        getMVVMPresenter().login(account, psw);
    }

    @Override
    public void showToast(@NonNull String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return null;
    }

    @Override
    public Activity getActivity() {
        return null;
    }
}
