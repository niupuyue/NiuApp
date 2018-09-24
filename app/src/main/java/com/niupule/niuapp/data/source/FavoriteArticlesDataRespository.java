package com.niupule.niuapp.data.source;

import com.niupule.niuapp.data.detail.FavoriteArticleDetailData;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

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
    public Observable<List<FavoriteArticleDetailData>> getFavoriteArticles(int page, boolean forceUpdate, boolean clearCache) {
        return null;
    }

    @Override
    public boolean isExist(int userId, int id) {
        return false;
    }
}
