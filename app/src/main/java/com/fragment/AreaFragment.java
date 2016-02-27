package com.fragment;

import com.base.BaseFilterFragment;
import com.model.Area;
import com.util.MockData;

import java.util.List;

/**
 * Created by cwj on 16/2/29.
 */
public class AreaFragment extends BaseFilterFragment<Area> {

    @Override
    protected List<Area> getSubFilters(int parentFilterId) {
        return MockData.getSubArea(parentFilterId);
    }

    @Override
    protected List<Area> getFirstFilters() {
        return MockData.getArea();
    }
}
