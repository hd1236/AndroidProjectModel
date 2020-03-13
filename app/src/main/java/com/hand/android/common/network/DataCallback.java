package com.hand.android.common.network;

/**
 * Created by admin on 2016/10/27.
 * 远程或者本地数据获取的回调接口，主线程操作
 */

public interface DataCallback {

    /**
     * 开始获取数据前操作
     * @param requestTag
     */
    void onStart(String requestTag);

    /**
     * 获取数据成功
     * @param data
     * @param requestTag
     */
    void onSuccess(Object data, String requestTag);

    /**
     * 获取数据失败
     * @param e
     * @param requestTag
     */
    void onError(Throwable e, String requestTag);

    /**
     * 获取数据完成，无论失败、成功总会调用
     * @param requestTag
     */
    void onComplete(String requestTag);

}
