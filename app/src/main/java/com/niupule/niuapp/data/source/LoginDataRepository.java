package com.niupule.niuapp.data.source;

import com.niupule.niuapp.data.detail.LoginData;
import com.niupule.niuapp.data.detail.LoginDetailData;
import com.niupule.niuapp.data.type.LoginType;

import io.reactivex.Observable;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/3
 * Time: 19:15
 * Desc:
 * Version:
 */
public class LoginDataRepository implements LoginDataSource {

    //本地登录
    private LoginDataSource localLoginDataSource;

    //远程登录
    private LoginDataSource remoteLoginDataSource;

    public LoginDataRepository(LoginDataSource localLoginDataSource,LoginDataSource remoteLoginDataSource){
        this.localLoginDataSource = localLoginDataSource;
        this.remoteLoginDataSource = remoteLoginDataSource;
    }

    //声明当前类对象
    private static LoginDataRepository instance;

    //获取当前类对象
    public static LoginDataRepository getInstance(LoginDataSource localLoginDataSource,LoginDataSource remoteLoginDataSource){
        if(instance == null){
            instance = new LoginDataRepository(localLoginDataSource,remoteLoginDataSource);
        }
        return instance;
    }

    @Override
    public Observable<LoginDetailData> getLocalLoginData(int userId) {
        return localLoginDataSource.getLocalLoginData(userId);
    }

    @Override
    public Observable<LoginData> getRemoteLoginData(String userName, String password, LoginType loginType) {
        return remoteLoginDataSource.getRemoteLoginData(userName,password,loginType);
    }
}
