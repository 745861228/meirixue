package com.lwgz.meirixue.interfaces;

import android.util.Log;

import com.lwgz.meirixue.utils.CommonUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;


/**
 * Created by zhiyuan on 17/1/11.
 */

public class SaveCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        //PHPSESSID=vg6d8mpgmqgni6ct15skcjjm71;loginname=15330276178;
        StringBuilder stringBuilder=new StringBuilder();
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            for (String header : originalResponse.headers("Set-Cookie")) {
                Log.i("AAAA----","=="+header+"==");
                String cookie = header.substring(0, header.indexOf(";") + 1);
                stringBuilder.append(cookie);
            }
        }
        Log.i("AAAA","++"+stringBuilder.toString()+"++");
        CommonUtils.saveString("cookie",stringBuilder.toString());
        return originalResponse;
    }
}