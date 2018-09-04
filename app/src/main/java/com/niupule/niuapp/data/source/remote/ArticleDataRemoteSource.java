package com.niupule.niuapp.data.source.remote;

import com.niupule.niuapp.data.detail.ArticleData;
import com.niupule.niuapp.data.detail.ArticleDetailData;
import com.niupule.niuapp.data.source.ArticleDataSource;
import com.niupule.niuapp.realm.RealmHelper;
import com.niupule.niuapp.retrofit.RetrofitClient;
import com.niupule.niuapp.retrofit.RetrofitService;

import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Retrofit;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/4
 * Time: 21:37
 * Desc:
 * Version:
 */
public class ArticleDataRemoteSource implements ArticleDataSource {

    private static ArticleDataRemoteSource instance;

    private ArticleDataRemoteSource() {
    }

    public static ArticleDataRemoteSource getInstance() {
        if (instance == null) {
            instance = new ArticleDataRemoteSource();
        }
        return instance;
    }

    @Override
    public Observable<List<ArticleDetailData>> getArticle(int page, boolean forceUpdate, boolean clearCache) {
        return RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getArticles(page)
                .filter(new Predicate<ArticleData>() {
                    @Override
                    public boolean test(ArticleData articleData) throws Exception {
                        return articleData.getErrorCode() != -1;
                    }
                })
                .flatMap(new Function<ArticleData, ObservableSource<List<ArticleDetailData>>>() {
                    @Override
                    public ObservableSource<List<ArticleDetailData>> apply(ArticleData articleData) throws Exception {
                        return Observable.fromIterable(articleData.getData().getDatas())
                                .toSortedList(new Comparator<ArticleDetailData>() {
                                    @Override
                                    public int compare(ArticleDetailData o1, ArticleDetailData o2) {
                                        return o1.getPublishTime() > o2.getPublishTime() ? -1 : 1;
                                    }
                                })
                                .toObservable()
                                .doOnNext(new Consumer<List<ArticleDetailData>>() {
                                    @Override
                                    public void accept(List<ArticleDetailData> list) throws Exception {
                                        for (ArticleDetailData item : list) {
                                            saveToRealm(item);
                                        }
                                    }
                                });
                    }
                });
    }

    /**
     * 保存到数据库中
     *
     * @param item
     */
    private void saveToRealm(ArticleDetailData item) {
        Realm realm = Realm.getInstance(new RealmConfiguration.Builder()
                .name(RealmHelper.DATABASE_NAME)
                .deleteRealmIfMigrationNeeded()
                .build());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(item);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public Observable<List<ArticleDetailData>> queryArticle(int page, String keyword, boolean forceUpdate, boolean clearCache) {
        return RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .queryArticle(page, keyword)
                .filter(new Predicate<ArticleData>() {
                    @Override
                    public boolean test(ArticleData articleData) throws Exception {
                        return articleData.getErrorCode() != -1;
                    }
                })
                .flatMap(new Function<ArticleData, ObservableSource<List<ArticleDetailData>>>() {
                    @Override
                    public ObservableSource<List<ArticleDetailData>> apply(ArticleData articleData) throws Exception {
                        return Observable.fromIterable(articleData.getData().getDatas())
                                .toSortedList(new Comparator<ArticleDetailData>() {
                                    @Override
                                    public int compare(ArticleDetailData o1, ArticleDetailData o2) {
                                        return o1.getPublishTime() > o2.getPublishTime() ? -1 : 1;
                                    }
                                })
                                .toObservable()
                                .doOnNext(new Consumer<List<ArticleDetailData>>() {
                                    @Override
                                    public void accept(List<ArticleDetailData> list) throws Exception {
                                        for (ArticleDetailData item : list) {
                                            saveToRealm(item);
                                        }
                                    }
                                });
                    }
                });
    }

    @Override
    public Observable<List<ArticleDetailData>> getArticleFromCatg(int page, int categoryId, boolean forceUpdate, boolean clearCahce) {
        return RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getArticleFromCateg(page, categoryId)
                .filter(new Predicate<ArticleData>() {
                    @Override
                    public boolean test(ArticleData articleData) throws Exception {
                        return articleData.getErrorCode() != -1;
                    }
                })
                .flatMap(new Function<ArticleData, ObservableSource<List<ArticleDetailData>>>() {
                    @Override
                    public ObservableSource<List<ArticleDetailData>> apply(ArticleData articleData) throws Exception {
                        return Observable.fromIterable(articleData.getData().getDatas())
                                .toSortedList(new Comparator<ArticleDetailData>() {
                                    @Override
                                    public int compare(ArticleDetailData o1, ArticleDetailData o2) {
                                        return o1.getPublishTime() > o2.getPublishTime() ? -1 : 1;
                                    }
                                })
                                .toObservable()
                                .doOnNext(new Consumer<List<ArticleDetailData>>() {
                                    @Override
                                    public void accept(List<ArticleDetailData> list) throws Exception {
                                        for (ArticleDetailData item : list) {
                                            saveToRealm(item);
                                        }
                                    }
                                });
                    }
                });
    }
}
