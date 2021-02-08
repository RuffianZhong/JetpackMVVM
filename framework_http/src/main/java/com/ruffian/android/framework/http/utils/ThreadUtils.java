package com.ruffian.android.framework.http.utils;

import android.os.Looper;

/**
 * 线程工具类
 *
 * @author ZhongDaFeng
 */
public class ThreadUtils {
    /**
     * 是否主线程
     *
     * @return
     */
    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId();
    }
}
