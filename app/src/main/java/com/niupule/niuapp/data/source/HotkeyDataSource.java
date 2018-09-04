package com.niupule.niuapp.data.source;

import com.niupule.niuapp.data.detail.HotKeyDetailData;

import java.util.List;

import io.reactivex.Observable;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/4
 * Time: 13:36
 * Desc: 获取热搜词汇
 * Version:
 */
public interface HotkeyDataSource {

    Observable<List<HotKeyDetailData>> getHotKeys(boolean forceUpdate);

}
