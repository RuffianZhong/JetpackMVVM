package com.ruffian.android.mvvm.viewbinding;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.adapters.ListenerUtil;

import com.ruffian.android.mvvm.R;
import com.ruffian.android.mvvm.utils.ImageLoaderUtils;

/**
 * 自定义ViewBindingAdapter
 * 备注：实现自定义名称和实现的事件绑定
 *
 * @author ZhongDaFeng
 */
public class CustomViewBindingAdapter {

    /**
     * 设置 EditText 事件
     *
     * @param view
     * @param beforeTextChanged
     * @param onTextChanged
     * @param afterTextChanged
     */
    @BindingAdapter(value = {"beforeTextChangedEvent", "onTextChangedEvent", "afterTextChangedEvent"}, requireAll = false)
    public static void setEditTextEvent(final EditText view,
                                        final IEditTextEvent.IBeforeTextChanged beforeTextChanged,
                                        final IEditTextEvent.IOnTextChanged onTextChanged,
                                        final IEditTextEvent.IAfterTextChanged afterTextChanged) {
        final TextWatcher newValue;
        if (beforeTextChanged == null && afterTextChanged == null && onTextChanged == null) {
            newValue = null;
        } else {
            newValue = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    if (beforeTextChanged != null) {
                        beforeTextChanged.beforeTextChanged(view, s, start, count, after);
                    }
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (onTextChanged != null) {
                        onTextChanged.onTextChanged(view, s, start, before, count);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (afterTextChanged != null) {
                        afterTextChanged.afterTextChanged(view, s);
                    }
                }
            };
        }
        final TextWatcher oldValue = ListenerUtil.trackListener(view, newValue, R.id.textWatcher);
        if (oldValue != null) {
            view.removeTextChangedListener(oldValue);
        }
        if (newValue != null) {
            view.addTextChangedListener(newValue);
        }
    }

    @BindingAdapter("imageUrl")
    public static void setImageURL(ImageView view, String url) {
        ImageLoaderUtils.load(view.getContext(), view, url);
    }


}
