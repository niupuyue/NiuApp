package com.niupule.niuapp.mvp.timeline.article;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niupule.niuapp.R;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 22:19
 * Desc:
 * Version:
 */
public class ArticleFragment extends Fragment {

    public static ArticleFragment newInstance(){
        return new ArticleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_article,container,false);
        return root;
    }
}
