package com.niupule.niuapp.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.niupule.niuapp.Constants;
import com.niupule.niuapp.app.App;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 16:55
 * Desc: 本地存储SharedPreference
 * Version:
 */
public class SharedUtil {

    private static SharedUtil instance = null;

    public static SharedUtil getInstance(){
        return SharedInstance.newInstance;
    }

    private static class SharedInstance{
        private static SharedUtil newInstance = new SharedUtil();
    }

    SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(App.getInstance());

    //判断是否是需要进入引导页面
    public boolean isFirstTime(){
        return shared.getBoolean(Constants.KEY_FIRST_TIME,true);
    }
    //设置是否需要进入引导页面
    public void setFirstTime(boolean flag){
        shared.edit().putBoolean(Constants.KEY_FIRST_TIME,flag).apply();
    }


    //存储用户名称
    public void setUsername(String  username){
        shared.edit().putString(Constants.KEY_USERNAME,username).apply();
    }
    //获取用户名称
    public String getUsername(){
        return shared.getString(Constants.KEY_USERNAME,"");
    }

}
