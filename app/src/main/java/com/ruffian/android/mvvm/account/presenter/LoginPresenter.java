package com.ruffian.android.mvvm.account.presenter;

import com.ruffian.android.framework.mvvm.model.IModelCallback;
import com.ruffian.android.framework.mvvm.model.ModelFactory;
import com.ruffian.android.framework.mvvm.presenter.MVVMPresenter;
import com.ruffian.android.mvvm.account.entity.UserBean;
import com.ruffian.android.mvvm.account.model.AccountModel;
import com.ruffian.android.mvvm.account.view.IAccountView;
import com.ruffian.android.mvvm.loading.LoadingManager;

/**
 * LoginPresenter
 * 弹窗使用全局唯一
 *
 * @author ZhongDaFeng
 */
public class LoginPresenter extends MVVMPresenter<IAccountView.ILoginView> {

    /**
     * 登录
     *
     * @param account
     * @param password
     */
    public void login(String account, String password) {
        LoadingManager.get().showProgressDialog();
        ModelFactory.getModel(AccountModel.class).login(account, password, getView().getLifecycleOwner(), new IModelCallback.Http<UserBean>() {
            @Override
            public void onSuccess(UserBean object) {
                LoadingManager.get().dismissLoading();
                if (isAttached()) getView().loginSuccess(object);
            }

            @Override
            public void onError(int code, String msg) {
                LoadingManager.get().dismissLoading();
                if (isAttached()) getView().onError(code, msg);
            }

            @Override
            public void onCancel() {
                LoadingManager.get().dismissLoading();
            }
        });
    }

}