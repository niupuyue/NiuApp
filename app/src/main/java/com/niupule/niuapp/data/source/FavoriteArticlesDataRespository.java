package com.niupule.niuapp.data.source;

import com.niupule.niuapp.data.detail.FavoriteArticleDetailData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/24
 * Time: 17:46
 * Desc:
 * Version:
 */
public class FavoriteArticlesDataRespository implements FavoriteArticlesDataSource {

    private FavoriteArticlesDataSource remote;
    private FavoriteArticlesDataSource local;
    private Map<Integer, FavoriteArticleDetailData> favoriteArticleClearCache;

    private static FavoriteArticlesDataRespository INSTANCE;

    private FavoriteArticlesDataRespository(FavoriteArticlesDataSource remote, FavoriteArticlesDataSource local) {
        this.local = local;
        this.remote = remote;
    }

    public static FavoriteArticlesDataRespository getInstance(FavoriteArticlesDataSource local, FavoriteArticlesDataSource remote) {
        if (INSTANCE == null) {
            INSTANCE = new FavoriteArticlesDataRespository(remote, local);
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<FavoriteArticleDetailData>> getFavoriteArticles(int page, boolean forceUpdate, final boolean clearCache) {
        if (!forceUpdate && favoriteArticleClearCache != null) {
            return Observable.fromIterable(new ArrayList<>(favoriteArticleClearCache.values()))
                    .toSortedList(new Comparator<FavoriteArticleDetailData>() {
                        @Override
                        public int compare(FavoriteArticleDetailData o1, FavoriteArticleDetailData o2) {
                            return o1.getPublishTime() > o2.getPublishTime() ? -1 : 1;
                        }
                    })
                    .toObservable();
        }
        if (!clearCache && favoriteArticleClearCache != null) {
            Observable obj1 = Observable.fromIterable(new ArrayList(favoriteArticleClearCache.values()))
                    .toSortedList(new Comparator<FavoriteArticleDetailData>() {
                        @Override
                        public int compare(FavoriteArticleDetailData o1, FavoriteArticleDetailData o2) {
                            return o1.getPublishTime() > o2.getPublishTime() ? -1 : 1;
                        }

                    })
                    .toObservable();
            Observable obj2 = remote.getFavoriteArticles(page, forceUpdate, clearCache)
                    .doOnNext(new Consumer<List<FavoriteArticleDetailData>>() {
                        @Override
                        public void accept(List<FavoriteArticleDetailData> favoriteArticleDetailData) throws Exception {
                            refreshArticleCache(clearCache, favoriteArticleDetailData);
                        }
                    });
            return Observable.merge(obj1, obj2)
                    .collect(new Callable<List<FavoriteArticleDetailData>>() {

                        @Override
                        public List<FavoriteArticleDetailData> call() throws Exception {
                            return new ArrayList<>();
                        }
                    }, new BiConsumer<List<FavoriteArticleDetailData>, List<FavoriteArticleDetailData>>() {

                        @Override
                        public void accept(List<FavoriteArticleDetailData> list, List<FavoriteArticleDetailData> list2) throws Exception {
                            list.addAll(list2);
                        }
                    }).toObservable();
        }
        return remote.getFavoriteArticles(page, forceUpdate, clearCache)
                .doOnNext(new Consumer<List<FavoriteArticleDetailData>>() {
                    @Override
                    public void accept(List<FavoriteArticleDetailData> list) throws Exception {
                        refreshArticleCache(clearCache, list);
                    }
                });
    }

    @Override
    public boolean isExist(int userId, int id) {
        return local.isExist(userId,id);
    }

    protected void refreshArticleCache(boolean clearCache, List<FavoriteArticleDetailData> list) {
        if (favoriteArticleClearCache == null) {
            favoriteArticleClearCache = new LinkedHashMap<>();
        }
        if (clearCache) {
            favoriteArticleClearCache.clear();
        }
        for (FavoriteArticleDetailData data : list) {
            favoriteArticleClearCache.put(data.getOriginId(), data);
        }
    }

}
