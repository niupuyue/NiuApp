package com.niupule.niuapp.data.source.local;

import com.niupule.niuapp.data.detail.LoginData;
import com.niupule.niuapp.data.detail.LoginDetailData;
import com.niupule.niuapp.data.source.LoginDataSource;
import com.niupule.niuapp.data.type.LoginType;
import com.niupule.niuapp.realm.RealmHelper;

import io.reactivex.Observable;
import io.realm.Realm;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/3
 * Time: 19:34
 * Desc:
 * Version:
 */
public class LoginDataLocalSource implements LoginDataSource {
    private static LoginDataLocalSource instance;

    private LoginDataLocalSource(){

    }

    public static LoginDataLocalSource getInstance(){
        if (instance == null){
            instance = new LoginDataLocalSource();
        }
        return instance;
    }

    @Override
    public Observable<LoginDetailData> getLocalLoginData(int userId) {
        //通过数据库获取具体内容
        Realm realm = RealmHelper.newRealmInstance();
        LoginDetailData loginDetailData = realm.copyFromRealm(
                realm.where(LoginDetailData.class)
                .equalTo("id",userId)
                .findFirst()
        );
        return Observable.just(loginDetailData);
    }

    @Override
    public Observable<LoginData> getRemoteLoginData(String userName, String password, LoginType loginType) {
        return null;
    }
}
