package com.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.base.BaseModel;

/**
 * Created by cwj on 15/11/30.
 */
@AVClassName("Student")
public class Student extends BaseModel {

    public static final String CLASS_NAME = "Student";

    public static final String STUDENT_NAME = "name";
    public static final String STUDENT_HOMETOWN = "hometown";

    public void setName(String name) {
        put(STUDENT_NAME, name);
    }

    public String getName() {
        return getString(STUDENT_NAME);
    }

    public void setHometown(City city) {
        put(STUDENT_HOMETOWN, city);
    }

    public void setHometown(String cityId) {
        try {
            put(STUDENT_HOMETOWN, AVObject.createWithoutData(City.class, cityId));
        } catch (AVException e) {
            e.printStackTrace();
        }
    }

    public City getHometown() {
        AVObject obj = getAVObject(STUDENT_HOMETOWN);
        if (obj instanceof City) {
            return (City) obj;
        }
        return null;
    }

    @Override
    public String text() {
        return "Name:" + getName() + "\n";
    }
}
