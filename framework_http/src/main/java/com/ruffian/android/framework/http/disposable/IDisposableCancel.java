package com.ruffian.android.framework.http.disposable;

/**
 * 调用取消接口定义
 *
 * @author ZhongDaFeng
 */
public interface IDisposableCancel {

    public void cancel();

    public boolean isCanceled();
}