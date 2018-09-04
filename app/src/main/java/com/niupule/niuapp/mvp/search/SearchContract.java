package com.niupule.niuapp.mvp.search;

import com.niupule.niuapp.data.detail.ArticleDetailData;
import com.niupule.niuapp.data.detail.HotKeyDetailData;
import com.niupule.niuapp.mvp.BasePresenter;
import com.niupule.niuapp.mvp.BaseView;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/4
 * Time: 10:38
 * Desc:
 * Version:
 */
public interface SearchContract {

    interface SearchPresenter extends BasePresenter {
        void getHotKeys(boolean forcesUpdate);

        void searchArticles(int page, String keywords, boolean forcesUpdate, boolean clearCache);
    }

    interface SearchView extends BaseView<SearchPresenter> {
        void showArticles(List<ArticleDetailData> articleList);

        void showHotKeys(List<HotKeyDetailData> hotkeyList);

        boolean isActivie();

        void hideImn();

        void showEmptyView(boolean toShow);
    }

}
