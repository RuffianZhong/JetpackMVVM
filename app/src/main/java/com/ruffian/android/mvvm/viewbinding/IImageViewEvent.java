package com.ruffian.android.mvvm.viewbinding;

import android.widget.ImageView;

/**
 * IEditTextEvent
 *
 * @author ZhongDaFeng
 */
public interface IImageViewEvent {

    public interface ILoadImage {
        public void loadImage(ImageView imageView, String url);
    }
}
