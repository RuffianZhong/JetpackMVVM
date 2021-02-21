package com.ruffian.android.mvvm.module.main.biz;


import com.ruffian.android.framework.http.HttpCallback;
import com.ruffian.android.framework.http.RHttp;
import com.ruffian.android.mvvm.common.BaseBiz;
import com.ruffian.android.mvvm.common.Response;
import com.ruffian.android.mvvm.module.main.entity.ArticleListBean;

import androidx.lifecycle.LifecycleOwner;

/**
 * 文章相关业务
 *
 * @author ZhongDaFeng
 */
public class ArticleBiz extends BaseBiz {

    /**
     * 获取文章列表
     */
    private final String ARTICLE_LIST = "article/list/%d/json";

    /**
     * 获取文章列表
     *
     * @param lifecycle
     * @param callback
     */
    public void getArticleList(int pageIndex, LifecycleOwner lifecycle, HttpCallback<Response<ArticleListBean>> callback) {

        String apiUrl = String.format(ARTICLE_LIST, pageIndex);

        /**
         * 发送请求
         */
        RHttp http = new RHttp.Builder()
                .get()
                .apiUrl(apiUrl)
                .lifecycle(lifecycle)
                .build();

        http.execute(callback);

    }


}
