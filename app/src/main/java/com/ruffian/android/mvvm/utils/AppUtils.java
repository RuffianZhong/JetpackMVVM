package com.ruffian.android.mvvm.utils;

import android.app.Activity;
import android.content.Intent;

import com.ruffian.android.mvvm.module.account.activity.LoginActivity;
import com.ruffian.android.mvvm.common.ActivityManager;

/**
 * AppUtils
 *
 * @author ZhongDaFeng
 */
public class AppUtils {

    public static void triggerRebirth(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
        Runtime.getRuntime().exit(0); // Kill kill kill!
    }

    public static void exit() {
        ActivityManager.get().finishAllActivity();//close activity
        Runtime.getRuntime().exit(0); // Kill kill kill!
    }

}