package com.niupule.niuapp.mvp.search;

import com.niupule.niuapp.data.detail.ArticleDetailData;
import com.niupule.niuapp.data.detail.HotKeyDetailData;
import com.niupule.niuapp.data.source.ArticleDataRepository;
import com.niupule.niuapp.data.source.HotkeyDataRespository;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/4
 * Time: 10:50
 * Desc:
 * Version:
 */
public class SearchPresenter implements SearchContract.SearchPresenter {

    private HotkeyDataRespository hotkeyDataRespository;
    private CompositeDisposable compositeDisposable;
    private ArticleDataRepository articleDataRepository;
    private SearchContract.SearchView view;

    public SearchPresenter(HotkeyDataRespository hotkeyDataRespository,ArticleDataRepository articleDataRepository,SearchContract.SearchView view){
        this.hotkeyDataRespository = hotkeyDataRespository;
        this.articleDataRepository = articleDataRepository;
        this.view = view;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void getHotKeys(boolean forcesUpdate) {
        Disposable disposable = hotkeyDataRespository.getHotKeys(forcesUpdate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<HotKeyDetailData>>() {

                    @Override
                    public void onNext(List<HotKeyDetailData> value) {
                        if (view.isActivie()) {
                            view.showHotKeys(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void searchArticles(int page, String keywords, boolean forcesUpdate, boolean clearCache) {
        Disposable disposable = articleDataRepository.queryArticle(page,keywords,forcesUpdate,clearCache)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<ArticleDetailData>>() {
                    @Override
                    public void onNext(List<ArticleDetailData> value) {
                        if (view.isActivie()){
                            view.showArticles(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showEmptyView(true);
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
