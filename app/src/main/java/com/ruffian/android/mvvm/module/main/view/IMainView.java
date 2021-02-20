package com.ruffian.android.mvvm.module.main.view;

import android.widget.RadioGroup;

import androidx.annotation.IdRes;

import com.ruffian.android.framework.mvvm.view.IMVVMView;
import com.ruffian.android.mvvm.module.account.entity.UserBean;

/**
 * IMainView
 *
 * @author ZhongDaFeng
 */
public interface IMainView extends IMVVMView {

    /**
     * Tab选中变化
     *
     * @param group
     * @param checkedId
     */
    public void onTabCheckedChanged(RadioGroup group, @IdRes int checkedId);

    /**
     * 用户信息加载成功
     *
     * @param userBean
     */
    public void onUserInfoGot(UserBean userBean);


}