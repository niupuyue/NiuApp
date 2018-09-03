package com.niupule.niuapp.retrofit.cookies;

import java.io.Serializable;

import okhttp3.Cookie;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/3
 * Time: 20:14
 * Desc:
 * Version:
 */
public class OkHttpCookies implements Serializable {

    private transient final Cookie cookie;
    private transient Cookie clientCookie;

    public OkHttpCookies(Cookie cookie) {
        this.cookie = cookie;
    }

    public Cookie getCookies(){
        Cookie bestCookie = cookie;
        if (clientCookie != null){
            bestCookie = clientCookie;
        }
        return bestCookie;
    }

}
