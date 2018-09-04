package com.niupule.niuapp.data.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/4
 * Time: 10:46
 * Desc:
 * Version:
 */
public class ArticleDetailInnerData extends RealmObject {
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("url")
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
