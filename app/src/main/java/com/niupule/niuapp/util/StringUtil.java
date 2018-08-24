package com.niupule.niuapp.util;

import android.text.TextUtils;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 18:31
 * Desc: 关于字符串操作的工具类
 * Version:
 */
public class StringUtil {

    public static boolean isInvalid(String ss){
        boolean flag = false;
        if(TextUtils.isEmpty(ss)||ss.contains(" ")){
            flag = true;
        }
        return false;
    }

}
