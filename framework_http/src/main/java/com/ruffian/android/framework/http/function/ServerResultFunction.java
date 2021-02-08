package com.ruffian.android.framework.http.function;

import androidx.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import io.reactivex.functions.Function;

/**
 * 服务器结果处理函数
 *
 * @author ZhongDaFeng
 */
public class ServerResultFunction implements Function<JsonElement, String> {
    @Override
    public String apply(@NonNull JsonElement response) throws Exception {
        /*避免html文本被格式化*/
        return new GsonBuilder().disableHtmlEscaping().create().toJson(response);
    }
}