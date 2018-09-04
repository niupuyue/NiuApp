package com.niupule.niuapp.data.source;

import com.niupule.niuapp.data.detail.HotKeyDetailData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/4
 * Time: 13:39
 * Desc:
 * Version:
 */
public class HotkeyDataRespository implements HotkeyDataSource {

    private static HotkeyDataRespository instance;
    private HotkeyDataSource remote;
    private Map<Integer, HotKeyDetailData> cache;

    private HotkeyDataRespository(HotkeyDataSource remote) {
        this.remote = remote;
    }

    public static HotkeyDataRespository getInstance(HotkeyDataSource remote) {
        if (instance == null) {
            instance = new HotkeyDataRespository(remote);
        }
        return instance;
    }

    @Override
    public Observable<List<HotKeyDetailData>> getHotKeys(final boolean forceUpdate) {
        if (!forceUpdate && cache != null) {
            return Observable.fromIterable(new ArrayList<>(cache.values())).toSortedList(new Comparator<HotKeyDetailData>() {
                @Override
                public int compare(HotKeyDetailData o1, HotKeyDetailData o2) {
                    return o1.getOrder() > o2.getOrder() ? 1 : -1;
                }
            }).toObservable();
        }
        return remote.getHotKeys(forceUpdate).doOnNext(new Consumer<List<HotKeyDetailData>>() {
            @Override
            public void accept(List<HotKeyDetailData> hotKeyDetailData) throws Exception {
                refreshCache(forceUpdate, hotKeyDetailData);
            }
        });
    }

    protected void refreshCache(boolean forceUpdate, List<HotKeyDetailData> list) {
        if (cache == null) {
            cache = new LinkedHashMap<>();
        }
        if (forceUpdate) {
            cache.clear();
        }
        for (HotKeyDetailData data : list) {
            cache.put(data.getId(), data);
        }
    }
}
