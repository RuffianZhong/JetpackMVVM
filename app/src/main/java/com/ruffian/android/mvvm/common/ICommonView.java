package com.ruffian.android.mvvm.common;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

import com.ruffian.android.framework.mvvm.view.IMVVMView;

/**
 * ICommonView 通用/常用 View接口
 * 1.常用 loading 展示/隐藏
 * 2.吐司
 *
 * @author ZhongDaFeng
 */
public interface ICommonView extends IMVVMView {

    /**
     * 展示吐司
     *
     * @param msg 吐司文本
     */
    @UiThread
    void showToast(@NonNull String msg);

}