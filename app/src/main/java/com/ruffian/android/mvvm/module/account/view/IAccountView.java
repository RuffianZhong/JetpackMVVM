package com.ruffian.android.mvvm.module.account.view;

import com.ruffian.android.mvvm.module.account.entity.UserBean;
import com.ruffian.android.mvvm.common.ICommonView;

/**
 * IAccountView
 *
 * @author ZhongDaFeng
 */
public interface IAccountView {

    /**
     * 登录 View接口
     */
    interface ILoginView extends ICommonView {
        public void loginSuccess(UserBean userBean);

        public void onError(int code, String desc);

        public void onLoginClickEvent(String account, String psw);
    }

    /**
     * 注册 View接口
     */
    interface IRegisterView extends ICommonView {
    }

}