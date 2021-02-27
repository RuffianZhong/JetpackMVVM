package com.ruffian.android.module.sample;

import com.ruffian.android.framework.mvvm.model.IModelCallback;
import com.ruffian.android.framework.mvvm.model.ModelFactory;
import com.ruffian.android.framework.mvvm.presenter.MVVMPresenter;

/**
 * LoginPresenter
 */
public class LoginPresenter extends MVVMPresenter<ILoginView> {

    /**
     * 登录
     */
    public void login(String account, String password) {
        ModelFactory.getModel(LoginModel.class).login(account, password, getView().getLifecycleOwner(), new IModelCallback.Http<UserBean>() {
            @Override
            public void onSuccess(UserBean object) {
                if (!isAttached()) return;
                getView().loginSuccess(object);
            }

            @Override
            public void onError(int code, String msg) {
                if (isAttached()) getView().onError(code, msg);
            }

            @Override
            public void onCancel() {
            }
        });
    }
}