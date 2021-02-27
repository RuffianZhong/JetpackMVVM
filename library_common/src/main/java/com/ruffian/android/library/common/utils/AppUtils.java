package com.ruffian.android.library.common.utils;

import android.app.Activity;
import android.content.Intent;

import com.ruffian.android.library.common.manager.ActivityManager;

/**
 * AppUtils
 *
 * @author ZhongDaFeng
 */
public class AppUtils {

    public static void triggerRebirth(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
        //Runtime.getRuntime().exit(0); // Kill kill kill!
    }

    public static void exit() {
        ActivityManager.get().finishAllActivity();//close activity
        try {
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        //System.exit(0);
        //Runtime.getRuntime().exit(0); // Kill kill kill!
    }

}