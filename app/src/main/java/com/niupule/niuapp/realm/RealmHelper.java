package com.niupule.niuapp.realm;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 23:08
 * Desc: 数据库初始化操作
 * Version:
 */
public class RealmHelper {

    public static String DATABASE_NAME = "paul";

    public static Realm newRealmInstance() {
        return Realm.getInstance(new RealmConfiguration.Builder().name(DATABASE_NAME).deleteRealmIfMigrationNeeded().build());
    }

}
