package com.ruffian.android.mvvm.sample;

import com.ruffian.android.framework.mvvm.view.IMVVMView;

/**
 * ILoginView
 */
public interface ILoginView extends IMVVMView {
    public void loginSuccess(UserBean userBean);

    public void onError(int code, String desc);
}