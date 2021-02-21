package com.ruffian.android.mvvm.module.main.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 文章列表实体类
 */
public class ArticleListBean implements Serializable {

    @SerializedName("curPage")
    private int pageIndex;//当前分页下标
    @SerializedName("datas")
    private ArrayList<ArticleBean> list;//列表数据

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public ArrayList<ArticleBean> getList() {
        return list;
    }

    public void setList(ArrayList<ArticleBean> list) {
        this.list = list;
    }
}