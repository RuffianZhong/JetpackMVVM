package com.ruffian.android.mvvm.module.account.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ruffian.android.mvvm.module.main.activity.MainActivity;
import com.ruffian.android.mvvm.R;
import com.ruffian.android.mvvm.module.account.entity.UserBean;
import com.ruffian.android.mvvm.module.account.presenter.LoginPresenter;
import com.ruffian.android.mvvm.module.account.view.IAccountView;
import com.ruffian.android.mvvm.common.BaseActivity;
import com.ruffian.android.mvvm.databinding.LoginDataBinding;
import com.ruffian.android.mvvm.utils.IntentUtils;

/**
 * 登录
 *
 * @author ZhongDaFeng
 */
public class LoginActivity extends BaseActivity<IAccountView.ILoginView, LoginPresenter> implements IAccountView.ILoginView {


    @Override
    public LoginPresenter makePresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dataBinding
        ((LoginDataBinding) getViewDataBinding()).setLoginView(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }


    @Override
    public void loginSuccess(UserBean userBean) {
        IntentUtils.startActivity(this, MainActivity.class);
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

}
