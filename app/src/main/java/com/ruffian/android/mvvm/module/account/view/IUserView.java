package com.ruffian.android.mvvm.module.account.view;

import android.widget.ImageView;

import com.ruffian.android.framework.mvvm.view.IMVVMView;

/**
 * IUserView
 *
 * @author ZhongDaFeng
 */
public interface IUserView {

    /**
     * 用户信息 View接口
     */
    interface IUserInfoView extends IMVVMView {

        public void loadUserIcon(ImageView imageView, String url);
    }

}