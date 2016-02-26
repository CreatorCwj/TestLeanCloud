package com.leancloud;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;

/**
 * Created by cwj on 16/2/26.
 * 防止activity结束后回调导致异常
 */
public abstract class ContextSaveCallback extends SaveCallback {

    private Context context;

    public ContextSaveCallback(@NonNull Context context) {
        this.context = context;
    }

    @Override
    final public void done(AVException e) {
        //如果context为空或是activity且结束了则不再回调
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing()))
            return;
        save(e);
    }

    public abstract void save(AVException e);
}
