package com.ruffian.android.mvvm.module.main.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 文章实体类
 *
 * @author ZhongDaFeng
 */
public class ArticleBean implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("superChapterName")
    private String superChapterName;//一级分类
    @SerializedName("niceDate")
    private String niceDate;//时间
    @SerializedName("link")
    private String link;//链接
    @SerializedName("author")
    private String author;//作者

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSuperChapterName() {
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
