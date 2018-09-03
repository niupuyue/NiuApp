package com.niupule.niuapp.data.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/25
 * Time: 19:56
 * Desc:
 * Version:
 */
public class LoginDetailData extends RealmObject {

    @Expose
    @SerializedName("icon")
    private String icon;

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("username")
    @PrimaryKey
    private String username;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("type")
    private int type;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
