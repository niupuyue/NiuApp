package com.niupule.niuapp.mvp.timeline.readlater;

import com.niupule.niuapp.data.detail.ArticleDetailData;
import com.niupule.niuapp.mvp.BasePresenter;
import com.niupule.niuapp.mvp.BaseView;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/10
 * Time: 22:50
 * Desc:
 * Version:
 */
public interface ReaderLaterContract {

    interface ReaderLaterPresenter extends BasePresenter{
        void getReaderLaterArticles(int userId);
    }

    interface ReaderLaterView extends BaseView<ReaderLaterPresenter>{
        void showReaderLaterArticles(List<ArticleDetailData> list);

        boolean isActive();

        void showEmptyView(boolean toShow);
    }

}
