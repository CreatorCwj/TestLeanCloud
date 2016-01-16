package com.model;

import com.avos.avoscloud.AVClassName;
import com.base.BaseModel;

import java.util.List;

/**
 * Created by cwj on 15/11/30.
 */
@AVClassName("GameScore")
public class GameScore extends BaseModel {

    public static final String CLASS_NAME = "GameScore";

    public static final String SCORE = "score";
    public static final String PLAYER_NAME = "playerName";
    public static final String IS_KP = "isKP";
    public static final String SKILLS = "skills";

    public void setPlayerName(String playerName) {
        put(PLAYER_NAME, playerName);
    }

    public String getPlayerName() {
        return getString(PLAYER_NAME);
    }

    public void setScore(int score) {
        put(SCORE, score);
    }

    public int getScore() {
        return getInt(SCORE);
    }

    public void setIsKp(boolean isKp) {
        put(IS_KP, isKp);
    }

    public boolean getIsKp() {
        return getBoolean(IS_KP);
    }

    public void addSkills(List list) {
        addAll(SKILLS, list);
    }

    public void addSkillsUnique(List list) {
        addAllUnique(SKILLS, list);
    }

    public List<String> getSkills() {
        return getList(SKILLS);
    }

    @Override
    public String text() {
        return "PlayerName:" + getPlayerName() + "\n"
                + "Score:" + getScore() + "\n"
                + "IsKp:" + (getIsKp() ? "是" : "不是") + "\n"
                + getSkills().toString() + "\n";
    }
}
