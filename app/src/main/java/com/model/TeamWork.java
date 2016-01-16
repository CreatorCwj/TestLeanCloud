package com.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.base.BaseModel;

/**
 * Created by cwj on 15/11/30.
 */
@AVClassName("TeamWork")
public class TeamWork extends BaseModel {

    public static final String CLASS_NAME = "TeamWork";

    public static final String TEAMWORK_NAME = "name";
    public static final String TEAMWORK_SCORE = "score";
    public static final String TEAMWORK_CITY = "city";

    public void setName(String name) {
        put(TEAMWORK_NAME, name);
    }

    public String getName() {
        return getString(TEAMWORK_NAME);
    }

    public void setScore(int score) {
        put(TEAMWORK_SCORE, score);
    }

    public int getScore() {
        return getInt(TEAMWORK_SCORE);
    }

    public void setCity(City city) {
        put(TEAMWORK_CITY, city);
    }

    public void setCity(String cityId) {
        try {
            put(TEAMWORK_CITY, AVObject.createWithoutData(City.class, cityId));
        } catch (AVException e) {
            e.printStackTrace();
        }
    }

    public City getCity() {
        AVObject obj = getAVObject(TEAMWORK_CITY);
        if (obj instanceof City) {
            return (City) obj;
        }
        return null;
    }

    @Override
    public String text() {
        return "Name:" + getName() + "\n"
                + "Score:" + getScore() + "\n";
    }
}
