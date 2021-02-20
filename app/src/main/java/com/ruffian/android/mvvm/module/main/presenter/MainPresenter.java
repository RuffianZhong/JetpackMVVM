package com.ruffian.android.mvvm.module.main.presenter;

import com.ruffian.android.framework.mvvm.model.IModelCallback;
import com.ruffian.android.framework.mvvm.model.ModelFactory;
import com.ruffian.android.framework.mvvm.presenter.MVVMPresenter;
import com.ruffian.android.mvvm.module.main.view.IMainView;
import com.ruffian.android.mvvm.module.account.entity.UserBean;
import com.ruffian.android.mvvm.module.account.model.AccountModel;

/**
 * MainPresenter
 *
 * @author ZhongDaFeng
 */
public class MainPresenter extends MVVMPresenter<IMainView> {

    public void getUserBean() {
        ModelFactory.getModel(AccountModel.class).getUserLocalCache(getView().getActivity(), getView().getLifecycleOwner(), new IModelCallback.Data<UserBean>() {
            @Override
            public void onSuccess(UserBean object) {
                if (!isAttached()) return;
                getView().onUserInfoGot(object);
            }
        });
    }

}