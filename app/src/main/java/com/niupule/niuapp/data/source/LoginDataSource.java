package com.niupule.niuapp.data.source;


import com.niupule.niuapp.data.detail.LoginData;
import com.niupule.niuapp.data.detail.LoginDetailData;
import com.niupule.niuapp.data.type.LoginType;

import io.reactivex.Observable;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/3
 * Time: 19:09
 * Desc: 进行登录操作的两种方法，第一种读取本地数据，第二种获取远程数据
 * Version:
 */
public interface LoginDataSource {

    Observable<LoginDetailData> getLocalLoginData(int userId);

    Observable<LoginData> getRemoteLoginData(String userName,String password,LoginType loginType);

}
