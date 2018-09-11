package com.niupule.niuapp.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/11
 * Time: 21:19
 * Desc:
 * Version:
 */
public class GlideLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}
