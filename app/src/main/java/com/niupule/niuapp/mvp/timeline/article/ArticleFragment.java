package com.niupule.niuapp.mvp.timeline.article;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.niupule.niuapp.MainActivity;
import com.niupule.niuapp.R;
import com.niupule.niuapp.data.detail.ArticleDetailData;
import com.niupule.niuapp.data.detail.BannerDetailData;
import com.niupule.niuapp.mvp.adapter.ArticlesAdapter;
import com.niupule.niuapp.util.NetworkUtil;
import com.niupule.niuapp.util.SharedUtil;
import com.youth.banner.Banner;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 22:19
 * Desc:
 * Version:
 */
public class ArticleFragment extends Fragment implements ArticlesContract.ArticlesView {

    private NestedScrollView nestedScrollView;
    private RecyclerView recyclerView;
    private LinearLayout empty_layout;
    private ArticlesContract.ArticlesPresenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Banner banner;
    private final int INDEX = 0;
    private LinearLayoutManager linearLayoutManager;
    private int currentPage;
    private ArticlesAdapter articlesAdapter;

    private boolean firstLoading;
    private String username;
    private String password;

    public static ArticleFragment newInstance() {
        return new ArticleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = SharedUtil.getInstance().getUsername();
        password = SharedUtil.getInstance().getPassword();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_article, container, false);
        initView(root);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = INDEX;
                presenter.getArticles(currentPage, true, true);
            }
        });
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    loadMore();
                }
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (firstLoading) {
            presenter.autoLogin(username, password);
            presenter.getArticles(INDEX, true, true);
            presenter.getBanner();
            currentPage = INDEX;
            firstLoading = false;
        } else {
            presenter.getArticles(currentPage, true, true);
        }
        if (banner != null) {
            banner.startAutoPlay();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Toast.makeText(getContext(), "数据改变", Toast.LENGTH_SHORT).show();
    }

    protected void initView(View root) {
        nestedScrollView = root.findViewById(R.id.articles_nested_scrollview);
        recyclerView = root.findViewById(R.id.articles_recycler_view);
        empty_layout = root.findViewById(R.id.articles_empty_view);
        swipeRefreshLayout = root.findViewById(R.id.articles_swipe_refresh);
        recyclerView.setVisibility(View.VISIBLE);
        empty_layout.setVisibility(View.INVISIBLE);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
    }

    private void loadMore() {
        if (NetworkUtil.isNetworkAvailable(getContext())) {
            currentPage += 1;
            presenter.getArticles(currentPage, true, true);
        } else {
            Toast.makeText(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }

    @Override
    public void setLoadingIndicator(boolean isActive) {

    }

    @Override
    public void showArticles(List<ArticleDetailData> list) {

    }

    @Override
    public void showEmptyView(boolean toShow) {
        empty_layout.setVisibility(toShow ? View.VISIBLE : View.INVISIBLE);
        nestedScrollView.setVisibility(!toShow ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showBannder(List<BannerDetailData> list) {

    }

    @Override
    public void hideBanner() {
        banner.setVisibility(View.GONE);
    }

    @Override
    public void showAutoLoginFail() {
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setTitle("错误");
        dialog.setMessage("自动登录失败，请登录之后在进行操作");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //跳转到登录页面

                        dialog.dismiss();
                    }
                });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.dismiss();

    }

    @Override
    public void setPresenter(ArticlesContract.ArticlesPresenter presenter) {
        this.presenter = presenter;
    }
}
