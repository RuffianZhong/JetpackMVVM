package com.ruffian.android.mvvm.sample;

import androidx.lifecycle.LifecycleOwner;

import com.ruffian.android.framework.http.HttpCallback;
import com.ruffian.android.framework.http.RHttp;
import com.ruffian.android.framework.mvvm.model.IModelCallback;
import com.ruffian.android.mvvm.common.Response;

import java.util.TreeMap;

/**
 * LoginModel
 */
public class LoginModel {

    /**
     * 登录
     */
    public void login(String account, String password, LifecycleOwner lifecycle, final IModelCallback.Http<UserBean> modelCallback) {
        //构建请求参数
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("username", account);
        request.put("password", password);
        //发送请求
        new RHttp.Builder()
                .post()
                .apiUrl("user/login")
                .addParameter(request)
                .lifecycle(lifecycle)
                .build()
                .execute(new HttpCallback<Response<UserBean>>() {
                    @Override
                    public void onSuccess(Response<UserBean> object) {
                        modelCallback.onSuccess(object.getData());
                    }

                    @Override
                    public void onError(int code, String msg) {
                        modelCallback.onError(code, msg);
                    }

                    @Override
                    public void onCancel() {
                        modelCallback.onCancel();
                    }
                });
    }
}
