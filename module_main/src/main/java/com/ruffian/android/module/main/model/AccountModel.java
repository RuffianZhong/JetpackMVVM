package com.ruffian.android.module.main.model;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

import com.ruffian.android.framework.mvvm.model.IModelCallback;
import com.ruffian.android.library.common.async.RxJavaObservable;
import com.ruffian.android.library.common.async.RxJavaObserver;
import com.ruffian.android.library.common.utils.SpManager;
import com.ruffian.android.module.main.entity.UserBean;

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
