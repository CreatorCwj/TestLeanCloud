package com.model;

import com.avos.avoscloud.AVUser;

/**
 * Created by cwj on 15/12/3.
 */
public class User extends AVUser {
    public static final String DISPLAY_NAME = "displayName";

    public void setDisplayName(String displayName) {
        put(DISPLAY_NAME, displayName);
    }

    public String getDisplayName() {
        return getString(DISPLAY_NAME);
    }
}
