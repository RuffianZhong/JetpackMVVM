package com.ruffian.android.mvvm.asyns;


import android.text.TextUtils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * RxJavaObserver
 *
 * @author ZhongDaFeng
 */
public class RxJavaObserver<T> implements IAsyncObserver<T>, IAsyncCancel, LifecycleObserver {

    /*请求标识*/
    private String mDisposableTag;
    private Observer<T> mObserver;

    public RxJavaObserver(String disposableTag, Observer<T> observer, LifecycleOwner lifecycleOwner) {
        this.mObserver = observer;
        setDisposableTag(disposableTag);
        bindLifecycleOwner(lifecycleOwner);
    }

    @Override
    public void onError(Throwable e) {
        AsyncManager.get().removeDisposable(mDisposableTag);
        mObserver.onError(e);
    }

    @Override
    public void onComplete() {
        mObserver.onComplete();
    }

    @Override
    public void onNext(@NonNull T value) {
        AsyncManager.get().removeDisposable(mDisposableTag);
        mObserver.onNext(value);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        AsyncManager.get().addDisposable(mDisposableTag, d);
        mObserver.onSubscribe(d);
    }


    /**
     * 手动取消请求/在组件生命周期销毁时自动取消(只有绑定了LifecycleOwner才会自动回调)
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void cancel() {
        //  boolean isDisposed = AsyncManager.get().isDisposed(mDisposableTag);
        //如果没有移除，此时走取消逻辑
        // if (!isDisposed) if (mHttpResult != null) mHttpResult.onCancel();
        AsyncManager.get().removeDisposable(mDisposableTag);
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
