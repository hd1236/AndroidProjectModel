package com.hand.android.common.network.retrofitlib;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;
import rx.Observable;

public interface ApiService {

    @GET
    Observable<String> get(@Url String url);

    @POST
    Observable<String> post(@Url String url, @Body RequestBody requestBody);

    @PATCH
    Observable<String> patch(@Url String url, @Body RequestBody requestBody);

    @PUT
    Observable<String> put(@Url String url, @Body RequestBody file);

    @GET
    Call<String> synGet(@Url String url);

    @POST
    Call<String> synPost(@Url String url, @Body RequestBody requestBody);

    @PATCH
    Call<String> synPatch(@Url String url, @Body RequestBody requestBody);
}
