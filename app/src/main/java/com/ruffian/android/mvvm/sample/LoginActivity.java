package com.ruffian.android.mvvm.sample;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.ruffian.android.mvvm.R;
import com.ruffian.android.mvvm.common.BaseActivity;
import com.ruffian.android.mvvm.databinding.SampleLoginDataBinding;

/**
 * LoginActivity
 */
public class LoginActivity extends BaseActivity<ILoginView, LoginPresenter, ViewDataBinding> implements ILoginView {

    private UserViewModel userViewModel;
    private SampleLoginDataBinding dataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dataBinding
        dataBinding = DataBindingUtil.setContentView(this, layoutId());
        //ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        getViewDataBinding().setLifecycleOwner(this);
        dataBinding.setUserViewModel(userViewModel);

        //登录
        getMVVMPresenter().login("aaa", "xxx");
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
    }

    @Override
    public void onError(int code, String desc) {
        //update ui
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }
}
