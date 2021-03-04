package com.ruffian.android.module.article.model;

import androidx.lifecycle.LifecycleOwner;

import com.ruffian.android.framework.http.HttpCallback;
import com.ruffian.android.framework.http.RHttp;
import com.ruffian.android.framework.mvvm.model.IModelCallback;
import com.ruffian.android.library.common.http.Response;
import com.ruffian.android.module.article.entity.ArticleListBean;

/**
 * ArticleModel
 *
 * @author ZhongDaFeng
 */
public class ArticleModel {

    /**
     * 获取文章列表
     */
    private final String ARTICLE_LIST = "article/list/%d/json";


    /**
     * 获取文章列表
     *
     * @param pageIndex
     * @param lifecycle
     * @param modelCallback
     */
    public void getArticleList(int pageIndex, LifecycleOwner lifecycle, final IModelCallback.Http<ArticleListBean> modelCallback) {

        //参数处理
        String apiUrl = String.format(ARTICLE_LIST, pageIndex);

        /**
         * 发送请求
         */
        new RHttp.Builder()
                .get()
                .apiUrl(apiUrl)
                .lifecycle(lifecycle)
                .build()
                .execute(new HttpCallback<Response<ArticleListBean>>() {
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
