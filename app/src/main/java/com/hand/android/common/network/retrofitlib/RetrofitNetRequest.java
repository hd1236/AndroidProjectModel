package com.hand.android.common.network.retrofitlib;

import android.text.TextUtils;

import com.hand.android.common.network.frame.Callback;
import com.hand.android.common.network.frame.NetRequest;
import com.hand.android.common.network.frame.NetworkOption;
import com.hand.android.common.network.frame.NetworkResponse;
import com.hand.android.common.network.frame.TagUtils;
import com.hand.android.common.network.frame.UrlUtils;
import com.hand.android.utils.LogUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class RetrofitNetRequest implements NetRequest {
    private static final String TAG = "RetrofitNetRequest";

    public final static int CONNECT_TIMEOUT = 8;    // 链接超时时间
    public final static int READ_TIMEOUT = 30;       // 读取超时时间

    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;
    private OkHttpClient mAsyncOkHttpClient;
    private ApiService mApiService;
    private Gson mGson;
    private String mBaseUrl;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private Map<String, Subscription> mSubscriptionMap = new HashMap<>();
    private Map<String, Callback> mDataCallBackMap = new HashMap<>();
    private Map<String, Observable> mObservableMap = new HashMap<>();

    private RetrofitNetRequest() {
    }

    private static class InstanceFactory {
        private static RetrofitNetRequest sInstance = new RetrofitNetRequest();
    }

    public static RetrofitNetRequest getInstance() {
        return InstanceFactory.sInstance;
    }


    @Override
    public void init(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new NullPointerException("base url == null");
        } else {
            mBaseUrl = baseUrl;
        }

        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(RetrofitNetRequest.CONNECT_TIMEOUT, TimeUnit.SECONDS) //设置链接超时时间
                .readTimeout(RetrofitNetRequest.READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new ResponseInterceptor())
                .addInterceptor(new RequestInterceptor())
                .build();

        mAsyncOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(RetrofitNetRequest.CONNECT_TIMEOUT, TimeUnit.SECONDS) //设置链接超时时间
                .readTimeout(RetrofitNetRequest.READ_TIMEOUT, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mApiService = mRetrofit.create(ApiService.class);

        mGson = new Gson();
    }

    @Override
    public void requestData(String tag, String url, Object requestBody, Callback dataCallBack) {
        requestData(tag, url, requestBody, new NetworkOption.Builder().build(), dataCallBack);
    }

    @Override
    public void requestData(String tag, String url, Object requestBody, NetworkOption networkOption, Callback dataCallBack) {
//拼接请求链接
        StringBuffer path = new StringBuffer();
        if (!url.startsWith("http")) {
            if (TextUtils.isEmpty(networkOption.getmHost())) {
                path.append(mBaseUrl);
            } else {
                path.append(networkOption.getmHost());
            }
        }
        path.append(url);

        //判断调用Retrofit对外方法
        Observable observable = null;
        switch (networkOption.getmMethod()) {
            case NetworkOption.Method.GET:
                observable = mApiService.get(path.toString());
                break;
            case NetworkOption.Method.POST:
                String jsonStr = requestBody instanceof String ? (String) requestBody : mGson.toJson(requestBody);
                observable = mApiService.post(path.toString(), RequestBody.create(JSON, jsonStr));
                break;
            case NetworkOption.Method.PATCH:
                String jsonStr1 = requestBody instanceof String ? (String) requestBody : mGson.toJson(requestBody);
                observable = mApiService.patch(path.toString(), RequestBody.create(JSON, jsonStr1));
                break;
        }
        tag = TagUtils.generateTag(tag);

        Subscription subscription = request(tag, observable);

        mDataCallBackMap.put(tag, dataCallBack);
        mObservableMap.put(tag, observable);
        mSubscriptionMap.put(tag, subscription);
    }

    public void requestData(String tag,String url, String fileUrl,Callback dataCallBack) {
        StringBuffer path = new StringBuffer();
        if (!url.startsWith("http")) {
            path.append(mBaseUrl);
        }
        path.append(url);
        File file = new File(fileUrl);
        Observable observable = mApiService.put(url,RequestBody.create(MediaType.parse(UrlUtils.getContentType(url)), file));
        tag = TagUtils.generateTag(tag);

        Subscription subscription = request(tag, observable);

        mDataCallBackMap.put(tag, dataCallBack);
        mObservableMap.put(tag, observable);
        mSubscriptionMap.put(tag, subscription);
    }

    @Override
    public NetworkResponse synRequestData(String tag, String url, Object requestBody) {
        return synRequestData(tag, url, requestBody, new NetworkOption.Builder().build());
    }

    @Override
    public NetworkResponse synRequestData(String tag, String url, Object requestBody, NetworkOption networkOption) {
        //拼接请求链接
        StringBuffer path = new StringBuffer();
        if (!url.startsWith("http")) {
            if (TextUtils.isEmpty(networkOption.getmHost())) {
                path.append(mBaseUrl);
            } else {
                path.append(networkOption.getmHost());
            }
        }
        path.append(url);


        Call call = null;
        switch (networkOption.getmMethod()) {
            case NetworkOption.Method.GET:
                call = mApiService.synGet(path.toString());
                break;
            case NetworkOption.Method.POST:
                String jsonStr = requestBody instanceof String ? (String) requestBody : mGson.toJson(requestBody);
                call = mApiService.synPost(path.toString(), RequestBody.create(JSON, jsonStr));
                break;
            case NetworkOption.Method.PATCH:
                String jsonStr1 = requestBody instanceof String ? (String) requestBody : mGson.toJson(requestBody);
                call = mApiService.synPatch(path.toString(), RequestBody.create(JSON, jsonStr1));
                break;
        }

        NetworkResponse networkResponse = new NetworkResponse.Builder().build();
        try {
            Response response = call.execute();
            networkResponse = new NetworkResponse.Builder()
                    .setCode(response.code())
                    .setContent(response.code() >= 200 && response.code() < 300 ? (String) response.body() : response.errorBody().string())
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return networkResponse;
    }

    @Override
    public Map<String, NetworkResponse> synRequestData(String tag, Map<String, String> fileUrl) {
        Map<String, NetworkResponse> responseMap = new HashMap<>();
        for (String key : fileUrl.keySet()) {
            File file = new File(fileUrl.get(key));
            Request request = new Request.Builder()
                    .url(key)
                    .put(RequestBody.create(MediaType.parse(UrlUtils.getContentType(key)), file))
                    .build();
            try {
                NetworkResponse networkResponse;
                okhttp3.Response response = mAsyncOkHttpClient.newCall(request).execute();
                networkResponse = new NetworkResponse.Builder()
                        .setCode(response.code())
                        .setContent(response.body().toString())
                        .build();

                responseMap.put(key, networkResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return responseMap;
    }

    /**
     * 取消tag请求
     * tag后增加随机数，这里已无法取消网络请求
     *
     * @param tag
     */
    @Deprecated
    @Override
    public void cancel(String tag) {
        Subscription subscription = mSubscriptionMap.get(tag);
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        mDataCallBackMap.remove(tag);
        mObservableMap.remove(tag);
        mSubscriptionMap.remove(tag);
    }

    @Override
    public void cancelAll() {
        for (Map.Entry<String, Subscription> entry : mSubscriptionMap.entrySet()) {
            Subscription subscription = entry.getValue();
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
        mDataCallBackMap.clear();
        mObservableMap.clear();
        mSubscriptionMap.clear();
    }

    private Subscription request(final String tag, Observable observable) {
        Observer observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                LogUtil.e(TAG, "onCompleted:**tag:" + tag);
                if (mDataCallBackMap.get(tag) != null) {
                    mDataCallBackMap.remove(tag).onComplete(TagUtils.originalTag(tag));
                    mObservableMap.remove(tag);
                    mSubscriptionMap.remove(tag);
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(TAG, "onError:**tag:" + tag + " throwable:" + e.getMessage());
                if (mDataCallBackMap.get(tag) != null) {
                    mDataCallBackMap.remove(tag).onError(e, TagUtils.originalTag(tag));
                    mObservableMap.remove(tag);
                    mSubscriptionMap.remove(tag);
                }
            }

            @Override
            public void onNext(String responseBody) {
                LogUtil.e(TAG, "onNext:**tag:" + tag);
//                List<DataDisposeManager.DataDispose> dataDisposeList = DataDisposeManager.getInstance().getDataDisposeList();
//                if (dataDisposeList != null) {
//                    for (DataDisposeManager.DataDispose dataDispose : dataDisposeList) {
//                        if (dataDispose.baseDispose(tag, responseBody, mDataCallBackMap.get(tag))) {
//                            if (!dataDispose.isErrorTag(tag)) {
//                                mDataCallBackMap.remove(tag);
//                                mObservableMap.remove(tag);
//                                mSubscriptionMap.remove(tag);
//                            }
//                            return;
//                        }
//                    }
//                }
                if (mDataCallBackMap.get(tag) != null) {
                    //回调后先不移除tag，如果onSuccess方法中出现异常也会捕获到，然后回调到onError
                    //onError和onComplete方法必走一个，只需在这两个方法里移除tag即可
                    mDataCallBackMap.get(tag).onSuccess(responseBody,TagUtils.originalTag(tag));
                }
            }
        };

        return observable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (mDataCallBackMap.get(tag) != null) {
                            mDataCallBackMap.get(tag).onStart(TagUtils.originalTag(tag));
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
