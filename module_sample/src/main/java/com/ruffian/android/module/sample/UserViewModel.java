package com.ruffian.android.module.sample;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * UserViewModel
 */
public class UserViewModel extends ViewModel {

    private MutableLiveData<UserBean> userBeanLiveData;

    public MutableLiveData<UserBean> getUserBean() {
        if (userBeanLiveData == null) userBeanLiveData = new MutableLiveData<>();
        return userBeanLiveData;
    }

}
