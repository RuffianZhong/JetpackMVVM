package com.ruffian.android.framework.http;

/**
 * HttpCallback
 *
 * @author ZhongDaFeng
 */
public interface HttpCallback<T> {
    public void onSuccess(T object);

    public void onError(int code, String msg);

    public void onCancel();
}