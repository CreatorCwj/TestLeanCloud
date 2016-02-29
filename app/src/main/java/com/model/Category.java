package com.model;

import com.dao.base.BaseFilterModel;

/**
 * Created by cwj on 16/2/28.
 * 模拟的品类的model
 */
public class Category extends BaseFilterModel<Category> {

    private int id;
    private String name;
    private int parentId;

    public Category() {

    }

    public Category(int id, String name, int parentId) {
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
    public Category getAllFirstFilter() {
        return new Category(ALL_FILTER_ID, ALL_FILTER_NAME, INVALID_PARENT_ID);
    }

    @Override
    public Category getAllSubFilter(int parentFilterId) {
        return new Category(ALL_FILTER_ID, ALL_FILTER_NAME, parentFilterId);
    }

}
