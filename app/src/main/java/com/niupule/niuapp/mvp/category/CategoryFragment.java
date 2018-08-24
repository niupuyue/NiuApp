package com.niupule.niuapp.mvp.category;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.niupule.niuapp.R;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 21:24
 * Desc:
 * Version:
 */
public class CategoryFragment extends Fragment {

    public static CategoryFragment newInstance(){
        return new CategoryFragment();
    }

    private LinearLayout empty_layout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category,container,false);
        initView(root);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //执行刷新操作
            }
        });
        return root;
    }

    protected void initView(View root){
        empty_layout = root.findViewById(R.id.empty_view);
        swipeRefreshLayout = root.findViewById(R.id.refresh_layout);
        recyclerView = root.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
