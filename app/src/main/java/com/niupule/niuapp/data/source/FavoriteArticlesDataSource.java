package com.niupule.niuapp.data.source;

import com.niupule.niuapp.data.detail.FavoriteArticleDetailData;

import java.util.List;

import io.reactivex.Observable;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/24
 * Time: 17:41
 * Desc:
 * Version:
 */
public interface FavoriteArticlesDataSource {

    Observable<List<FavoriteArticleDetailData>> getFavoriteArticles(int page, boolean forceUpdate, boolean clearCache);

    boolean isExist(int userId,int id);

}
