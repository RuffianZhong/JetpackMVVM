package com.ruffian.android.framework.mvvm;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.ruffian.android.framework.mvvm.delegate.IMVVMDelegate;
import com.ruffian.android.framework.mvvm.presenter.IPresenter;
import com.ruffian.android.framework.mvvm.view.IView;

/**
 * MVVMLifecycle MVPVM 生命周期
 *
 * @author ZhongDaFeng
 */
public class MVVMLifecycle<V extends IView, P extends IPresenter<V>> implements LifecycleObserver {

    private IMVVMDelegate<V, P> mMVVMDelegate;
    private P mPresenter;

    public MVVMLifecycle(LifecycleOwner lifecycleOwner, IMVVMDelegate<V, P> mvvmDelegate) {
        this.mMVVMDelegate = mvvmDelegate;
        bindLifecycleOwner(lifecycleOwner);
    }

    /**
     * 创建回调，添加绑定
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        if (mMVVMDelegate == null) return;
        /**
         * 调用创建 Presenter 函数
         */
        mPresenter = mMVVMDelegate.makePresenter();

        /**
         * 获取 View
         */
        V view = mMVVMDelegate.getMVVMView();

        if (mPresenter != null && view != null) {
            //关联view
            mPresenter.attachView(view);
        }
    }

    /**
     * 销毁回调，解除View绑定
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        if (mPresenter != null) mPresenter.detachView();//清空View对象
        mMVVMDelegate = null;//清空'代理'对象
        mPresenter = null;//清空Presenter对象
    }

    /**
     * 获取 Presenter
     *
     * @return
     */
    public P getPresenter() {
        return mPresenter;
    }

    /**
     * 绑定生命周期
     */
    private void bindLifecycleOwner(LifecycleOwner lifecycleOwner) {
        if (lifecycleOwner == null) return;
        lifecycleOwner.getLifecycle().addObserver(this);
    }

}
