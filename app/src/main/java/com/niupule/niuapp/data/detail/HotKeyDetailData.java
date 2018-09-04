package com.niupule.niuapp.data.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/4
 * Time: 10:48
 * Desc:
 * Version:
 */
public class HotKeyDetailData extends RealmObject {

    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("link")
    private String link;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("order")
    private int order;
    @Expose
    @SerializedName("visible")
    private int visible;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
}
