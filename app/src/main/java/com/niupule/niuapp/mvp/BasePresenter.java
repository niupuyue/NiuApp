package com.niupule.niuapp.mvp;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 23:03
 * Desc: 实现具体操作的接口，如网络请求，数据库操作等
 * Version:
 */
public interface BasePresenter {

    //订阅事件
    void subscribe();

    //解绑事件
    void unsubscribe();

}
