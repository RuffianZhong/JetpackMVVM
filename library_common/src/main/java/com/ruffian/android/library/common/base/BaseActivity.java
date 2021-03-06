package com.ruffian.android.library.common.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.ruffian.android.framework.mvvm.component.MVVMFragmentActivity;
import com.ruffian.android.framework.mvvm.presenter.IPresenter;
import com.ruffian.android.framework.mvvm.view.IMVVMView;
import com.ruffian.android.framework.mvvm.viewmodel.IBinding;
import com.ruffian.android.library.common.manager.ActivityManager;
import com.ruffian.android.library.common.utils.RouterUtils;

/**
 * BaseActivity
 *
 * @author ZhongDaFeng
 */
public abstract class BaseActivity<V extends IMVVMView, P extends IPresenter<V>, T extends ViewDataBinding> extends MVVMFragmentActivity<V, P> implements IBinding<T> {

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
        RouterUtils.inject(this);
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
    @Override
    public T getViewDataBinding() {
        return (T) mViewDataBinding;
    }
}
