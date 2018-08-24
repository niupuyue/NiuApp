package com.niupule.niuapp.mvp.timeline;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 22:26
 * Desc:
 * Version:
 */
public class TimeLinePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private String[] titles;
    private Context context;

    public TimeLinePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public TimeLinePagerAdapter(FragmentManager fm, Context context, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.context = context;
        titles = new String[]{
                "短文", "喜欢", "稍后阅读"
        };
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = fragments.get(0);
                break;
            case 1:
                fragment = fragments.get(1);
                break;
            case 2:
                fragment = fragments.get(2);
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    public String[] getTitles() {
        return titles;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
