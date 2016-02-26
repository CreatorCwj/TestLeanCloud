package com.leancloud;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;

import java.util.List;

/**
 * Created by cwj on 16/2/26.
 * 防止activity结束后回调导致异常
 */
public abstract class ContextFindCallback<T extends AVObject> extends FindCallback<T> {

    private Context context;

    public ContextFindCallback(@NonNull Context context) {
        this.context = context;
    }

    @Override
    final public void done(List<T> list, AVException e) {
        //如果context为空或是activity且结束了则不再回调
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing()))
            return;
        findResult(list, e);
    }

    public abstract void findResult(List<T> objects, AVException e);
}
