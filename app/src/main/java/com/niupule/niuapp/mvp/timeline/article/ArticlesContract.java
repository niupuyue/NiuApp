package com.niupule.niuapp.mvp.timeline.article;

import com.niupule.niuapp.data.detail.ArticleDetailData;
import com.niupule.niuapp.data.detail.BannerDetailData;
import com.niupule.niuapp.mvp.BasePresenter;
import com.niupule.niuapp.mvp.BaseView;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/10
 * Time: 22:40
 * Desc:
 * Version:
 */
public interface ArticlesContract {

    interface ArticlesPresenter extends BasePresenter{
        void getArticles(int page,boolean forceUpdate,boolean cleanCache);

        void getBanner();

        void autoLogin(String username,String password);
    }

    interface ArticlesView extends BaseView<ArticlesPresenter>{
        boolean isActive();

        void setLoadingIndicator(boolean isActive);

        void showArticles(List<ArticleDetailData> list);

        void showEmptyView(boolean toShow);

        void showBannder(List<BannerDetailData> list);

        void hideBanner();

        void showAutoLoginFail();
    }

}
