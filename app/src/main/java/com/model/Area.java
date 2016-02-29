package com.model;

import com.dao.base.BaseFilterModel;

/**
 * Created by cwj on 16/2/29.
 */
public class Area extends BaseFilterModel<Area> {

    private int id;
    private String name;
    private int parentId;

    public Area() {

    }

    public Area(int id, String name, int parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public int getFilterId() {
        return id;
    }

    @Override
    public int getFilterParentId() {
        return parentId;
    }

    @Override
    public String getFilterName() {
        return name;
    }

    @Override
    public Area getAllFirstFilter() {
        return new Area(ALL_FILTER_ID, ALL_FILTER_NAME, INVALID_PARENT_ID);
    }

    @Override
    public Area getAllSubFilter(int parentFilterId) {
        return new Area(ALL_FILTER_ID, ALL_FILTER_NAME, parentFilterId);
    }
}
