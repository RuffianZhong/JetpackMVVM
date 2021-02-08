package com.ruffian.android.framework.mvvm.model;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Model工厂类
 *
 * @author ZhongDaFeng
 */
public class ModelFactory {

    /**
     * 全局存储Model
     */
    private static Map<String, Object> modelMap = new HashMap<>();

    /**
     * 获取model
     * 查询Map中是否存在model实例,不存在时动态创建
     *
     * @param cls 类
     * @param <T> model
     * @return
     */
    public static <T> T getModel(@NonNull Class<T> cls) {
        String className = cls.getName();//类名
        T model = (T) modelMap.get(className);
        if (model == null) {//不存在
            model = getModelReflex(className);
            modelMap.put(className, model);
        }
        return model;
    }

    /**
     * 反射获取Model
     *
     * @param className 包含完整路径的类名称 com.ruffian.cn.User
     * @param <T>
     * @return
     */
    private static <T> T getModelReflex(@NonNull String className) {
        T result = null;
        try {
            result = (T) Class.forName(className).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
