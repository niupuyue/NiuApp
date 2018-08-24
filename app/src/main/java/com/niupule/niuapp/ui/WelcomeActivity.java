package com.niupule.niuapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.niupule.niuapp.R;
import com.niupule.niuapp.mvp.login.LoginActivity;
import com.niupule.niuapp.util.SharedUtil;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 16:25
 * Desc:
 * Version:
 */
public class WelcomeActivity extends AppCompatActivity {


    private ViewPager welcome_viewpager;
    private ImageButton welcome_pre, welcome_next;
    private AppCompatButton welcome_finish;
    private ImageView[] indicators;

    private int[] bgColor;
    private int currentIndex = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //判断是否是第一次启动应用程序
        if (SharedUtil.getInstance().isFirstTime()) {
            initView();
            initData();

            welcome_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    //被选中之后更改页面的背景颜色
                    int color = (int) new android.animation.ArgbEvaluator().evaluate(positionOffset, bgColor[position], bgColor[position == 2 ? position : position + 1]);
                    welcome_viewpager.setBackgroundColor(color);
                }

                @Override
                public void onPageSelected(int position) {
                    currentIndex = position;
                    welcome_viewpager.setBackgroundColor(bgColor[position]);
                    updateIndicators(position);
                    welcome_pre.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
                    welcome_next.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                    welcome_finish.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else {
            gotoLogin();
        }

        welcome_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex -= 1;
                welcome_viewpager.setCurrentItem(currentIndex);
            }
        });
        welcome_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex += 1;
                welcome_viewpager.setCurrentItem(currentIndex);
            }
        });
        welcome_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置以后启动不需要再次引导页面
                SharedUtil.getInstance().setFirstTime(false);
                gotoLogin();
            }
        });

    }

    /**
     * 初始化页面
     */
    protected void initView() {
        WelcomeAdapter welcomeAdapter = new WelcomeAdapter(getSupportFragmentManager());
        welcome_viewpager = findViewById(R.id.welcome_viewpager);
        welcome_viewpager.setAdapter(welcomeAdapter);
        welcome_pre = findViewById(R.id.welcome_pre_btn);
        welcome_next = findViewById(R.id.welcome_next_btn);
        welcome_finish = findViewById(R.id.welcome_finish_btn);
        indicators = new ImageView[]{
                findViewById(R.id.img_indicator_0),
                findViewById(R.id.img_indicator_1),
                findViewById(R.id.img_indicator_2)
        };

    }

    /**
     * 初始化背景颜色数组数据
     */
    protected void initData() {
        bgColor = new int[]{
                ContextCompat.getColor(this, R.color.colorPrimary)
                , ContextCompat.getColor(this, R.color.cyan_500)
                , ContextCompat.getColor(this, R.color.light_blue_500)
        };
    }

    /**
     * 更新指示器
     *
     * @param position
     */
    protected void updateIndicators(int position) {
        for (int i = 0; i < 3; i++) {
            indicators[i].setBackgroundResource(i == position ? R.drawable.onboarding_indicator_selected : R.drawable.onboarding_indicator_unselected);
        }
    }

    /**
     * 跳转到登录页面
     */
    protected void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
