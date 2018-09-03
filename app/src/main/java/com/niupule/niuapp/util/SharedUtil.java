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

    //存储用户id
    public void setUserId(int userId){
        shared.edit().putInt(Constants.KEY_ID,userId).apply();
    }
    //获取用户id
    public int getUserId(){
        return shared.getInt(Constants.KEY_ID,-1);
    }

    //获取用户头像
    public String getIcon(){
        return shared.getString(Constants.KEY_ICON,"");
    }
    //设置用户头像
    public void setIcon(String icon){
        shared.edit().putString(Constants.KEY_ICON,icon).apply();
    }

    //设置用户密码
    public void setPassword(String password){
        shared.edit().putString(Constants.KEY_PASSWORD,password).apply();
    }
    //获取用户密码
    public String getPassword(){
        return shared.getString(Constants.KEY_PASSWORD,"");
    }

    //设置是否需要跳过登录页面
    public void setSkipLogin(boolean flag){
        shared.edit().putBoolean(Constants.KEY_SKIP_LOGIN_PAGE,flag).apply();
    }
    //获取是否需要跳过登录页面
    public boolean getSkipLogin(){
        return shared.getBoolean(Constants.KEY_SKIP_LOGIN_PAGE,false);
    }

}
