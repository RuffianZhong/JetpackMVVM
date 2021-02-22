package com.ruffian.android.framework.http.observe;


import android.text.TextUtils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.ruffian.android.framework.http.result.HttpResult;
import com.ruffian.android.framework.http.disposable.DisposableManager;
import com.ruffian.android.framework.http.disposable.IDisposableCancel;
import com.ruffian.android.framework.http.exception.EventException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * 网络请求Observer(监听者)
 * 备注:多个请求复用一个监听者HttpObserver时，tag会被覆盖，取消回调会有误
 *
 * @author ZhongDaFeng
 */
public class HttpObserver<T> implements Observer<String>, IDisposableCancel, LifecycleObserver {

    /*请求标识*/
    private String mDisposableTag;
    private HttpResult<T> mHttpResult;

    public HttpObserver(String disposableTag, HttpResult<T> httpResult, LifecycleOwner lifecycleOwner) {
        this.mHttpResult = httpResult;
        setDisposableTag(disposableTag);
        bindLifecycleOwner(lifecycleOwner);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        DisposableManager.get().removeDisposable(mDisposableTag);
        if (mHttpResult != null) {
            EventException exception = (EventException) e;
            mHttpResult.onError(exception.getCode(), exception.getMsg());
        }
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onNext(@NonNull String value) {
        DisposableManager.get().removeDisposable(mDisposableTag);
        if (mHttpResult != null) mHttpResult.onSuccess(value);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        DisposableManager.get().addDisposable(mDisposableTag, d);
    }

    /**
     * 手动取消请求/在组件生命周期销毁时自动取消(只有绑定了LifecycleOwner才会自动回调)
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void cancel() {
        //如果没有移除，此时走取消逻辑
        if (!isCanceled()) if (mHttpResult != null) mHttpResult.onCancel();
        DisposableManager.get().removeDisposable(mDisposableTag);
    }

    @Override
    public boolean isCanceled() {
        return DisposableManager.get().isDisposed(mDisposableTag);
    }

    /**
     * 绑定生命周期
     */
    protected void bindLifecycleOwner(LifecycleOwner lifecycleOwner) {
        if (lifecycleOwner != null)
            lifecycleOwner.getLifecycle().addObserver(this);
    }

    /**
     * 设置请求唯一标识
     */
    protected void setDisposableTag(String disposableTag) {
        this.mDisposableTag = TextUtils.isEmpty(disposableTag) ? String.valueOf(System.currentTimeMillis()) : disposableTag;
    }

}
