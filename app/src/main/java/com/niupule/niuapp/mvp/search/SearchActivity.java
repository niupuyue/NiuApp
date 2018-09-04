package com.niupule.niuapp.mvp.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.niupule.niuapp.R;
import com.niupule.niuapp.data.source.ArticleDataRepository;
import com.niupule.niuapp.data.source.HotkeyDataRespository;
import com.niupule.niuapp.data.source.HotkeyDataSource;
import com.niupule.niuapp.data.source.remote.ArticleDataRemoteSource;
import com.niupule.niuapp.data.source.remote.HotkeyDataRemoteSource;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 21:37
 * Desc:
 * Version:
 */
public class SearchActivity extends AppCompatActivity {

    private SearchFragment searchFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);

        if (savedInstanceState != null) {
            searchFragment = (SearchFragment) getSupportFragmentManager().getFragment(savedInstanceState, SearchFragment.class.getSimpleName());
        } else {
            searchFragment = SearchFragment.getInstance();
        }

        new SearchPresenter(HotkeyDataRespository.getInstance(HotkeyDataRemoteSource.getInstance()),
                ArticleDataRepository.getInstance(ArticleDataRemoteSource.getInstance()), searchFragment);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, searchFragment, SearchFragment.class.getSimpleName())
                .commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (searchFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, SearchFragment.class.getSimpleName(), searchFragment);
        }
    }
}
