package com.ruffian.android.mvvm.common;

import java.util.TreeMap;

/**
 * 基础业务类
 * 根据业务需要编写
 *
 * @author ZhongDaFeng
 */
public class BaseBiz {

    /**
     * 获取基础 request 参数
     */
    public TreeMap<String, Object> getBaseRequest() {
        return new TreeMap<>();
    }

    /**
     * 获取基础 header 参数
     */
    public TreeMap<String, Object> getBaseHeader() {
        return new TreeMap<>();
    }

}
