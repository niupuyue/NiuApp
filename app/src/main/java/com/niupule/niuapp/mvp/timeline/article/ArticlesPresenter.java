package com.niupule.niuapp.mvp.timeline.article;

import com.niupule.niuapp.data.detail.ArticleDetailData;
import com.niupule.niuapp.data.detail.BannerDetailData;
import com.niupule.niuapp.data.detail.LoginData;
import com.niupule.niuapp.data.source.ArticleDataRepository;
import com.niupule.niuapp.data.source.ArticleDataSource;
import com.niupule.niuapp.data.source.BannerDataRepository;
import com.niupule.niuapp.data.source.LoginDataRepository;
import com.niupule.niuapp.data.type.LoginType;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/11
 * Time: 20:11
 * Desc:
 * Version:
 */
public class ArticlesPresenter implements ArticlesContract.ArticlesPresenter {

    private ArticleDataRepository articleDataRepository;
    private CompositeDisposable compositeDisposable;
    private BannerDataRepository bannerDataRepository;
    private LoginDataRepository loginDataRepository;
    private ArticlesContract.ArticlesView articlesView;

    public ArticlesPresenter(ArticleDataRepository articleDataRepository,BannerDataRepository bannerDataRepository,LoginDataRepository loginDataRepository,ArticlesContract.ArticlesView articlesView){
        this.articleDataRepository = articleDataRepository;
        this.bannerDataRepository = bannerDataRepository;
        this.loginDataRepository = loginDataRepository;
        this.articlesView = articlesView;
        this.articlesView.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getArticles(int page, boolean forceUpdate, boolean cleanCache) {
        Disposable disposable = articleDataRepository.getArticle(page,forceUpdate,cleanCache)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<ArticleDetailData>>() {
                    @Override
                    public void onNext(List<ArticleDetailData> value) {
                        if (articlesView.isActive()){
                            articlesView.showEmptyView(true);
                            articlesView.showArticles(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (articlesView.isActive()){
                            articlesView.showEmptyView(true);
                            articlesView.setLoadingIndicator(false);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (articlesView.isActive()){
                            articlesView.setLoadingIndicator(false);
                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void getBanner() {
        Disposable disposable = bannerDataRepository.getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<BannerDetailData>>() {
                    @Override
                    public void onNext(List<BannerDetailData> value) {
                        if (articlesView.isActive()){
                            articlesView.showBannder(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (articlesView.isActive()){
                            articlesView.hideBanner();
                            articlesView.setLoadingIndicator(false);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (articlesView.isActive()){
                            articlesView.setLoadingIndicator(false);
                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void autoLogin(String username, String password) {
        Disposable disposable = loginDataRepository.getRemoteLoginData(username,password, LoginType.TYPE_LOGIN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginData>() {
                    @Override
                    public void onNext(LoginData value) {
                        if (articlesView.isActive() && value.getErrorCode() == -1){
                            articlesView.showAutoLoginFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (articlesView.isActive()){
                            articlesView.showAutoLoginFail();
                        }
                    }

                    @Override
                    public void onComplete() {

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
