package com.ruffian.android.mvvm.account;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ruffian.android.mvvm.R;
import com.ruffian.android.mvvm.account.entity.UserBean;
import com.ruffian.android.mvvm.account.presenter.LoginPresenter;
import com.ruffian.android.mvvm.account.view.IAccountView;
import com.ruffian.android.mvvm.account.viewmodel.LoginViewModel;
import com.ruffian.android.mvvm.common.BaseActivity;
import com.ruffian.android.mvvm.databinding.LoginDataBinding;
import com.ruffian.android.mvvm.viewbinding.IEditTextEvent;

/**
 * 登录
 *
 * @author ZhongDaFeng
 */
public class LoginActivity extends BaseActivity<IAccountView.ILoginView, LoginPresenter> implements IEditTextEvent.IAfterTextChanged, IAccountView.ILoginView {

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
        dataBinding.setAccount(new UserBean());
        dataBinding.setAfterTextChanged(this);
        //viewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getUserBean().observe(this, new Observer<UserBean>() {
            @Override
            public void onChanged(UserBean userBean) {
                //userBean变化了
            }
        });

        //login
        dataBinding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = dataBinding.etAccount.getText().toString();
                String psw = dataBinding.etPsw.getText().toString();
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(psw)) return;
                getMVVMPresenter().login(account, psw);
            }
        });
    }

    private void updateUserBean(String account) {
        UserBean newUserBean = new UserBean();
        // newUserBean.setAccount(account);

        //更新UserBean(系统将会回调onChanged)
        loginViewModel.getUserBean().setValue(newUserBean);
    }

    @Override
    public void afterTextChanged(EditText editText, Editable editable) {
        Log.e("tag", "====afterTextChanged====editText" + editText + "===editable===" + editable.toString());
    }


    @Override
    public void loginSuccess(UserBean userBean) {
        if (userBean != null)
            showToast(userBean.getNickname());
    }

    @Override
    public void onError(int code, String desc) {
        showToast(desc);
    }

    @Override
    public void showToast(@NonNull String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
