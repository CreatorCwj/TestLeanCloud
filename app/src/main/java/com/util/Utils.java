package com.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by cwj on 15/12/3.
 */
public class Utils {

    public static void showToast(Context context, String content) {
        if (context == null || content == null)
            return;
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
