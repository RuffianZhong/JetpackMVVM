package com.ruffian.android.mvvm.asyns;

import io.reactivex.functions.Action;

/**
 * OnAsyncDisposeAction
 *
 * @author ZhongDaFeng
 */
public class OnAsyncDisposeAction implements Action {

    private IAsyncCancel asyncCancel;

    public OnAsyncDisposeAction(IAsyncCancel asyncCancel) {
        this.asyncCancel = asyncCancel;
    }

    @Override
    public void run() throws Exception {
        //Dispose
        if (asyncCancel != null) asyncCancel.cancel();
    }
}