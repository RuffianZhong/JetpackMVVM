package com.ruffian.android.module.account.view;

import com.ruffian.android.library.common.mvvm.ICommonView;
import com.ruffian.android.module.account.entity.UserBean;

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