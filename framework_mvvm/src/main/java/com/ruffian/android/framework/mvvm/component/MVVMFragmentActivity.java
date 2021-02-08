package com.ruffian.android.framework.mvvm.component;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import com.ruffian.android.framework.mvvm.MVVMLifecycle;
import com.ruffian.android.framework.mvvm.delegate.IMVVMDelegate;
import com.ruffian.android.framework.mvvm.presenter.IPresenter;
import com.ruffian.android.framework.mvvm.view.IMVVMView;


/**
 * MVVMFragmentActivity
 * 基础 FragmentActivity（开发者如果不想继承此类可以参考此类，自行实现）
 *
 * @author ZhongDaFeng
 */
public class MVVMFragmentActivity<V extends IMVVMView, P extends IPresenter<V>> extends FragmentActivity implements IMVVMView, IMVVMDelegate<V, P> {

    private MVVMLifecycle<V, P> mMVVMLifecycle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMVVMLifecycle = new MVVMLifecycle<V, P>(this, this);//绑定生命周期
    }

    @Override
    public P makePresenter() {
        return null;//具体业务Activity中根据需要重写
    }

    @Override
    public V getMVVMView() {
        return (V) this;//返回当前View
    }

    @Override
    public P getMVVMPresenter() {
        return mMVVMLifecycle.getPresenter();
    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;//获取生命周期所有者
    }

    @Override
    public Activity getActivity() {
        return this;//获取Activity
    }


}
