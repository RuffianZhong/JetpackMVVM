package com.ruffian.android.mvvm.module.main.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ruffian.android.mvvm.module.main.entity.ArticleBean;

import java.util.ArrayList;

/**
 * ArticleViewModel
 *
 * @author ZhongDaFeng
 */
public class ArticleViewModel extends ViewModel {

    private MutableLiveData<ArrayList<ArticleBean>> articleList;

    public MutableLiveData<ArrayList<ArticleBean>> getArticleList() {
        if (articleList == null) articleList = new MutableLiveData<>();
        return articleList;
    }
}