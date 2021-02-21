package com.ruffian.android.mvvm.module.main.view;

import com.ruffian.android.mvvm.common.ICommonView;
import com.ruffian.android.mvvm.module.main.entity.ArticleBean;

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