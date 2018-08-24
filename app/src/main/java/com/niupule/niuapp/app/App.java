package com.niupule.niuapp.app;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 16:24
 * Desc:
 * Version:
 */
public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Realm.init(context);

    }

    public static Context getInstance(){
        return context;
    }
}
