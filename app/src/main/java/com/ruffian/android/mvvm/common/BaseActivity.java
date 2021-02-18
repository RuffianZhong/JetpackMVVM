package com.ruffian.android.mvvm.common;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ruffian.android.framework.mvvm.component.MVVMFragmentActivity;
import com.ruffian.android.framework.mvvm.presenter.IPresenter;
import com.ruffian.android.framework.mvvm.view.IMVVMView;

/**
 * BaseActivity
 *
 * @author ZhongDaFeng
 */
public class BaseActivity<V extends IMVVMView, P extends IPresenter<V>> extends MVVMFragmentActivity<V, P> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActivityManager.get().push(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.get().remove(this);
    }
}
