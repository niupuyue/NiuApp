package com.niupule.niuapp.data.source;

import com.niupule.niuapp.data.detail.ArticleDetailData;

import java.util.List;

import io.reactivex.Observable;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/4
 * Time: 20:39
 * Desc:
 * Version:
 */
public interface ArticleDataSource {

    Observable<List<ArticleDetailData>> getArticle(int page,boolean forceUpdate ,boolean clearCache);

    Observable<List<ArticleDetailData>> queryArticle(int page,String keyword,boolean forceUpdate,boolean clearCache);

    Observable<List<ArticleDetailData>> getArticleFromCatg(int page,int categoryId,boolean forceUpdate,boolean clearCahce);

}
