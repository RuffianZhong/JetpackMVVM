package com.ruffian.android.framework.http.result;

import com.google.gson.Gson;
import com.ruffian.android.framework.http.HttpCallback;
import com.ruffian.android.framework.http.IResponse;
import com.ruffian.android.framework.http.exception.ExceptionEngine;
import com.ruffian.android.framework.http.parse.IParseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * HttpResult
 *
 * @author ZhongDaFeng
 */
public class HttpResult<T> implements IHttpResult, IParseHelper {

    protected HttpCallback<T> mIHttpCallback;

    public HttpResult(HttpCallback<T> httpCallback) {
        this.mIHttpCallback = httpCallback;
    }

    @Override
    public void onSuccess(String data) {
        try {
            parse(data);
        } catch (Exception e) {
            e.printStackTrace();
            onError(ExceptionEngine.code_error_analytic, ExceptionEngine.msg_error_analytic);
        }
    }

    @Override
    public void onError(int code, String msg) {
        if (mIHttpCallback != null) mIHttpCallback.onError(code, msg);
    }

    @Override
    public void onCancel() {
        if (mIHttpCallback != null) mIHttpCallback.onCancel();
    }

    @Override
    public void parse(String data) throws Exception {
        if (mIHttpCallback != null) {
            Type genericType = getGenericType(mIHttpCallback);
            T object = parseResponse(data, genericType);
            if (object instanceof IResponse) {//开发者实现 IResponse
                IResponse response = (IResponse) object;
                if (response.isISuccess()) {
                    mIHttpCallback.onSuccess(object);
                } else {
                    mIHttpCallback.onError(response.getICode(), response.getIMsg());
                }
            } else {//开发者未实现 IResponse
                mIHttpCallback.onSuccess(object);
            }
        }
    }

    /**
     * 解析 Response
     * 特殊处理 String.class/JSONObject.class/JSONArray.class
     * 其他情况  fromJson(String json, Type typeOfT) (Type/Class)
     */
    private <T> T parseResponse(String data, Type rawType) throws Exception {
        if (rawType == String.class) {
            //noinspection unchecked
            return (T) data;
        } else if (rawType == JSONObject.class) {
            //noinspection unchecked
            return (T) new JSONObject(data);
        } else if (rawType == JSONArray.class) {
            //noinspection unchecked
            return (T) new JSONArray(data);
        } else {
            //noinspection unchecked
            return (T) new Gson().fromJson(data, rawType);
        }
    }

    /**
     * 获取泛型 Type
     *
     * @param httpCallback
     * @param <T>
     * @return
     */
    private <T> Type getGenericType(HttpCallback<T> httpCallback) {
        //com.ruffian.android.framework.http.HttpCallback<com.ruffian.android.mvvm.Response<com.ruffian.android.mvvm.account.entity.UserBean>>
        Type[] types = httpCallback.getClass().getGenericInterfaces();
        //com.ruffian.android.mvvm.Response<com.ruffian.android.mvvm.account.entity.UserBean>
        Type[] params = ((ParameterizedType) types[0]).getActualTypeArguments();
        return params[0];
    }

    /**
     * 获取泛型 Type Name
     *
     * @param type
     */
    private String getGenericTypeName(Type type) {
        //com.ruffian.android.mvvm.Response<com.ruffian.android.mvvm.account.entity.UserBean>
        String genericTAllName = type.toString();
        //com.ruffian.android.mvvm.Response 移除 <xxx>
        String genericTMainName = genericTAllName.replaceAll("<[^<>]*>", "");
        return genericTMainName;
    }

}