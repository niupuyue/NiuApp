package com.niupule.niuapp.mvp.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.niupule.niuapp.R;
import com.niupule.niuapp.data.source.LoginDataRepository;
import com.niupule.niuapp.data.source.local.LoginDataLocalSource;
import com.niupule.niuapp.data.source.local.LoginDataRemoteSource;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 16:26
 * Desc:
 * Version:
 */
public class LoginActivity extends AppCompatActivity {

    private LoginFragment loginFragment;
    private SignUpFragment signUpFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);

        //判断之前是否有使用过当前的对象
        if (savedInstanceState != null) {
            FragmentManager manager = getSupportFragmentManager();
            loginFragment = (LoginFragment) manager.getFragment(savedInstanceState, LoginFragment.class.getSimpleName());
            signUpFragment = (SignUpFragment) manager.getFragment(savedInstanceState, SignUpFragment.class.getSimpleName());
        } else {
            loginFragment = LoginFragment.newInstance();
            signUpFragment = SignUpFragment.newInstance();
        }

        //判断当前的登录注册页面是否已经被添加进来了
        if (!loginFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, loginFragment, LoginFragment.class.getSimpleName())
                    .commit();
        }
        if (!signUpFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, signUpFragment, SignUpFragment.class.getSimpleName())
                    .commit();
        }
        new LoginPresenter(loginFragment, LoginDataRepository.getInstance(
                LoginDataLocalSource.getInstance(), LoginDataRemoteSource.getInstance()
        ));

        showLoginFragment();
    }


    public void showLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.right_in, R.anim.right_out)
                .show(loginFragment)
                .hide(signUpFragment)
                .commit();
    }

    public void showSignUpFragment() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.left_in, R.anim.left_out)
                .show(signUpFragment)
                .hide(loginFragment)
                .commit();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getSupportFragmentManager();
        if (loginFragment.isAdded()) {
            manager.putFragment(outState, LoginFragment.class.getSimpleName(), loginFragment);
        }
        if (signUpFragment.isAdded()) {
            manager.putFragment(outState, SignUpFragment.class.getSimpleName(), signUpFragment);
        }
    }
}
