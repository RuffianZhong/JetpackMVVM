package com.ruffian.android.framework.http.result;

/**
 * IHttpResult
 *
 * @author ZhongDaFeng
 */
public interface IHttpResult {
    public void onSuccess(String data);

    public void onError(int code, String msg);

    public void onCancel();
}