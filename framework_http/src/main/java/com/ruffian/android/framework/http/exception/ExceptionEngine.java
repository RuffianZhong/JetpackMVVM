package com.ruffian.android.framework.http.exception;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;


/**
 * 错误/异常处理工具
 *
 * @author ZhongDaFeng
 */
public class ExceptionEngine {

    public static final int code_error_unknown = -1000000;//未知错误
    public static final int code_error_analytic = -1000001;//解析数据错误
    public static final int code_error_connect = -1000002;//网络连接错误
    public static final int code_error_time_out = -1000003;//网络连接超时

    public static final String msg_error_unknown = "未知错误";//未知错误
    public static final String msg_error_analytic = "解析错误";//解析数据错误
    public static final String msg_error_connect = "连接失败";//网络连接错误
    public static final String msg_error_time_out = "网络超时";//网络连接超时
    public static final String msg_error_http = "网络错误";//网络错误


    public static EventException handleException(Throwable e) {
        EventException ex;
        if (e instanceof HttpException) { //HTTP错误(均视为网络错误)
            HttpException httpExc = (HttpException) e;
            ex = new EventException(e, httpExc.code(), msg_error_http);
            return ex;
        } else if (e instanceof JsonParseException || e instanceof JSONException
                || e instanceof ParseException || e instanceof MalformedJsonException) {  //解析数据错误
            ex = new EventException(e, code_error_analytic, msg_error_analytic);
            return ex;
        } else if (e instanceof ConnectException || e instanceof SSLHandshakeException || e instanceof UnknownHostException) {//连接网络错误
            ex = new EventException(e, code_error_connect, msg_error_connect);
            return ex;
        } else if (e instanceof SocketTimeoutException) {//网络超时
            ex = new EventException(e, code_error_time_out, msg_error_time_out);
            return ex;
        } else {  //未知错误
            ex = new EventException(e, code_error_unknown, msg_error_unknown);
            return ex;
        }
    }

}
