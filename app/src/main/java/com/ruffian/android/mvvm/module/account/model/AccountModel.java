package com.ruffian.android.mvvm.module.account.model;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

import com.ruffian.android.framework.http.HttpCallback;
import com.ruffian.android.framework.mvvm.model.IModelCallback;
import com.ruffian.android.mvvm.module.account.biz.AccountBiz;
import com.ruffian.android.mvvm.module.account.entity.UserBean;
import com.ruffian.android.mvvm.asyns.RxJavaObservable;
import com.ruffian.android.mvvm.asyns.RxJavaObserver;
import com.ruffian.android.mvvm.common.BizFactory;
import com.ruffian.android.mvvm.common.Response;
import com.ruffian.android.mvvm.utils.SpManager;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * AccountModel
 *
 * @author ZhongDaFeng
 */
public class AccountModel {

    public final String key_user_cache = "key_user_info";

    /**
     * 登录
     *
     * @param account
     * @param password
     * @param lifecycle
     * @param modelCallback
     */
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


    /**
     * 获取用户缓存信息
     *
     * @param context
     * @param lifecycleOwner
     * @param modelCallback
     */
    public void getUserLocalCache(final Context context, final LifecycleOwner lifecycleOwner, final IModelCallback.Data<UserBean> modelCallback) {
        //RxJava异步解析本地数据
        new RxJavaObservable(Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                //工作线程获取并解析数据
                UserBean userBean = SpManager.getAccount(context).getObject(key_user_cache, UserBean.class);
                emitter.onNext(userBean);
                emitter.onComplete();
            }
        }), new RxJavaObserver<UserBean>(lifecycleOwner) {
            @Override
            public void onNext(UserBean value) {
                super.onNext(value);
                modelCallback.onSuccess(value);  //回调
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                modelCallback.onSuccess(new UserBean());  //回调
            }
        }).subscribe();
    }

    /**
     * 缓存用户信息
     *
     * @param context
     * @param lifecycleOwner
     * @param data
     */
    public void saveUserLocalCache(final Context context, final LifecycleOwner lifecycleOwner, final UserBean data) {
        new RxJavaObservable(Observable.create(new ObservableOnSubscribe<UserBean>() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                data.setIcon("https://avatars.githubusercontent.com/u/7261019?s=400&u=7e5046d2505ed6f8e2c168bb6f6dfc5df108b63f&v=4");
                SpManager.getAccount(context).put(key_user_cache, data);
                emitter.onNext(true);
                emitter.onComplete();
            }
        }), new RxJavaObserver(lifecycleOwner)).subscribe();
    }
}
