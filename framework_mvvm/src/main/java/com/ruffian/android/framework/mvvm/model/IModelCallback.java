package com.ruffian.android.framework.mvvm.model;

/**
 * IModelCallback
 *
 * @author ZhongDaFeng
 */
public interface IModelCallback {

    /**
     * 网络数据回调，泛指http
     *
     * @param <T>
     */
    public interface Http<T> {

        public void onSuccess(T object);

        public void onError(int code, String msg);

        public void onCancel();
    }

    /**
     * 其他数据回调<本地数据，数据库等>
     *
     * @param <T>
     */
    public interface Data<T> {

        public void onSuccess(T object);
    }
}
