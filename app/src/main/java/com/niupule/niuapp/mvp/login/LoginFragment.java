package com.niupule.niuapp.mvp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.niupule.niuapp.MainActivity;
import com.niupule.niuapp.R;
import com.niupule.niuapp.data.detail.LoginDetailData;
import com.niupule.niuapp.data.type.LoginType;
import com.niupule.niuapp.util.SharedUtil;
import com.niupule.niuapp.util.StringUtil;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 17:47
 * Desc:
 * Version:
 */
public class LoginFragment extends Fragment implements LoginContract.LoginView {

    private TextInputEditText username;
    private TextInputEditText password;
    private AppCompatButton login_btn;
    private TextView gotoSignUp;

    private LoginContract.LoginPresenter presenter;

    public LoginFragment() {

    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharedUtil.getInstance().getSkipLogin()){
            gotoMain();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        initView(root);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行登录操作
                String name = username.getText().toString();
                String pwd = password.getText().toString();
                if (checkValid(name, pwd)) {
                    //执行
                    presenter.login(name,pwd, LoginType.TYPE_LOGIN);
                }
            }
        });
        gotoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity activity = new LoginActivity();
                activity.showSignUpFragment();
            }
        });
        return root;
    }

    protected void initView(View root) {
        username = root.findViewById(R.id.login_username);
        password = root.findViewById(R.id.login_password);
        login_btn = root.findViewById(R.id.login_btn);
        gotoSignUp = root.findViewById(R.id.login_gotosignup);
    }


    protected boolean checkValid(String name, String pwd) {
        boolean flag = true;
        if (StringUtil.isInvalid(name) || StringUtil.isInvalid(pwd)) {
            Snackbar.make(gotoSignUp, "输入信息无效，请重新输入", Snackbar.LENGTH_SHORT).show();
            username.setText("");
            password.setText("");
            flag = false;
        }
        return flag;
    }

    private void gotoMain() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }

    @Override
    public void showLoginError(String error) {
        Snackbar.make(gotoSignUp, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }

    @Override
    public void saveUserInfo2Predference(LoginDetailData loginDetailData) {
        //将数据保存到本地
        int userId = loginDetailData.getId();
        String username = loginDetailData.getUsername();
        String password = loginDetailData.getPassword();
        String icon = loginDetailData.getIcon();
        int oldId = SharedUtil.getInstance().getUserId();
        if (oldId != -1 && userId != oldId) {
            //清空之前的登录信息
            presenter.clearReaderLaterData();
        }
        SharedUtil.getInstance().setIcon(icon);
        SharedUtil.getInstance().setUserId(userId);
        SharedUtil.getInstance().setUsername(username);
        SharedUtil.getInstance().setPassword(password);
        SharedUtil.getInstance().setSkipLogin(true);
        gotoMain();
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(LoginContract.LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }
}
