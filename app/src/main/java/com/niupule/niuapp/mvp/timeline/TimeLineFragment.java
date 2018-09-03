package com.niupule.niuapp.mvp.timeline;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niupule.niuapp.R;
import com.niupule.niuapp.mvp.timeline.article.ArticleFragment;
import com.niupule.niuapp.mvp.timeline.favorites.FavoritesFragment;
import com.niupule.niuapp.mvp.timeline.readlater.ReadLaterFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 21:24
 * Desc:
 * Version:
 */
public class TimeLineFragment extends Fragment {

    private TabLayout timeline_tablayout;
    private ViewPager timeline_viewpager;

    private List<Fragment> fragments;

    private ArticleFragment articleFragment;
    private FavoritesFragment favoritesFragment;
    private ReadLaterFragment readLaterFragment;


    public static TimeLineFragment newInstance(){
        return new TimeLineFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager manager = getChildFragmentManager();
        if(savedInstanceState != null){
            articleFragment = (ArticleFragment) manager.getFragment(savedInstanceState,ArticleFragment.class.getSimpleName());
            favoritesFragment = (FavoritesFragment) manager.getFragment(savedInstanceState,FavoritesFragment.class.getSimpleName());
            readLaterFragment = (ReadLaterFragment) manager.getFragment(savedInstanceState,ReadLaterFragment.class.getSimpleName());
        }else {
            articleFragment = ArticleFragment.newInstance();
            favoritesFragment = FavoritesFragment.newInstance();
            readLaterFragment = ReadLaterFragment.newInstance();
        }
        fragments = new ArrayList<>();
        fragments.add(articleFragment);
        fragments.add(favoritesFragment);
        fragments.add(readLaterFragment);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_timeline,container,false);
        initView(root);
        return root;
    }

    protected void initView(View root){
        timeline_tablayout = root.findViewById(R.id.tab_layout);
        timeline_viewpager = root.findViewById(R.id.view_pager);
        timeline_viewpager.setAdapter(new TimeLinePagerAdapter(getChildFragmentManager(),getContext(),fragments));
        timeline_viewpager.setOffscreenPageLimit(3);
        timeline_tablayout.setupWithViewPager(timeline_viewpager);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getChildFragmentManager();
        if(articleFragment.isAdded()){
            manager.putFragment(outState,ArticleFragment.class.getSimpleName(),articleFragment);
        }
        if (favoritesFragment.isAdded()){
            manager.putFragment(outState,FavoritesFragment.class.getSimpleName(),favoritesFragment);
        }
        if (readLaterFragment.isAdded()){
            manager.putFragment(outState,ReadLaterFragment.class.getSimpleName(),readLaterFragment);
        }
    }
}
