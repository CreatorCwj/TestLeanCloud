package com.model;

import com.avos.avoscloud.AVClassName;
import com.base.BaseModel;

/**
 * Created by cwj on 15/11/30.
 */
@AVClassName("City")
public class City extends BaseModel {

    public static final String CLASS_NAME = "City";

    public static final String NAME = "name";
    public static final String CITY_ID = "cityId";
    public static final String PINYIN = "pinyin";
    public static final String SHORT_PINYIN = "shortPinyin";

    public void setName(String name) {
        put(NAME, name);
    }

    public String getName() {
        return getString(NAME);
    }

    public void setCityId(int id) {
        put(CITY_ID, id);
    }

    public int getCityId() {
        return getInt(CITY_ID);
    }

    public void setPinyin(String pinyin) {
        put(PINYIN, pinyin);
    }

    public String getPinyin() {
        return getString(PINYIN);
    }

    public void setShortPinyin(String shortPinyin) {
        put(SHORT_PINYIN, shortPinyin);
    }

    public String getShortPinyin() {
        return getString(SHORT_PINYIN);
    }

    @Override
    public String text() {
        return "Name:" + getName() + "\n";
    }
}
