package com.niupule.niuapp.mvp.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.niupule.niuapp.R;
import com.niupule.niuapp.data.detail.ArticleDetailData;
import com.niupule.niuapp.data.detail.HotKeyDetailData;
import com.niupule.niuapp.interfaze.OnRecycleViewItemClick;
import com.niupule.niuapp.mvp.adapter.CategoryAdapter;
import com.niupule.niuapp.mvp.detail.DetailActivity;
import com.niupule.niuapp.util.NetworkUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 23:03
 * Desc:
 * Version:
 */
public class SearchFragment extends Fragment implements SearchContract.SearchView {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private TagFlowLayout tagFlowLayout;
    private SearchView searchView;
    private LinearLayout empty_linearlayout;

    private final int index = 0;
    private int currentPage = 0;
    private String keyword;
    private boolean isFirstLoad = true;
    private int articleListSize;
    private LinearLayoutManager linearLayoutManager;

    private SearchContract.SearchPresenter presenter;

    private CategoryAdapter adapter;

    public SearchFragment() {

    }

    public static SearchFragment getInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        initView(root);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hideImn();
                hideTagFlowLayout(true);
                presenter.searchArticles(index, query, true, true);
                keyword = query;
                currentPage = index;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                hideTagFlowLayout(false);
                return true;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == articleListSize - 1) {
                        loadMore();
                    }
                }
            }
        });
        setHasOptionsMenu(true);
        return root;
    }

    protected void initView(View root) {
        toolbar = root.findViewById(R.id.toolbar);
        recyclerView = root.findViewById(R.id.recycle_view);
        tagFlowLayout = root.findViewById(R.id.flow_layout);
        searchView = root.findViewById(R.id.search_view);
        empty_linearlayout = root.findViewById(R.id.empty_view);

        linearLayoutManager = new LinearLayoutManager(getContext());
        SearchActivity searchActivity = (SearchActivity) getActivity();
        searchActivity.setSupportActionBar(toolbar);
        searchActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchView.setIconified(false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
        if (isFirstLoad) {
            presenter.getHotKeys(true);
            isFirstLoad = true;
        } else {
            presenter.getHotKeys(false);
            presenter.searchArticles(currentPage, keyword, false, false);
        }
    }

    private void hideTagFlowLayout(boolean hide) {
        if (hide) {
            tagFlowLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            tagFlowLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }

    private void loadMore() {
        boolean isNetworkAvailable = NetworkUtil.isNetworkAvailable(getContext());
        if (isNetworkAvailable) {
            currentPage += 1;
            presenter.searchArticles(currentPage, keyword, true, true);
        } else {
            Toast.makeText(getContext(), "网络连接状态不可用", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showArticles(final List<ArticleDetailData> articleList) {
        if (articleList.isEmpty()) {
            showEmptyView(true);
            return;
        }
        showEmptyView(false);
        if (adapter == null) {
            adapter = new CategoryAdapter(getContext(), articleList);
            adapter.setOnItemClickListener(new OnRecycleViewItemClick() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    ArticleDetailData data = articleList.get(position);
                    intent.putExtra(DetailActivity.ID, data.getId());
                    intent.putExtra(DetailActivity.URL, data.getLink());
                    intent.putExtra(DetailActivity.TITLE, data.getTitle());
                    intent.putExtra(DetailActivity.FROM_FAVORITE_FRAGMENT, false);
                    intent.putExtra(DetailActivity.FROM_BANNER, false);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateData(articleList);
        }
        articleListSize = articleList.size();
    }

    @Override
    public void showHotKeys(final List<HotKeyDetailData> hotkeyList) {
        tagFlowLayout.setAdapter(new TagAdapter<HotKeyDetailData>(hotkeyList) {
            @Override
            public View getView(FlowLayout parent, int position, HotKeyDetailData hotKeyDetailData) {
                TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_flow_layout, tagFlowLayout, false);
                if (hotkeyList == null) {
                    return null;
                }
                textView.setText(hotKeyDetailData.getName());
                tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        searchView.setQuery(hotkeyList.get(position).getName(), true);
                        return true;
                    }
                });
                return textView;
            }
        });
    }

    @Override
    public boolean isActivie() {
        return isAdded() && isResumed();
    }

    @Override
    public void hideImn() {
        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager.isActive()) {
            manager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        }
    }

    @Override
    public void showEmptyView(boolean toShow) {
        empty_linearlayout.setVisibility(toShow ? View.VISIBLE : View.INVISIBLE);
        recyclerView.setVisibility(toShow ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void setPresenter(SearchContract.SearchPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }
}
