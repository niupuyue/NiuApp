package com.niupule.niuapp.data.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/3
 * Time: 19:11
 * Desc: 进行远程登录操作的实体对象
 * Version:
 */
public class LoginData {

    @Expose
    @SerializedName("data")
    private LoginDetailData data;

    @Expose
    @SerializedName("errorCode")
    private int errorCode;

    @Expose
    @SerializedName("errorMsg")
    private String errorMsg;

    public LoginDetailData getData() {
        return data;
    }

    public void setData(LoginDetailData data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
