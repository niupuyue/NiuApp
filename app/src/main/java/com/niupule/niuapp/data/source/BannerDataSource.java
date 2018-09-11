package com.niupule.niuapp.data.source;

import com.niupule.niuapp.data.detail.BannerDetailData;

import java.util.List;

import io.reactivex.Observable;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/11
 * Time: 20:37
 * Desc:
 * Version:
 */
public interface BannerDataSource {
    Observable<List<BannerDetailData>> getBanner();

}
