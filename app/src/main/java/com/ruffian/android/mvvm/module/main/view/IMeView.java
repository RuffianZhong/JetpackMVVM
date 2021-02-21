package com.ruffian.android.mvvm.module.main.view;

import com.ruffian.android.framework.mvvm.view.IMVVMView;
import com.ruffian.android.mvvm.module.account.entity.UserBean;

/**
 * IMeView
 *
 * @author ZhongDaFeng
 */
public interface IMeView extends IMVVMView {

    /**
     * 用户信息加载成功
     *
     * @param userBean
     */
    public void onUserInfoGot(UserBean userBean);


}