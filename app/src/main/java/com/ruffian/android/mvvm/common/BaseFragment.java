package com.ruffian.android.mvvm.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.ruffian.android.framework.mvvm.component.MVVMFragment;
import com.ruffian.android.framework.mvvm.presenter.IPresenter;
import com.ruffian.android.framework.mvvm.view.IMVVMView;
import com.ruffian.android.framework.mvvm.viewmodel.IBinding;

/**
 * BaseFragment
 *
 * @author ZhongDaFeng
 */
public abstract class BaseFragment<V extends IMVVMView, P extends IPresenter<V>, T extends ViewDataBinding> extends MVVMFragment<V, P> implements IBinding<T> {

    private View mView;
    private ViewDataBinding mViewDataBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            if (useDataBinding()) {
                mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false);
                mView = mViewDataBinding.getRoot();
            } else {
                mView = View.inflate(getActivity(), layoutId(), null);
            }
        }
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewDataBinding = null;//清除对绑定类实例的所有引用
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
    public T getViewDataBinding() {
        return (T) mViewDataBinding;
    }

    /**
     * 获取View
     *
     * @return
     */
    public View getView() {
        return mView;
    }
}
