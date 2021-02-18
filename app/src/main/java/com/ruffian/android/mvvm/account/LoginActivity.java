package com.ruffian.android.mvvm.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ruffian.android.framework.http.utils.LogUtils;
import com.ruffian.android.mvvm.MainActivity;
import com.ruffian.android.mvvm.R;
import com.ruffian.android.mvvm.account.entity.UserBean;
import com.ruffian.android.mvvm.account.presenter.LoginPresenter;
import com.ruffian.android.mvvm.account.view.IAccountView;
import com.ruffian.android.mvvm.account.viewmodel.LoginViewModel;
import com.ruffian.android.mvvm.common.BaseActivity;
import com.ruffian.android.mvvm.databinding.LoginDataBinding;

/**
 * 登录
 *
 * @author ZhongDaFeng
 */
public class LoginActivity extends BaseActivity<IAccountView.ILoginView, LoginPresenter> implements IAccountView.ILoginView {

    private LoginViewModel loginViewModel;

    @Override
    public LoginPresenter makePresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dataBinding
        final LoginDataBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        dataBinding.setLoginView(this);

        //viewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getUserBean().observe(this, new Observer<UserBean>() {
            @Override
            public void onChanged(UserBean userBean) {
                //userBean变化了
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMVVMPresenter().login("632835821@qq.com", "zhong920306");
    }

    private void updateUserBean(String account) {
        UserBean newUserBean = new UserBean();
        // newUserBean.setAccount(account);

        //更新UserBean(系统将会回调onChanged)
        loginViewModel.getUserBean().setValue(newUserBean);
    }


    @Override
    public void loginSuccess(UserBean userBean) {
        if (userBean != null)
            showToast(userBean.getNickname());

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
