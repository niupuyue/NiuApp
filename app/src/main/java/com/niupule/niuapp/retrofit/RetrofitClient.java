package com.niupule.niuapp.retrofit;

import com.niupule.niuapp.app.App;
import com.niupule.niuapp.retrofit.cookies.CookieManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/3
 * Time: 19:44
 * Desc:
 * Version:
 */
public class RetrofitClient {

    private RetrofitClient() {

    }

    private static class ClientHelper {
        private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieManager(App.getInstance()))
                .build();


        private static Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.API_BASE)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getInstance() {
        return ClientHelper.retrofit;
    }

}
