package com.ruffian.android.mvvm.account.model;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

import com.ruffian.android.framework.mvvm.model.IModel;
import com.ruffian.android.framework.mvvm.model.IModelCallback;
import com.ruffian.android.mvvm.account.entity.UserBean;

/**
 * IAccountModel
 * 备注：此接口按需定义，良好的编程习惯应该是先定义接口再实现
 *
 * @author ZhongDaFeng
 */
public interface IAccountModel {

    /**
     * 登录 model 接口定义
     */
    interface LoginModel extends IModel {
        /**
         * 用户密码登录
         *
         * @param lifecycle     组件生命周期
         * @param modelCallback model回调接口(网络)
         */
        void login(final Context context, String userName, String password, LifecycleOwner lifecycle, IModelCallback.Http<UserBean> modelCallback);

        /**
         * 获取本地缓存数据
         *
         * @param modelCallback model回调接口(普通数据)
         */
        void getLocalCache(Context context, LifecycleOwner lifecycle, IModelCallback.Data<String> modelCallback);

        /**
         * 缓存数据
         */
        void saveLocalCache(Context context, UserBean data);
    }

    /**
     * 注册 model 接口定义
     */
    interface RegisterModel extends IModel {

    }

}
