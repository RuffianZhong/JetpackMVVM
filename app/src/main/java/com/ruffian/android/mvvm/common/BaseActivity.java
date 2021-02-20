package com.ruffian.android.mvvm.common;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.ruffian.android.framework.mvvm.component.MVVMFragmentActivity;
import com.ruffian.android.framework.mvvm.presenter.IPresenter;
import com.ruffian.android.framework.mvvm.view.IMVVMView;

/**
 * BaseActivity
 *
 * @author ZhongDaFeng
 */
public abstract class BaseActivity<V extends IMVVMView, P extends IPresenter<V>> extends MVVMFragmentActivity<V, P> {

    private ViewDataBinding mViewDataBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActivityManager.get().push(this);
        super.onCreate(savedInstanceState);
        if (useDataBinding()) {
            mViewDataBinding = DataBindingUtil.setContentView(this, layoutId());
        } else {
            setContentView(layoutId());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.get().remove(this);
    }

    /**
     * 提供view资源id
     *
     * @return
     */
    protected abstract int layoutId();

    /**
     * 是否使用数据绑定,子类可重写
     *
     * @return
     */
    protected boolean useDataBinding() {
        return true;
    }

    /**
     * 获取ViewDataBinding
     *
     * @return
     */
    public <T extends ViewDataBinding> T getViewDataBinding() {
        return (T)mViewDataBinding;
    }
}
