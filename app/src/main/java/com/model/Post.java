package com.model;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.base.BaseModel;

/**
 * Created by cwj on 15/11/29.
 */
@AVClassName("Post")
public class Post extends BaseModel {

    public static final String POST_CLASS_NAME = "Post";

    public static final String POST_TITLE = "title";
    public static final String POST_FILE = "file";

    public static final Creator CREATOR = AVObjectCreator.instance;

    public Post() {

    }

    public Post(Parcel in) {
        super(in);
    }

    public void setTitle(String title) {
        put(POST_TITLE, title);
    }

    public String getTitle() {
        return getString(POST_TITLE);
    }

    public void setFile(AVFile file) {
        put(POST_FILE, file);
    }

    public AVFile getFile() {
        return getAVFile(POST_FILE);
    }

    @Override
    public String text() {
        return "Title:" + getTitle() + "\n";
    }
}
