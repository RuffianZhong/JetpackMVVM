package com.ruffian.android.module.sample;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.ruffian.android.library.common.base.BaseActivity;
import com.ruffian.android.module.sample.databinding.SampleLoginDataBinding;

/**
 * LoginActivity
 */
public class LoginActivity extends BaseActivity<ILoginView, LoginPresenter, SampleLoginDataBinding> implements ILoginView {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        getViewDataBinding().setLifecycleOwner(this);
        getViewDataBinding().setUserViewModel(userViewModel);

        //登录
        getMVVMPresenter().login("ruffian", "test12345");
    }

    @Override
    protected int layoutId() {
        return R.layout.layout_sample;
    }

    @Override
    public LoginPresenter makePresenter() {
        return new LoginPresenter();
    }

    @Override
    public void loginSuccess(UserBean userBean) {
        //update ui
        //findViewById(R.id.tv_user_name).setText(userBean.getNickname());//传统方式更新UI
        //dataBinding.tvUserName.setText(userBean.getNickname());//dataBinding获取控件更新UI
        userViewModel.getUserBean().setValue(userBean);//ViewModel绑定数据直接更新UI
        Toast.makeText(this, "欢迎您，" + userBean.getNickname(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(int code, String desc) {
        //update ui
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }
}
