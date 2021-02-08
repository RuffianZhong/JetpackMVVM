package com.ruffian.android.framework.http.exception;

/**
 * 整个事件链错误类型
 * 备注：网络相关，解析数据相关
 *
 * @author ZhongDaFeng
 */
public class EventException extends Exception {
    private int code;//错误码
    private String msg;//错误信息

    public EventException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public EventException(Throwable throwable, int code, String msg) {
        super(throwable);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
