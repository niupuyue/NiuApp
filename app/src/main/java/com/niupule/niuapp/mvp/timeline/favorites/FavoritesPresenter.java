package com.niupule.niuapp.mvp.timeline.favorites;

import com.niupule.niuapp.data.source.FavoriteArticlesDataRespository;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/24
 * Time: 17:37
 * Desc:
 * Version:
 */
public class FavoritesPresenter implements FavoritesContract.FavoritesPresenter {

    private FavoriteArticlesDataRespository respository;

    private FavoritesContract.FavoritesView view;
    private CompositeDisposable compositeDisposable;
    private final static String TAG = "favorites_present";

    public FavoritesPresenter(FavoritesContract.FavoritesView view,FavoriteArticlesDataRespository respository){
        this.view = view;
        this.respository = respository;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void getFavoritesArticles(int page, boolean forceUpdate, boolean clearCache) {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
