package com.ruffian.android.framework.http;

/**
 * 响应接口定义
 * 建议开发者实现此接口，并且设置重写对应的方法
 * 1.重写：框架会根据 isISuccess() 处理 逻辑接口失败时 直接回调 onError
 * 2.不重写：开发者自行实现 逻辑接口失败 时回调到 onError
 *
 * @author ZhongDaFeng
 */
public interface IResponse {

    /**
     * 接口逻辑是否成功
     * 备注：例如 code == 0 表示接口处理成功
     *
     * @return
     */
    public boolean isISuccess();

    /**
     * 接口响应码
     *
     * @return
     */
    public int getICode();

    /**
     * 接口响应描述
     *
     * @return
     */
    public String getIMsg();

}