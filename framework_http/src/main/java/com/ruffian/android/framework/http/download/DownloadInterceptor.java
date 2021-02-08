package com.ruffian.android.framework.http.download;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;


/**
 * 通过Interceptor回调监听Response进度
 *
 * @author ZhongDaFeng
 */
public class DownloadInterceptor implements Interceptor {

    private IDownloadProgress downloadProgress;

    public DownloadInterceptor(IDownloadProgress downloadProgress) {
        this.downloadProgress = downloadProgress;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder()
                .body(new DownloadResponseBody(response.body(), downloadProgress))
                .build();
    }
}
