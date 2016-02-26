package com.leancloud;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.CountCallback;

/**
 * Created by cwj on 16/2/26.
 * 防止activity结束后回调导致异常
 */
public abstract class ContextCountCallback extends CountCallback {

    private Context context;

    public ContextCountCallback(@NonNull Context context) {
        this.context = context;
    }

    @Override
    final public void done(int i, AVException e) {
        //如果context为空或是activity且结束了则不再回调
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing()))
            return;
        getCount(i, e);
    }

    public abstract void getCount(int i, AVException e);
}
