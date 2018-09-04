package com.niupule.niuapp.data.source.remote;

import com.niupule.niuapp.data.detail.HotKeyDetailData;
import com.niupule.niuapp.data.detail.HotKeysData;
import com.niupule.niuapp.data.source.HotkeyDataSource;
import com.niupule.niuapp.retrofit.RetrofitClient;
import com.niupule.niuapp.retrofit.RetrofitService;

import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/4
 * Time: 21:29
 * Desc:
 * Version:
 */
public class HotkeyDataRemoteSource implements HotkeyDataSource {

    private static HotkeyDataRemoteSource instance;

    public static HotkeyDataRemoteSource getInstance() {
        if (instance == null) {
            instance = new HotkeyDataRemoteSource();
        }
        return instance;
    }

    @Override
    public Observable<List<HotKeyDetailData>> getHotKeys(boolean forceUpdate) {
        return RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getHotkeys()
                .filter(new Predicate<HotKeysData>() {
                    @Override
                    public boolean test(HotKeysData hotKeysData) throws Exception {
                        return hotKeysData.getErrorCode() != -1;
                    }
                })
                .flatMap(new Function<HotKeysData, ObservableSource<List<HotKeyDetailData>>>() {
                    @Override
                    public ObservableSource<List<HotKeyDetailData>> apply(HotKeysData hotKeysData) throws Exception {
                        return Observable.fromIterable(hotKeysData.getData()).toSortedList(new Comparator<HotKeyDetailData>() {
                            @Override
                            public int compare(HotKeyDetailData o1, HotKeyDetailData o2) {
                                return o1.getOrder() > o2.getOrder() ? 1 : -1;
                            }
                        }).toObservable();
                    }
                })
                ;
    }
}
