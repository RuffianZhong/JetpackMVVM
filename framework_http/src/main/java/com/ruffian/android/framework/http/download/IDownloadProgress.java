package com.ruffian.android.framework.http.download;


/**
 * 下载进度回调接口
 *
 * @author ZhongDaFeng
 */
public interface IDownloadProgress {

    /**
     * 下载进度回调
     *
     * @param currentSize 当前值
     * @param totalSize   总大小
     */
    void progress(long currentSize, long totalSize);

}
