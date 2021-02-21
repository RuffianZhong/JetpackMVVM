package com.ruffian.android.mvvm.module.main.model;

import com.ruffian.android.framework.http.HttpCallback;
import com.ruffian.android.framework.mvvm.model.IModelCallback;
import com.ruffian.android.mvvm.common.BizFactory;
import com.ruffian.android.mvvm.common.Response;
import com.ruffian.android.mvvm.module.main.biz.ArticleBiz;
import com.ruffian.android.mvvm.module.main.entity.ArticleBean;
import com.ruffian.android.mvvm.module.main.entity.ArticleListBean;

import java.util.ArrayList;

import androidx.lifecycle.LifecycleOwner;

/**
 * ArticleModel
 *
 * @author ZhongDaFeng
 */
public class ArticleModel {


    /**
     * 获取文章列表
     *
     * @param pageIndex
     * @param lifecycle
     * @param modelCallback
     */
    public void getArticleList(int pageIndex, LifecycleOwner lifecycle, final IModelCallback.Http<ArticleListBean> modelCallback) {
        BizFactory.getBiz(ArticleBiz.class).getArticleList(pageIndex, lifecycle, new HttpCallback<Response<ArticleListBean>>() {
            @Override
            public void onSuccess(Response<ArticleListBean> value) {
                modelCallback.onSuccess(value.getData());
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
