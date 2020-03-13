package com.hand.android.common.network;

import com.hand.android.common.network.frame.Callback;
import com.hand.android.common.network.frame.NetworkOption;
import com.hand.android.common.network.retrofitlib.RetrofitNetRequest;

/**
 * Created by admin on 2016/10/27.
 * 远程获取数据
 */
public class RemoteDataSource {

    /**
     * get请求
     *
     * @param requestTag
     * @param url
     * @param callback
     */
    public void getRequest(String requestTag, String url, final DataCallback callback) {
        RetrofitNetRequest.getInstance().requestData(requestTag, url, null,
                new NetworkOption.Builder().setMethod(NetworkOption.Method.GET).build(),
                new Callback() {
                    @Override
                    public void onStart(String requestTag) {
                        if (callback != null) {
                            callback.onStart(requestTag);
                        }
                    }

                    @Override
                    public void onSuccess(Object data, String requestTag) {
                        if (callback != null) {
                            callback.onSuccess(data, requestTag);
                        }
                    }

                    @Override
                    public void onError(Throwable e, String requestTag) {
                        if (callback != null) {
                            callback.onError(e, requestTag);
                        }
                    }

                    @Override
                    public void onComplete(String requestTag) {
                        if (callback != null) {
                            callback.onComplete(requestTag);
                        }
                    }
                });
    }

    /**
     * post请求
     *
     * @param requestTag
     * @param url
     * @param requestBody
     * @param callback
     */
    public void postRequest(String requestTag, String url, Object requestBody, final DataCallback callback) {
        RetrofitNetRequest.getInstance().requestData(requestTag, url, requestBody,
                new Callback() {
                    @Override
                    public void onStart(String requestTag) {
                        if (callback != null) {
                            callback.onStart(requestTag);
                        }
                    }

                    @Override
                    public void onSuccess(Object data, String requestTag) {
                        if (callback != null) {
                            callback.onSuccess(data, requestTag);
                        }
                    }

                    @Override
                    public void onError(Throwable e, String requestTag) {
                        if (callback != null) {
                            callback.onError(e, requestTag);
                        }
                    }

                    @Override
                    public void onComplete(String requestTag) {
                        if (callback != null) {
                            callback.onComplete(requestTag);
                        }
                    }
                });
    }

    public void putImage(String requestTag, String url, String fileUrl, final DataCallback callback) {
        RetrofitNetRequest.getInstance().requestData(requestTag, url, fileUrl, new Callback() {
            @Override
            public void onStart(String requestTag) {
                if (callback != null) {
                    callback.onStart(requestTag);
                }
            }

            @Override
            public void onSuccess(Object data, String requestTag) {
                if (callback != null) {
                    callback.onSuccess(data, requestTag);
                }
            }

            @Override
            public void onError(Throwable e, String requestTag) {
                if (callback != null) {
                    callback.onError(e, requestTag);
                }
            }

            @Override
            public void onComplete(String requestTag) {
                if (callback != null) {
                    callback.onComplete(requestTag);
                }
            }
        });
    }

    /**
     * 终止请求,解除观察者关联
     * 不再调用回调方法，防止activity pause或者stop后内存泄漏
     */
    public void stopAllRequest() {
        RetrofitNetRequest.getInstance().cancelAll();
    }
}
