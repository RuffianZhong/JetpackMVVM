package com.ruffian.android.framework.mvvm.view;

import android.app.Activity;

import androidx.lifecycle.LifecycleOwner;

/**
 * IMVVMView
 *
 * @author ZhongDaFeng
 */
public interface IMVVMView extends IView {
    /**
     * LifecycleOwner
     */
    LifecycleOwner getLifecycleOwner();

    /**
     * Activity
     */
    Activity getActivity();

}
