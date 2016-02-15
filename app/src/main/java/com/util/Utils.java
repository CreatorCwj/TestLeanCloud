package com.util;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.application.MyApplication;

/**
 * Created by cwj on 15/12/3.
 * 工具类
 */
public class Utils {

    /**
     * Toast提示
     */
    public static void showToast(Context context, String content, int duration) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing()))
            return;
        Toast.makeText(context, content, duration).show();
    }

    public static void showToast(Context context, String content) {
        showToast(context, content, Toast.LENGTH_SHORT);
    }

    /**
     * Snack提示
     */
    public static void showSnack(View view, String content, int duration) {
        if (view == null)
            return;
        Snackbar.make(view, content, duration).show();
    }

    public static void showSnack(View view, String content) {
        showSnack(view, content, Snackbar.LENGTH_SHORT);
    }

    /**
     * 测试时间用
     */
    private static long startTime;

    public static void startTime() {
        startTime = System.currentTimeMillis();
    }

    public static void endTime() {
        long endTime = System.currentTimeMillis();
        Utils.showToast(MyApplication.getAppContext(), "" + (endTime - startTime));
    }

}
