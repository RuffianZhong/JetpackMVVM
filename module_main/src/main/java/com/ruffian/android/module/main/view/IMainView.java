package com.ruffian.android.module.main.view;

import android.widget.RadioGroup;

import com.ruffian.android.framework.mvvm.view.IMVVMView;

import androidx.annotation.IdRes;

/**
 * IMainView
 *
 * @author ZhongDaFeng
 */
public interface IMainView extends IMVVMView {

    /**
     * Tab选中变化
     *
     * @param group
     * @param checkedId
     */
    public void onTabCheckedChanged(RadioGroup group, @IdRes int checkedId);


}