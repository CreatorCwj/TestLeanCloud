package com.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by cwj on 15/12/3.
 * 工具类
 */
public class Utils {

    /**
     * Toast提示
     */
    public static void showToast(Context context, String content, int duration) {
        if (context == null)
            return;
        Toast.makeText(context, content, duration).show();
    }

    public static void showToast(Context context, String content) {
        showToast(context, content, Toast.LENGTH_SHORT);
    }
}
