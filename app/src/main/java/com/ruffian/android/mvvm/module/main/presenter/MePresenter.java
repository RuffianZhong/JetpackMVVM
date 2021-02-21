package com.ruffian.android.mvvm.module.main.presenter;

import com.ruffian.android.framework.mvvm.model.IModelCallback;
import com.ruffian.android.framework.mvvm.model.ModelFactory;
import com.ruffian.android.framework.mvvm.presenter.MVVMPresenter;
import com.ruffian.android.mvvm.module.account.entity.UserBean;
import com.ruffian.android.mvvm.module.account.model.AccountModel;
import com.ruffian.android.mvvm.module.main.view.IMeView;

/**
 * MePresenter
 *
 * @author ZhongDaFeng
 */
public class MePresenter extends MVVMPresenter<IMeView> {

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