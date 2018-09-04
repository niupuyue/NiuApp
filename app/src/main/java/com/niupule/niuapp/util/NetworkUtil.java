package com.niupule.niuapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/4
 * Time: 11:16
 * Desc: 获取网络状态
 * Version:
 */
public class NetworkUtil {

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null){
            return false;
        }
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()){
            return false;
        }
        return true;
    }

}
