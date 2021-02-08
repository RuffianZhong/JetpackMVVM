package com.ruffian.android.framework.http.function;

import com.ruffian.android.framework.http.disposable.IDisposableCancel;

import io.reactivex.functions.Action;

/**
 * OnDisposableCancel
 *
 * @author ZhongDaFeng
 */
public class OnDisposeAction implements Action {

    private IDisposableCancel disposableCancel;

    public OnDisposeAction(IDisposableCancel disposableCancel) {
        this.disposableCancel = disposableCancel;
    }

    @Override
    public void run() throws Exception {
        //Dispose
        if (disposableCancel != null) disposableCancel.cancel();
    }
}