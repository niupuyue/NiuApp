package com.niupule.niuapp.data.source;

import com.niupule.niuapp.data.detail.ArticleDetailData;

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
 * Date: 2018/9/4
 * Time: 20:42
 * Desc:
 * Version:
 */
public class ArticleDataRepository implements ArticleDataSource {

    private ArticleDataSource articleDataSource;

    private Map<Integer, ArticleDetailData> articleCache;

    private Map<Integer, ArticleDetailData> queryCache;

    private Map<Integer, ArticleDetailData> categoryCache;

    private static ArticleDataRepository instance;

    private final int index = 0;

    public ArticleDataRepository(ArticleDataSource articleDataSource) {
        this.articleDataSource = articleDataSource;
    }

    public static ArticleDataRepository getInstance(ArticleDataSource articleDataSource) {
        if (instance == null) {
            instance = new ArticleDataRepository(articleDataSource);
        }
        return instance;
    }

    private void refreshArticleCache(boolean clearCache, List<ArticleDetailData> list) {
        if (articleCache == null) {
            articleCache = new LinkedHashMap<>();
        }
        if (clearCache) {
            articleCache.clear();
        }
        for (ArticleDetailData item : list) {
            articleCache.put(item.getId(), item);
        }
    }

    private void refreshQueryCache(boolean clearCache, List<ArticleDetailData> list) {
        if (queryCache == null) {
            queryCache = new LinkedHashMap<>();
        }
        if (clearCache) {
            queryCache.clear();
        }
        for (ArticleDetailData item : list) {
            queryCache.put(item.getId(), item);
        }
    }

    private void refreshCategoryCache(boolean clearCache, List<ArticleDetailData> list) {
        if (categoryCache == null) {
            categoryCache = new LinkedHashMap<>();
        }
        if (clearCache) {
            categoryCache.clear();
        }
        for (ArticleDetailData item : list) {
            categoryCache.put(item.getId(), item);
        }
    }

    @Override
    public Observable<List<ArticleDetailData>> getArticle(int page, boolean forceUpdate, final boolean clearCache) {
        if (!forceUpdate && articleCache != null) {
            return Observable.fromIterable(new ArrayList<>(articleCache.values()))
                    .toSortedList(new Comparator<ArticleDetailData>() {
                        @Override
                        public int compare(ArticleDetailData o1, ArticleDetailData o2) {
                            return o1.getPublishTime() > o2.getPublishTime() ? -1 : 1;
                        }
                    })
                    .toObservable();
        }
        if (!clearCache && articleCache != null) {
            Observable<List<ArticleDetailData>> obj1 = Observable.fromIterable(new ArrayList<>(articleCache.values()))
                    .toSortedList(new Comparator<ArticleDetailData>() {
                        @Override
                        public int compare(ArticleDetailData o1, ArticleDetailData o2) {
                            return o1.getPublishTime() > o2.getPublishTime() ? -1 : 1;
                        }
                    })
                    .toObservable();
            Observable<List<ArticleDetailData>> obj2 = articleDataSource.getArticle(page, forceUpdate, clearCache)
                    .doOnNext(new Consumer<List<ArticleDetailData>>() {
                        @Override
                        public void accept(List<ArticleDetailData> list) throws Exception {
                            refreshArticleCache(clearCache, list);
                        }
                    });
            return Observable.merge(obj1, obj2).collect(new Callable<List<ArticleDetailData>>() {

                @Override
                public List<ArticleDetailData> call() throws Exception {
                    return new ArrayList<>();
                }
            }, new BiConsumer<List<ArticleDetailData>, List<ArticleDetailData>>() {
                @Override
                public void accept(List<ArticleDetailData> list, List<ArticleDetailData> list2) throws Exception {
                    list.addAll(list2);
                }
            }).toObservable();
        }
        return articleDataSource.getArticle(index, forceUpdate, clearCache).doOnNext(new Consumer<List<ArticleDetailData>>() {
            @Override
            public void accept(List<ArticleDetailData> list) throws Exception {
                refreshArticleCache(clearCache, list);
            }
        });
    }

    @Override
    public Observable<List<ArticleDetailData>> queryArticle(int page, String keyword, boolean forceUpdate, final boolean clearCache) {
        if (!forceUpdate && queryCache != null) {
            return Observable.fromIterable(new ArrayList<>(queryCache.values()))
                    .toSortedList(new Comparator<ArticleDetailData>() {
                        @Override
                        public int compare(ArticleDetailData o1, ArticleDetailData o2) {
                            return o1.getPublishTime() > o2.getPublishTime() ? -1 : 1;
                        }
                    })
                    .toObservable();
        }
        if (!clearCache && queryCache != null) {
            Observable obj1 = Observable.fromIterable(new ArrayList<>(queryCache.values()))
                    .toSortedList(new Comparator<ArticleDetailData>() {
                        @Override
                        public int compare(ArticleDetailData o1, ArticleDetailData o2) {
                            return o1.getPublishTime() > o2.getPublishTime() ? -1 : 1;
                        }
                    })
                    .toObservable();
            Observable obj2 = articleDataSource.queryArticle(page, keyword, forceUpdate, clearCache)
                    .doOnNext(new Consumer<List<ArticleDetailData>>() {
                        @Override
                        public void accept(List<ArticleDetailData> list) throws Exception {
                            refreshQueryCache(clearCache, list);
                        }
                    });
            return Observable.merge(obj1, obj2).collect(new Callable<List<ArticleDetailData>>() {
                @Override
                public List<ArticleDetailData> call() throws Exception {
                    return new ArrayList<>();
                }
            }, new BiConsumer<List<ArticleDetailData>, List<ArticleDetailData>>() {
                @Override
                public void accept(List<ArticleDetailData> o, List<ArticleDetailData> o2) throws Exception {
                    o.addAll(o2);
                }
            }).toObservable();
        }
        return articleDataSource.queryArticle(page, keyword, forceUpdate, clearCache)
                .doOnNext(new Consumer<List<ArticleDetailData>>() {
                    @Override
                    public void accept(List<ArticleDetailData> list) throws Exception {
                        refreshQueryCache(clearCache, list);
                    }
                });
    }

    @Override
    public Observable<List<ArticleDetailData>> getArticleFromCatg(int page, int categoryId, boolean forceUpdate, final boolean clearCahce) {
        if (!forceUpdate && categoryCache != null) {
            return Observable.fromIterable(new ArrayList<>(categoryCache.values()))
                    .toSortedList(new Comparator<ArticleDetailData>() {
                        @Override
                        public int compare(ArticleDetailData o1, ArticleDetailData o2) {
                            return o1.getPublishTime() > o2.getPublishTime() ? -1 : 1;
                        }
                    })
                    .toObservable();
        }
        if (!clearCahce && categoryCache != null) {
            Observable<List<ArticleDetailData>> obj1 = Observable.fromIterable(new ArrayList<>(categoryCache.values()))
                    .toSortedList(new Comparator<ArticleDetailData>() {
                        @Override
                        public int compare(ArticleDetailData o1, ArticleDetailData o2) {
                            return o1.getPublishTime() > o2.getPublishTime() ? -1 : 1;
                        }
                    })
                    .toObservable();
            Observable<List<ArticleDetailData>> obj2 = articleDataSource.getArticleFromCatg(page, categoryId, forceUpdate, clearCahce)
                    .doOnNext(new Consumer<List<ArticleDetailData>>() {
                        @Override
                        public void accept(List<ArticleDetailData> list) throws Exception {
                            refreshCategoryCache(clearCahce, list);
                        }
                    });
            return Observable.merge(obj1, obj2)
                    .collect(new Callable<List<ArticleDetailData>>() {
                        @Override
                        public List<ArticleDetailData> call() throws Exception {
                            return new ArrayList<>();
                        }
                    }, new BiConsumer<List<ArticleDetailData>, List<ArticleDetailData>>() {
                        @Override
                        public void accept(List<ArticleDetailData> list, List<ArticleDetailData> list2) throws Exception {
                            list.addAll(list2);
                        }
                    })
                    .toObservable();
        }
        return articleDataSource.getArticleFromCatg(page, categoryId, forceUpdate, clearCahce)
                .doOnNext(new Consumer<List<ArticleDetailData>>() {
                    @Override
                    public void accept(List<ArticleDetailData> list) throws Exception {
                        refreshCategoryCache(clearCahce, list);
                    }
                });
    }
}
