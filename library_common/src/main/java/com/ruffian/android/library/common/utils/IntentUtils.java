package com.ruffian.android.library.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.util.List;


/**
 * IntentUtils
 */
public class IntentUtils {


    public static void startActivity(Context context, Class<?> gotoClass) {
        startActivity(context, gotoClass, null);
    }

    public static void startActivity(Context context, Class<?> gotoClass, Bundle bundle) {
        startActivity(context, gotoClass, bundle, "", false, true);
    }

    public static void startActivity(Context context, Class<?> gotoClass, Bundle bundle, int requestCode) {
        startActivity(context, gotoClass, bundle, String.valueOf(requestCode), false, true);
    }

    public static void startActivity(Context context, Class<?> gotoClass, @Nullable Bundle bundle, String requestCode, boolean finishSelf, boolean withAnim) {
        Intent intent = new Intent();
        intent.setClass(context, gotoClass);

        if (bundle != null) intent.putExtras(bundle);

        if (!TextUtils.isEmpty(requestCode))
            ((Activity) context).startActivityForResult(intent, Integer.parseInt(requestCode));
        else
            context.startActivity(intent);

        if (finishSelf) ((Activity) context).finish();

        //if(withAnim) ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }


    public static void startActivityForResult(Context context, Class<?> gotoClass, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, gotoClass);
        ((Activity) context).startActivityForResult(intent, requestCode);
        // ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }


    public static void finish(Activity activity) {
        activity.finish();
        // activity.overridePendingTransition(R.anim.exit_enter, R.anim.exit_exit);
    }


    /**
     * 跳转到浏览器
     *
     * @param url
     */
    public static void startActivityBrowser(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(intent, 0);
        if (list.size() > 0) {
            context.startActivity(intent);
        }
    }


    /**
     * 跳转到设置页面
     *
     * @param activity
     */
    public static void startActivitySettings(Activity activity) {
        try {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到系统邮箱
     *
     * @param context
     */
    public static void startActivityEmail(Context context, String uriString, String title) {
        Uri uri = Uri.parse(uriString);//mailto:support@qq.com
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        intent.putExtra(Intent.EXTRA_TEXT, "content");
        context.startActivity(Intent.createChooser(intent, title));
    }

}
