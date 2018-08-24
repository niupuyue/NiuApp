package com.niupule.niuapp.mvp;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 23:06
 * Desc: 视图界面回调方法，一般用来实现一些UI布局
 * Version:
 */
public interface BaseView<T> {

    void setPresenter(T presenter);

}
