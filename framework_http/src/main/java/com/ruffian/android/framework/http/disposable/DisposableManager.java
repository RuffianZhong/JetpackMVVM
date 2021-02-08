package com.ruffian.android.framework.http.disposable;

import java.util.HashMap;
import java.util.Set;

import io.reactivex.disposables.Disposable;

/**
 * DisposableManager
 *
 * @author ZhongDaFeng
 */
public class DisposableManager implements IDisposableManager {
    private static volatile DisposableManager mInstance;
    private static HashMap<Object, Disposable> mMaps;//处理,请求列表

    public static DisposableManager get() {
        if (mInstance == null) {
            synchronized (DisposableManager.class) {
                if (mInstance == null) {
                    mInstance = new DisposableManager();
                }
            }
        }
        return mInstance;
    }

    private DisposableManager() {
        mMaps = new HashMap<>();
    }

    @Override
    public void addDisposable(Object tag, Disposable disposable) {
        if (tag == null) return;
        mMaps.put(tag, disposable);
    }

    @Override
    public void removeDisposable(Object tag) {
        if (tag == null) return;
        if (mMaps.isEmpty()) return;
        if (mMaps.get(tag) == null) return;

        Disposable disposable = mMaps.get(tag);
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();

        mMaps.remove(tag);
    }

    @Override
    public void removeAll() {
        if (mMaps.isEmpty()) return;
        //遍历取消请求
        Set<Object> keySet = mMaps.keySet();
        for (Object key : keySet) {
            removeDisposable(key);
        }
    }

    @Override
    public boolean isDisposed(Object tag) {
        if (tag == null || mMaps.isEmpty()) return true;
        Disposable disposable = mMaps.get(tag);
        if (disposable != null) return disposable.isDisposed();
        return true;
    }
}