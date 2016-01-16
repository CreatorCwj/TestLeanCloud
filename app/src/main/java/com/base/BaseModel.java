package com.base;

import android.os.Parcel;

import com.avos.avoscloud.AVObject;

/**
 * Created by cwj on 15/11/29.
 */
public abstract class BaseModel extends AVObject {

    protected BaseModel() {

    }

    protected BaseModel(Parcel in) {
        super(in);
    }

    public String text() {
        return "";
    }

}
