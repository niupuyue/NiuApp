package com.niupule.niuapp.mvp.timeline.favorites;

import com.niupule.niuapp.data.detail.FavoriteArticleDetailData;
import com.niupule.niuapp.data.source.FavoriteArticlesDataRespository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

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
        Disposable disposable = respository.getFavoriteArticles(page,forceUpdate,clearCache)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<FavoriteArticleDetailData>>(){

                    @Override
                    public void onNext(List<FavoriteArticleDetailData> value) {
                        if (view.isActive()){
                            view.showFavoritesArticles(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view.isActive()){
                            view.showEmptyView(true);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (view.isActive()){
                            view.setLoadingIndicator(false);
                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
