package com.ruffian.android.mvvm.module.account.biz;


import androidx.lifecycle.LifecycleOwner;

import com.ruffian.android.framework.http.HttpCallback;
import com.ruffian.android.framework.http.RHttp;
import com.ruffian.android.mvvm.common.Response;
import com.ruffian.android.mvvm.module.account.entity.UserBean;
import com.ruffian.android.mvvm.common.BaseBiz;

import java.util.TreeMap;

/**
 * 用户相关业务
 *
 * @author ZhongDaFeng
 */
public class AccountBiz extends BaseBiz {

    /**
     * 登录API
     */
    private final String API_LOGIN = "user/login";

    /**
     * 用户登录
     *
     * @param userName
     * @param password
     * @param lifecycle
     * @param callback
     */
    public void login(String userName, String password, LifecycleOwner lifecycle, HttpCallback<Response<UserBean>> callback) {
        /**
         * 构建请求参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("username", userName);
        request.put("password", password);

        /**
         * 发送请求
         */
        RHttp http = new RHttp.Builder()
                .post()
                .apiUrl(API_LOGIN)
                .addParameter(request)
                .lifecycle(lifecycle)
                .build();

        http.execute(callback);

    }


}
