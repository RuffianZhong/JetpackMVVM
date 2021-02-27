package com.ruffian.android.module.main.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ruffian.android.module.main.entity.UserBean;


/**
 * UserViewModel
 *
 * @author ZhongDaFeng
 */
public class UserViewModel extends ViewModel {

    private MutableLiveData<UserBean> userBeanLiveData;

    public MutableLiveData<UserBean> getUserBean() {
        if (userBeanLiveData == null) userBeanLiveData = new MutableLiveData<>();
        return userBeanLiveData;
    }

}
