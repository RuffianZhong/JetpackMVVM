package com.ruffian.android.mvvm.module.account.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 用户实体类
 *
 * @author ZhongDaFeng
 */
public class UserBean implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("coinCount")
    private int coinCount;
    @SerializedName("icon")
    private String icon;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("username")
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public String getIcon() {
        return icon == null ? "" : icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username == null ? "" : username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
