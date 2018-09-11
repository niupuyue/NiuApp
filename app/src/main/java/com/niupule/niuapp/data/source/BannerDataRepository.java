package com.niupule.niuapp.data.source;

import com.niupule.niuapp.data.detail.BannerDetailData;

import java.util.List;

import io.reactivex.Observable;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/11
 * Time: 20:38
 * Desc:
 * Version:
 */
public class BannerDataRepository implements BannerDataSource {

    private BannerDataSource remote;

    private static BannerDataRepository instance;

    private BannerDataRepository(BannerDataSource remote){
        this.remote = remote;
    }

    public static BannerDataRepository getInstance(BannerDataSource remote){
        if (instance == null){
            instance = new BannerDataRepository(remote);
        }
        return instance;
    }

    @Override
    public Observable<List<BannerDetailData>> getBanner() {
        return remote.getBanner();
    }
}
