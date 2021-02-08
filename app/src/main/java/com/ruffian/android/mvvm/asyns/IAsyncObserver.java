package com.ruffian.android.mvvm.asyns;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * IAsyncObserver
 *
 * @author ZhongDaFeng
 */
public interface IAsyncObserver<T> {

    void onSubscribe(@NonNull Disposable d);

    void onNext(@NonNull T t);

    void onError(@NonNull Throwable e);

    void onComplete();

}