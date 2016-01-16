package com.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by cwj on 16/1/14.
 */
public class UIUtils {

    /**
     * 将dp单位转为px单位
     *
     */
    public static int dp2px(Context context, float dpValue) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());

    }
}
