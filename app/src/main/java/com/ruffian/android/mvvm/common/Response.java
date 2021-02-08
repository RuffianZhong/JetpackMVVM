package com.ruffian.android.mvvm.common;

import com.google.gson.annotations.SerializedName;
import com.ruffian.android.framework.http.IResponse;

import java.io.Serializable;

/**
 * 解析接口响应实体类 : HttpCallback<T>
 * 备注：
 * 1.实现接口 IResponse 重写函数，告知 http 框架 code == 0 认为是接口逻辑成功，code !=0 情况由 http 直接回调 onError
 * 2.如果不想实现 IResponse，则可以传任意类型到 HttpCallback<T>
 *
 * @author ZhongDaFeng
 */
public class Response<T> implements Serializable, IResponse {

    /*
    {
        "data": {},
        "errorCode": 0,
        "errorMsg": ""
    }
    */

    /**
     * 描述信息
     */
    @SerializedName("errorMsg")
    private String msg;

    /**
     * 状态码
     */
    @SerializedName("errorCode")
    private int code;

    /**
     * 数据对象/成功返回对象
     */
    @SerializedName("data")
    private T data;


    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean isISuccess() {
        return code == 0;
    }

    @Override
    public int getICode() {
        return code;
    }

    @Override
    public String getIMsg() {
        return msg;
    }
}
