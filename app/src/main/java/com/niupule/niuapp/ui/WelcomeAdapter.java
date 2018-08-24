package com.niupule.niuapp.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 17:25
 * Desc:
 * Version:
 */
public class WelcomeAdapter extends FragmentPagerAdapter {


    public WelcomeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return WelcomeFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
