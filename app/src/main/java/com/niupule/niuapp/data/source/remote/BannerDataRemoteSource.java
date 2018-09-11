package com.niupule.niuapp.data.source.remote;

import com.niupule.niuapp.data.detail.BannerData;
import com.niupule.niuapp.data.detail.BannerDetailData;
import com.niupule.niuapp.data.source.BannerDataSource;
import com.niupule.niuapp.retrofit.RetrofitClient;
import com.niupule.niuapp.retrofit.RetrofitService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/11
 * Time: 21:33
 * Desc:
 * Version:
 */
public class BannerDataRemoteSource implements BannerDataSource {

    private static BannerDataRemoteSource instance;

    private BannerDataRemoteSource(){

    }

    public static BannerDataRemoteSource getInstance(){
        if (instance == null){
            instance = new BannerDataRemoteSource();
        }
        return instance;
    }

    @Override
    public Observable<List<BannerDetailData>> getBanner() {
        return RetrofitClient.getInstance().create(RetrofitService.class)
                .getBanner()
                .filter(new Predicate<BannerData>() {
                    @Override
                    public boolean test(BannerData bannerData) throws Exception {
                        return bannerData.getErrorCode() != -1;
                    }
                })
                .flatMap(new Function<BannerData, ObservableSource<List<BannerDetailData>>>() {
                    @Override
                    public ObservableSource<List<BannerDetailData>> apply(BannerData bannerData) throws Exception {
                        return Observable.fromIterable(bannerData.getData()).toList().toObservable();
                    }
                });
    }
}
