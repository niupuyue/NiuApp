package com.niupule.niuapp.data.source.local;

import com.niupule.niuapp.data.detail.LoginData;
import com.niupule.niuapp.data.detail.LoginDetailData;
import com.niupule.niuapp.data.source.LoginDataSource;
import com.niupule.niuapp.data.type.LoginType;
import com.niupule.niuapp.realm.RealmHelper;
import com.niupule.niuapp.retrofit.RetrofitClient;
import com.niupule.niuapp.retrofit.RetrofitService;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/3
 * Time: 19:40
 * Desc: 远程登录操作
 * Version:
 */
public class LoginDataRemoteSource implements LoginDataSource {

    private static LoginDataRemoteSource instance;

    private LoginDataRemoteSource (){}

    public static LoginDataRemoteSource getInstance(){
        if (instance == null){
            instance = new LoginDataRemoteSource();
        }
        return instance;
    }

    @Override
    public Observable<LoginDetailData> getLocalLoginData(int userId) {
        return null;
    }

    @Override
    public Observable<LoginData> getRemoteLoginData(String userName, String password, LoginType loginType) {
        Observable<LoginData> loginDataObservable = null;
        if (loginType == LoginType.TYPE_LOGIN){
            loginDataObservable = RetrofitClient.getInstance()
                    .create(RetrofitService.class)
                    .login(userName,password);
        }else if(loginType == LoginType.TYPE_REGIST){
            loginDataObservable = RetrofitClient.getInstance()
                    .create(RetrofitService.class)
                    .register(userName,password,password);
        }

        return loginDataObservable
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<LoginData>() {
                    @Override
                    public void accept(LoginData loginData) throws Exception {
                        if(loginData.getErrorCode() != -1 || loginData.getData() != null){
                            //数据返回成功，保存到数据库中
                            Realm realm = Realm.getInstance(new RealmConfiguration.Builder()
                            .name(RealmHelper.DATABASE_NAME)
                            .deleteRealmIfMigrationNeeded()
                            .build());
                            realm.beginTransaction();
                            realm.copyToRealmOrUpdate(loginData.getData());
                            realm.commitTransaction();
                            realm.close();
                        }
                    }
                });
    }
}
