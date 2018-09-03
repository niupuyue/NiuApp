package com.niupule.niuapp.mvp.login;

import com.niupule.niuapp.data.detail.LoginData;
import com.niupule.niuapp.data.source.LoginDataRepository;
import com.niupule.niuapp.data.type.LoginType;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/3
 * Time: 19:04
 * Desc:
 * Version:
 */
public class LoginPresenter implements LoginContract.LoginPresenter {


    private LoginContract.LoginView view;

    private LoginDataRepository repository;

    private CompositeDisposable compositeDisposable;

    //构造方法
    public LoginPresenter(LoginContract.LoginView view, LoginDataRepository repository) {
        this.view = view;
        this.repository = repository;
        this.view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void login(String username, String password, LoginType loginType) {
        Disposable disposable = repository.getRemoteLoginData(username, password, loginType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginData>() {
                    @Override
                    public void onNext(LoginData value) {
                        if (!view.isActive()) {
                            return;
                        }
                        if (value.getErrorCode() == -1) {
                            view.showLoginError(value.getErrorMsg());
                        } else {
                            view.saveUserInfo2Predference(value.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view.isActive()) {
                            view.showNetworkError();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        compositeDisposable.add(disposable);

    }

    @Override
    public void clearReaderLaterData() {
        //清除最近可读文章信息
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
