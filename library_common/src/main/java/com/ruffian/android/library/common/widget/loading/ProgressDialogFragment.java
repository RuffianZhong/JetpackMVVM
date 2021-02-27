package com.ruffian.android.library.common.widget.loading;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.ruffian.android.library.common.R;


/**
 * LoadingDialog
 */
public class ProgressDialogFragment extends DialogFragment {

    private static final String K_CANCELABLE = "k_cancelable";
    private boolean mCancelable;

    public static ProgressDialogFragment newInstance(boolean cancelable) {
        ProgressDialogFragment dialog = new ProgressDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(K_CANCELABLE, cancelable);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseArgs(getArguments());
        setCancelable(mCancelable);
        //启用窗体的扩展特性
        setStyle(DialogFragment.STYLE_NORMAL, R.style.LoadingDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loading_dialog, container, false);
        return view;
    }

    private void parseArgs(Bundle args) {
        if (args != null) {
            mCancelable = args.getBoolean(K_CANCELABLE);
        }
    }

    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

}
