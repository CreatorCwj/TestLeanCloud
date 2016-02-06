package com.model;

/**
 * Created by cwj on 16/2/6.
 */
public class CitySiftModel {

    private int id;
    private String name;
    private String pinyin;

    public CitySiftModel() {

    }

    public CitySiftModel(int id, String name, String pinyin) {
        this.id = id;
        this.name = name;
        this.pinyin = pinyin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
