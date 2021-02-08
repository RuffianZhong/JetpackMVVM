package com.ruffian.android.mvvm.asyns;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJavaObservable
 *
 * @author ZhongDaFeng
 */
public class RxJavaObservable {

    private RxJavaObserver rxJavaObserver;
    private Observable observable;

    /*构造函数*/
    public RxJavaObservable(Observable observable, RxJavaObserver rxJavaObserver) {
        this.rxJavaObserver = rxJavaObserver;
        this.observable = observable;
    }

    /*doOnDispose*/
    private Observable doOnDispose() {
        return observable.doOnDispose(new OnAsyncDisposeAction(rxJavaObserver));
    }

    /*订阅在主线程*/
    public void observe() {
        doOnDispose().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
               // .subscribe(rxJavaObserver);
    }

}
