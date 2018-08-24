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

import com.niupule.niuapp.MainActivity;
import com.niupule.niuapp.R;
import com.niupule.niuapp.util.StringUtil;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 17:47
 * Desc:
 * Version:
 */
public class LoginFragment extends Fragment {

    private TextInputEditText username;
    private TextInputEditText password;
    private AppCompatButton login_btn;
    private TextView gotoSignUp;

    public LoginFragment() {

    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
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

}
