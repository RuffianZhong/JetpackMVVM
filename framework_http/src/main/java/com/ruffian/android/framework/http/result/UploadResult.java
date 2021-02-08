package com.ruffian.android.framework.http.result;

import com.ruffian.android.framework.http.UploadCallback;

import java.io.File;

/**
 * UploadResult
 *
 * @author ZhongDaFeng
 */
public class UploadResult<T> extends HttpResult implements IUploadResult {

    public UploadResult(UploadCallback<T> httpCallback) {
        super(httpCallback);
    }

    @Override
    public void progress(File file, long currentSize, long totalSize, float progress, int currentIndex, int totalFile) {
        if (mIHttpCallback != null)
            ((UploadCallback) mIHttpCallback).progress(file, currentSize, totalSize, progress, currentIndex, totalFile);
    }
}