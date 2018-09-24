package com.niupule.niuapp.mvp.timeline.favorites;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.niupule.niuapp.R;
import com.niupule.niuapp.data.detail.ArticleDetailData;
import com.niupule.niuapp.mvp.adapter.FavoritesAdapter;
import com.niupule.niuapp.util.NetworkUtil;
import com.niupule.niuapp.util.SharedUtil;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 22:20
 * Desc:
 * Version:
 */
public class FavoritesFragment extends Fragment implements FavoritesContract.FavoritesView {

    private String username;
    private String password;

    private RecyclerView recyclerView;
    private LinearLayout empty_view;
    private NestedScrollView nestedScrollView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int currentPage;
    private boolean isFirstLoad;
    private final int INDEX = 0;

    private FavoritesContract.FavoritesPresenter presenter;
    private FavoritesAdapter adapter;

    public FavoritesFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = SharedUtil.getInstance().getUsername();
        password = SharedUtil.getInstance().getPassword();
    }

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        initView(root);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    loadMore();
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = INDEX;
                presenter.getFavoritesArticles(INDEX, true, true);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
        if (isFirstLoad) {
            presenter.getFavoritesArticles(INDEX, true, true);
            currentPage = INDEX;
            isFirstLoad = false;
        } else {
            presenter.getFavoritesArticles(INDEX, false, false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    protected void initView(View root) {
        empty_view = root.findViewById(R.id.empty_view);
        recyclerView = root.findViewById(R.id.recycle_view);
        nestedScrollView = root.findViewById(R.id.nested_scroll_view);
        swipeRefreshLayout = root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
    }

    private void loadMore() {
        boolean isNetworkAvailable = NetworkUtil.isNetworkAvailable(getContext());
        if (isNetworkAvailable) {
            currentPage += 1;
            presenter.getFavoritesArticles(currentPage, true, false);
        } else {
            Toast.makeText(getContext(), "网络不可用", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showFavoritesArticles(List<ArticleDetailData> list) {

    }

    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }

    @Override
    public void showEmptyView(boolean toShow) {
        empty_view.setVisibility(toShow ? View.VISIBLE : View.INVISIBLE);
        nestedScrollView.setVisibility(!toShow ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setLoadingIndicator(boolean isActive) {

    }

    @Override
    public void setPresenter(FavoritesContract.FavoritesPresenter presenter) {
        this.presenter = presenter;
    }
}
