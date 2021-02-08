package com.ruffian.android.mvvm.account.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ruffian.android.mvvm.account.entity.UserBean;

/**
 * 登录ViewModel
 *
 * @author ZhongDaFeng
 */
public class LoginViewModel extends ViewModel {

    private MutableLiveData<UserBean> userBeanLiveData;

    public MutableLiveData<UserBean> getUserBean() {
        if (userBeanLiveData == null) userBeanLiveData = new MutableLiveData<>();
        return userBeanLiveData;
    }

}
