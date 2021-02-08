package com.ruffian.android.mvvm.account.view;

import com.ruffian.android.mvvm.account.entity.UserBean;
import com.ruffian.android.mvvm.common.ICommonView;

/**
 * IAccountView
 *
 * @author ZhongDaFeng
 * @date 2021/2/3 11:58
 */
public interface IAccountView {

    /**
     * 登录 View接口
     */
    interface ILoginView extends ICommonView {
        public void loginSuccess(UserBean userBean);

        public void onError(int code, String desc);
    }

    /**
     * 注册 View接口
     */
    interface IRegisterView extends ICommonView {
    }

}