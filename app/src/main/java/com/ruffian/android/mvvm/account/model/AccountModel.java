package com.ruffian.android.mvvm.account.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.ruffian.android.framework.http.HttpCallback;
import com.ruffian.android.framework.mvvm.model.IModelCallback;
import com.ruffian.android.mvvm.account.biz.AccountBiz;
import com.ruffian.android.mvvm.account.entity.UserBean;
import com.ruffian.android.mvvm.common.BizFactory;
import com.ruffian.android.mvvm.common.Response;
import com.ruffian.android.mvvm.utils.SpManager;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * AccountModel
 *
 * @author ZhongDaFeng
 */
public class AccountModel {

    public final String key_user_cache = "key_user_info";

    public void login(String account, String password, LifecycleOwner lifecycle, final IModelCallback.Http<UserBean> modelCallback) {
        BizFactory.getBiz(AccountBiz.class).login(account, password, lifecycle, new HttpCallback<Response<UserBean>>() {

            @Override
            public void onSuccess(Response<UserBean> value) {
                //回调给Presenter
                modelCallback.onSuccess(value.getData());
            }

            @Override
            public void onError(int code, String desc) {
                //回调给Presenter
                modelCallback.onError(code, desc);
            }

            @Override
            public void onCancel() {
                //回调给Presenter
                modelCallback.onCancel();
            }
        });
    }


    public void getLocalCache(final Context context, LifecycleOwner lifecycle, final IModelCallback.Data<UserBean> modelCallback) {
        //RxJava异步解析本地数据
        Observable.create(new ObservableOnSubscribe<UserBean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<UserBean> e) throws Exception {
                //工作线程获取并解析数据
                UserBean userBean = SpManager.getAccount(context).getObject(key_user_cache, UserBean.class);
                e.onNext(userBean);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())//工作线程
                .observeOn(AndroidSchedulers.mainThread())
                //   .compose(lifecycle.<String>bindToLifecycle())//绑定生命周期
                .subscribe(new Observer<UserBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull UserBean userBean) {
                        modelCallback.onSuccess(userBean);  //回调给Presenter
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        modelCallback.onSuccess(new UserBean());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void saveLocalCache(Context context, UserBean data) {
        SpManager.getAccount(context).put(key_user_cache, data);
    }
}
