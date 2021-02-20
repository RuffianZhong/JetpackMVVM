package com.ruffian.android.mvvm.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ruffian.android.mvvm.R;


/**
 * ImageLoaderUtils
 *
 * @author ZhongDaFeng
 */
public class ImageLoaderUtils {

    public static void load(Context context, ImageView imageView, Object obj) {
        load(context, imageView, obj, R.mipmap.ic_launcher, R.mipmap.ic_launcher, false);
    }

    public static void loadGif(Context context, ImageView imageView, Object obj) {
        load(context, imageView, obj, R.mipmap.ic_launcher, R.mipmap.ic_launcher, true);
    }

    public static void load(Context context, ImageView imageView, Object obj, int placeholderId, int errorRes, boolean asGif) {
        DrawableTypeRequest drawableTypeRequest = Glide.with(context).load(obj);
        if (asGif) drawableTypeRequest.asGif();
        drawableTypeRequest.placeholder(placeholderId).dontAnimate().error(errorRes).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }

}
