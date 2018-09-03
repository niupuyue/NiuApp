package com.niupule.niuapp.mvp.login;

import com.niupule.niuapp.data.detail.LoginDetailData;
import com.niupule.niuapp.data.type.LoginType;
import com.niupule.niuapp.mvp.BasePresenter;
import com.niupule.niuapp.mvp.BaseView;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/25
 * Time: 08:14
 * Desc:
 * Version:
 */
public interface LoginContract {

    interface LoginPresenter extends BasePresenter {
        void login(String username, String password, LoginType loginType);

        void clearReaderLaterData();
    }

    interface LoginView extends BaseView<LoginPresenter> {
        void showLoginError(String error);

        boolean isActive();

        void saveUserInfo2Predference(LoginDetailData loginDetailData);

        void showNetworkError();
    }

}
