package com.niupule.niuapp.retrofit.cookies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/3
 * Time: 19:54
 * Desc: 数据持久化缓存
 * Version:
 */
public class PersistentCookieStore {

    private static final String TAG = "persistentCookieStore";
    private static final String COOKIE_PREFS = "cookie_prefs";

    private SharedPreferences shared = null;
    private Map<String, ConcurrentHashMap<String, Cookie>> cookies = null;

    public PersistentCookieStore(Context context) {
        shared = context.getSharedPreferences(COOKIE_PREFS, 0);
        cookies = new HashMap<>();
        //将数据持久化的cookie保存存到内存中，也就是hashmap中
        Map<String, ?> preMap = shared.getAll();
        for (Map.Entry<String, ?> entry : preMap.entrySet()) {
            String[] cookieName = TextUtils.split((String) entry.getValue(), ",");
            for (String name : cookieName) {
                String encodedCookie = shared.getString(name, "");
                if (encodedCookie != null) {
                    Cookie decodedCookie = decodedCookie(encodedCookie);
                }
            }
        }
    }

    protected String getCookieToken(Cookie cookie) {
        return cookie.name() + "@" + cookie.domain();
    }

    public void add(HttpUrl url, Cookie cookie) {
        String name = getCookieToken(cookie);
        //将cookie缓存到内存中，如果缓存过期重置缓存
        if (!cookie.persistent()) {
            if (!cookies.containsKey(url.host())) {
                cookies.put(url.host(), new ConcurrentHashMap<String, Cookie>());
            }
            cookies.get(url.host()).put(name, cookie);
        } else {
            if (cookies.containsKey(url.host())) {
                cookies.get(url.host()).remove(name);
            }
        }
        //将cookie保存到本地持久化
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
        editor.putString(name, encodeCookie(new OkHttpCookies(cookie)));
        editor.apply();

    }

    public List<Cookie> get(HttpUrl url) {
        ArrayList<Cookie> ret = new ArrayList<>();
        if (cookies.containsKey(url.host())) {
            ret.addAll(cookies.get(url.host()).values());
        }
        return ret;
    }

    /**
     * 将cookie序列化成String
     *
     * @param cookies
     * @return
     */
    public String encodeCookie(OkHttpCookies cookies) {
        if (cookies == null) {
            return null;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(cookies);
        } catch (IOException e) {
            Log.e(TAG, "");
            return null;
        }
        return byteArrayToHexString(bos.toByteArray());
    }

    /**
     * 将字符串反序列化成cookie
     *
     * @param cookieString
     * @return
     */
    public Cookie decodedCookie(String cookieString) {
        byte[] bytes = hexStringToByteArray(cookieString);
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            cookie = ((OkHttpCookies) ois.readObject()).getCookies();
        } catch (IOException e) {
            Log.d(TAG, "IOException in decodeCookie", e);
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "ClassNotFoundException in decodeCookie", e);
        }
        return cookie;
    }

    /**
     * 将String类型转换成byte数组
     *
     * @param hexString
     * @return
     */
    protected byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + (Character.digit(hexString.charAt(1 + 1), 16)));
        }
        return data;
    }

    protected String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length / 2);
        for (byte elemet : bytes) {
            int v = elemet & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

}
