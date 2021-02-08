package com.ruffian.android.framework.mvvm.presenter;

import androidx.annotation.NonNull;

import com.ruffian.android.framework.mvvm.view.IView;

/**
 * IPresenter 实现类
 * Presenter 基础类，业务 P 继承此类实现逻辑
 *
 * @author ZhongDaFeng
 */
public class MVVMPresenter<V extends IView> implements IPresenter<V> {

    //View(传递时对象为Activity/Fragment必须要销毁)
    protected V mView;

    @Override
    public void attachView(@NonNull V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    /**
     * 获取View
     * 备注： getView() 在组件销毁后可能为 null 使用前通过 isAttached() 判断
     *
     * @return
     */
    public V getView() {
        return mView;
    }

    /**
     * View是否已经绑定
     *
     * @return
     */
    public boolean isAttached() {
        return mView != null;
    }

}
