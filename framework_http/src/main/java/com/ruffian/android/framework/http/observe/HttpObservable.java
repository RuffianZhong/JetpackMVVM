package com.ruffian.android.framework.http.observe;

import com.ruffian.android.framework.http.function.HttpResultFunction;
import com.ruffian.android.framework.http.function.OnDisposeAction;
import com.ruffian.android.framework.http.function.ServerResultFunction;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 网络请求Observable(被监听者)
 * <p>
 * 调度顺序
 * 1.map()
 * 2.onErrorResumeNext()
 * 3.doOnDispose()
 * 4.observe()
 *
 * @author ZhongDaFeng
 */
public class HttpObservable {

    private HttpObserver httpObserver;
    private Observable apiObservable;

    /*构造函数*/
    public HttpObservable(Observable apiObservable, HttpObserver httpObserver) {
        this.httpObserver = httpObserver;
        this.apiObservable = apiObservable;
    }

    /*map*/
    private Observable map() {
        return apiObservable.map(new ServerResultFunction());
    }

    /*onErrorResumeNext*/
    private Observable onErrorResumeNext() {
        return map().onErrorResumeNext(new HttpResultFunction<>());
    }

    /*doOnDispose*/
    private Observable doOnDispose() {
        return onErrorResumeNext().doOnDispose(new OnDisposeAction(httpObserver));
    }

    /*线程设置*/
    public void observe() {
        doOnDispose().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpObserver);
    }

}
