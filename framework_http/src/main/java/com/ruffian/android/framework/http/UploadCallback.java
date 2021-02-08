package com.ruffian.android.framework.http;

import java.io.File;

/**
 * UploadCallback
 *
 * @author ZhongDaFeng
 */
public interface UploadCallback<T> extends HttpCallback<T> {

    /**
     * 上传进度回调
     *
     * @param currentSize  当前值
     * @param totalSize    总大小
     * @param progress     进度
     * @param currentIndex 当前下标
     * @param totalFile    总文件数
     */
    void progress(File file, long currentSize, long totalSize, float progress, int currentIndex, int totalFile);
}
