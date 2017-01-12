package com.lwgz.meirixue.interfaces;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * author by LiKe on 2017/1/11.
 */

public interface RetrofitAPI {
    @GET
    Call<String> getMethod(@Url String url);

    @FormUrlEncoded
    @POST
    Call<String> postMothod(@Url String url,@FieldMap Map<String,String> map);
}
