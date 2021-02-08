package com.ruffian.android.framework.http.download;


import com.ruffian.android.framework.http.utils.LogUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;


/**
 * 下载ResponseBody
 *
 * @author ZhongDaFeng
 */
public class DownloadResponseBody extends ResponseBody {

    private ResponseBody responseBody;
    private IDownloadProgress downloadProgress;
    private BufferedSource bufferedSource;

    public DownloadResponseBody(ResponseBody responseBody, IDownloadProgress downloadProgress) {
        this.responseBody = responseBody;
        this.downloadProgress = downloadProgress;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long readBytesCount = 0L;
            long totalBytesCount = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                readBytesCount += bytesRead != -1 ? bytesRead : 0;
                if (totalBytesCount == 0) {
                    totalBytesCount = contentLength();
                }
                LogUtils.d("download progress readBytesCount:" + readBytesCount + "  totalBytesCount:" + totalBytesCount + " callback:" + downloadProgress);
                if (downloadProgress != null) {
                    downloadProgress.progress(readBytesCount, totalBytesCount);
                }
                return bytesRead;
            }
        };
    }
}
