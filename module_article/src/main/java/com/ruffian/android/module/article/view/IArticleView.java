package com.ruffian.android.module.article.view;

import com.ruffian.android.library.common.mvvm.ICommonView;
import com.ruffian.android.module.article.entity.ArticleBean;

import java.util.ArrayList;

/**
 * IArticleView
 *
 * @author ZhongDaFeng
 */
public interface IArticleView extends ICommonView {

    /**
     * 获取文章列表成功
     */
    public void onArticleListSuccess(int pageIndex, ArrayList<ArticleBean> list);


    public void onArticleListError(int pageIndex, int code, String msg);

}