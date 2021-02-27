package com.ruffian.android.library.common.async;

import io.reactivex.disposables.Disposable;

/**
 * 调用管理接口定义
 *
 * @author ZhongDaFeng
 */
public interface IAsyncManager {

    /**
     * 添加
     */
    void addDisposable(Object tag, Disposable disposable);

    /**
     * 取消/移除
     */
    void removeDisposable(Object tag);

    /**
     * 取消全部
     */
    void removeAll();

    /**
     * 是否已经处理
     */
    boolean isDisposed(Object tag);
}