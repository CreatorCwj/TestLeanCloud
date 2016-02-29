package com.fragment;

import com.base.BaseFilterFragment;
import com.model.Category;
import com.util.MockData;

import java.util.List;

public class CategoryFragment extends BaseFilterFragment<Category> {

    @Override
    protected List<Category> getSubFilters(int parentFilterId) {
        return MockData.getSubCategory(parentFilterId);
    }

    @Override
    protected List<Category> getFirstFilters() {
        return MockData.getCategory();
    }

}
