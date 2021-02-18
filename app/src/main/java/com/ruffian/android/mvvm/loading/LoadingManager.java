package com.ruffian.android.mvvm.loading;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ruffian.android.mvvm.common.ActivityManager;

/**
 * LoadingManager
 * 备注：全局唯一 Loading 弹窗，多个请求时，开发者自行处理弹窗显示隐藏逻辑
 *
 * @author ZhongDaFeng
 */
public class LoadingManager {

    private static final String DIALOG_PROGRESS_TAG = "progress";
    private FragmentManager mFragmentManager;
    private ProgressDialogFragment mProgressDialog;

    private static volatile LoadingManager instance;

    public static LoadingManager get() {
        if (instance == null) {
            synchronized (LoadingManager.class) {
                if (instance == null) {
                    instance = new LoadingManager();
                }
            }
        }
        return instance;
    }

    private LoadingManager() {
    }

    /**
     * 展示Loading
     */
    public void showProgressDialog() {
        if (mFragmentManager == null)
            mFragmentManager = ((FragmentActivity) ActivityManager.get().context()).getSupportFragmentManager();
        //为了不重复显示dialog，在显示对话框之前移除正在显示的对话框
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(DIALOG_PROGRESS_TAG);
        if (null != fragment) ft.remove(fragment).commitAllowingStateLoss();

        //缓存并展示
        mProgressDialog = ProgressDialogFragment.newInstance(false);//默认不可取消
        mProgressDialog.show(mFragmentManager, DIALOG_PROGRESS_TAG);
    }


    /**
     * 关闭Loading
     */
    public void dismissLoading() {
        if (mFragmentManager != null) {
            Fragment fragment = mFragmentManager.findFragmentByTag(DIALOG_PROGRESS_TAG);
            if (null != fragment) {
                ((ProgressDialogFragment) fragment).dismissAllowingStateLoss();
                mFragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
            }
        }
        //无法预料存在多个loading框时取消
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

}