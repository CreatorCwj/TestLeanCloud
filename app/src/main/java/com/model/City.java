package com.model;

import com.avos.avoscloud.AVClassName;
import com.base.BaseModel;

/**
 * Created by cwj on 15/11/30.
 */
@AVClassName("City")
public class City extends BaseModel {

    public static final String CLASS_NAME = "City";

    public static final String CITY_NAME = "name";

    public void setName(String name) {
        put(CITY_NAME, name);
    }

    public String getName() {
        return getString(CITY_NAME);
    }

    @Override
    public String text() {
        return "Name:" + getName() + "\n";
    }
}
