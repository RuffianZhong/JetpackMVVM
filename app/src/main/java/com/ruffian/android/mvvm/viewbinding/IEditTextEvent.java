package com.ruffian.android.mvvm.viewbinding;

import android.text.Editable;
import android.widget.EditText;

/**
 * IEditTextEvent
 *
 * @author ZhongDaFeng
 */
public interface IEditTextEvent {

    public interface IBeforeTextChanged {
        public void beforeTextChanged(EditText editText, CharSequence s, int start, int count, int after);
    }

    public interface IOnTextChanged {
        public void onTextChanged(EditText editText, CharSequence s, int start, int before, int count);
    }

    public interface IAfterTextChanged {
        public void afterTextChanged(EditText editText, Editable editable);
    }

}
