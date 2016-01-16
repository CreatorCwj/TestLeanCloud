package com.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVGeoPoint;
import com.base.BaseModel;

/**
 * Created by cwj on 15/12/6.
 */
@AVClassName("Place")
public class Place extends BaseModel {
    public static final String CLASS_NAME = "Place";

    public static final String PLACE_NAME = "name";
    public static final String PLACE_LOCATION = "location";

    public void setName(String name) {
        put(PLACE_NAME, name);
    }

    public String getName() {
        return getString(PLACE_NAME);
    }

    public void setLocation(AVGeoPoint location) {
        put(PLACE_LOCATION, location);
    }

    public AVGeoPoint getLocation() {
        return getAVGeoPoint(PLACE_LOCATION);
    }

    @Override
    public String text() {
        return "Name:" + getName() + "\n";
    }
}
