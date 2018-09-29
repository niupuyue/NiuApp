package com.niupule.niuapp.mvp.timeline.favorites;

import com.niupule.niuapp.data.detail.ArticleDetailData;
import com.niupule.niuapp.data.detail.FavoriteArticleDetailData;
import com.niupule.niuapp.mvp.BasePresenter;
import com.niupule.niuapp.mvp.BaseView;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/10
 * Time: 22:46
 * Desc:
 * Version:
 */
public interface FavoritesContract {

    interface FavoritesPresenter extends BasePresenter{
        void getFavoritesArticles(int page,boolean forceUpdate,boolean clearCache);
    }

    interface FavoritesView extends BaseView<FavoritesPresenter>{
        void showFavoritesArticles(List<FavoriteArticleDetailData> list);

        boolean isActive();

        void showEmptyView(boolean toShow);

        void setLoadingIndicator(boolean isActive);

    }

}
