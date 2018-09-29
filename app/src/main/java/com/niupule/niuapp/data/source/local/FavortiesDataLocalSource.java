package com.niupule.niuapp.data.source.local;

import com.niupule.niuapp.data.detail.FavoriteArticleData;
import com.niupule.niuapp.data.detail.FavoriteArticleDetailData;
import com.niupule.niuapp.data.source.FavoriteArticlesDataSource;
import com.niupule.niuapp.data.source.remote.FavortitesDataRemoteSource;
import com.niupule.niuapp.retrofit.RetrofitClient;
import com.niupule.niuapp.retrofit.RetrofitService;

import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/24
 * Time: 21:10
 * Desc:
 * Version:
 */
public class FavortiesDataLocalSource implements FavoriteArticlesDataSource {
    private static FavoriteArticlesDataSource INSTANCE;

    private FavortiesDataLocalSource() {

    }

    public static FavoriteArticlesDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FavortiesDataLocalSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<FavoriteArticleDetailData>> getFavoriteArticles(int page, boolean forceUpdate, boolean clearCache) {
        return RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getFavoriteArticles(page)
                .filter(new Predicate<FavoriteArticleData>() {
                    @Override
                    public boolean test(FavoriteArticleData favoriteArticlesData) throws Exception {
                        return favoriteArticlesData.getErrorCode() != -1;
                    }
                }).flatMap(new Function<FavoriteArticleData, ObservableSource<List<FavoriteArticleDetailData>>>() {
                    @Override
                    public ObservableSource<List<FavoriteArticleDetailData>> apply(FavoriteArticleData favoriteArticlesData) throws Exception {
                        return Observable.fromIterable(favoriteArticlesData.getData().getDatas()).toSortedList(new Comparator<FavoriteArticleDetailData>() {
                            @Override
                            public int compare(FavoriteArticleDetailData favoriteArticleDetailData, FavoriteArticleDetailData t1) {
                                return favoriteArticleDetailData.getPublishTime() > t1.getPublishTime() ? -1 : 1;
                            }
                        }).toObservable();
                    }
                });
    }

    @Override
    public boolean isExist(int userId, int id) {
        return false;
    }
}
