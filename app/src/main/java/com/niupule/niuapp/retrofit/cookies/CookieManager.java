package com.niupule.niuapp.retrofit.cookies;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/3
 * Time: 19:53
 * Desc: 自定义Cookie管理类
 * Version:
 */
public class CookieManager implements CookieJar {

    private Context context;
    private static PersistentCookieStore store;

    public CookieManager(Context context){
        this.context = context;
        if (store == null){
            store = new PersistentCookieStore(context);
        }
    }



    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        //根据返回值保存
        if (cookies != null && cookies.size() > 0){
            for (Cookie item:cookies){
                store.add(url,item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = store.get(url);
        return cookies;
    }
}
