package com.ruffian.android.mvvm.common;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import com.ruffian.android.mvvm.MainActivity;

import java.util.Stack;


/**
 * Activity栈管理
 *
 * @author ZhongDaFeng
 */
public class ActivityManager {

    private static final String TAG = "ActivityManager";
    private static volatile ActivityManager instance;//单例
    private static volatile Stack<Activity> activityStack;// 栈
    private Context context;// 当前上下文对象

    /**
     * 私有构造
     */
    private ActivityManager() {
        activityStack = new Stack<Activity>();
    }

    /**
     * 单例实例
     *
     * @return
     */
    public static ActivityManager get() {
        if (instance == null) {
            synchronized (ActivityManager.class) {
                if (instance == null) {
                    instance = new ActivityManager();
                }
            }
        }
        return instance;
    }

    /**
     * 压栈
     *
     * @param activity
     */
    public void push(Activity activity) {
        activityStack.push(activity);
    }

    /**
     * 出栈
     *
     * @return
     */
    public Activity pop() {
        if (activityStack.isEmpty())
            return null;
        return activityStack.pop();
    }

    /**
     * 栈顶
     *
     * @return
     */
    public Activity peek() {
        if (activityStack.isEmpty())
            return null;
        return activityStack.peek();
    }

    /**
     * 移除
     *
     * @param activity
     */
    public void remove(Activity activity) {
        if (activityStack.size() > 0 && activity == activityStack.peek())
            activityStack.pop();
        else
            activityStack.remove(activity);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        while (!activityStack.isEmpty()) {
            activityStack.pop().finish();
        }
    }

    /**
     * 是否存在栈
     *
     * @param activity
     * @return
     */
    public boolean contains(Activity activity) {
        return activityStack.contains(activity);
    }

    /**
     * 当前上下文对象
     *
     * @return
     */
    public Context context() {
        return peek();
    }


    /**
     * 退出应用程序
     *
     * @param context
     */
    public void exitApp(Context context) {
        try {
            finishAllActivity();
            //清除通知栏
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除 targetCls 以及往上的栈集合
     * 例如：
     * 1.AActivity
     * 2.BActivity
     * 3.CActivity
     * 4.DActivity
     * targetCls = CActivity 处理完成栈中剩余
     * 4.DActivity
     *
     * @param targetCls
     */
    public void removeTargetAndAboveStackMap(Class targetCls) {
        while (!activityStack.isEmpty()) {
            Activity activity = activityStack.peek();
            String actName = activity.getLocalClassName();
            String targetName = targetCls.getCanonicalName();
            if (targetName.equals(actName)) {
                Log.e(TAG, "======匹配到目标页面退出并且跳出循环:" + activity);
                activityStack.pop().finish();
                break;
            } else {
                //退到主页跳出循环,避免使用不当直接退出整个应用
                if (activity.equals(MainActivity.class.getCanonicalName())) break;
                Log.e(TAG, "======移除目标之上的页面:" + activity);
                activityStack.pop().finish();
            }
        }
    }

}