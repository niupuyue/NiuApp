package com.niupule.niuapp.data.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/4
 * Time: 21:32
 * Desc:
 * Version:
 */
public class HotKeysData {

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

    public List<HotKeyDetailData> getData() {
        return data;
    }

    public void setData(List<HotKeyDetailData> data) {
        this.data = data;
    }

    @Expose
    @SerializedName("data")
    private List<HotKeyDetailData> data;

    @Expose
    @SerializedName("errorCode")
    private int errorCode;
    @Expose
    @SerializedName("errorMsg")
    private String errorMsg;

}
