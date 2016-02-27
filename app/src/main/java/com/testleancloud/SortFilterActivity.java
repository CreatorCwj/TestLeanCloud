package com.testleancloud;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.base.BaseFilterFragment;
import com.base.BaseFragmentActivity;
import com.fragment.AreaFragment;
import com.fragment.CategoryFragment;
import com.model.Area;
import com.model.Category;
import com.util.Utils;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_sort_filter)
public class SortFilterActivity extends BaseFragmentActivity implements View.OnClickListener {

    @InjectView(R.id.area)
    private Button area;

    @InjectView(R.id.category)
    private Button category;

    @InjectView(R.id.sort)
    private Button sort;

    private CategoryFragment categoryFragment;
    private AreaFragment areaFragment;

    private Category cat;
    private Area ar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListener();
        categoryFragment = new CategoryFragment();
        areaFragment = new AreaFragment();
        categoryFragment.setOnSelectListener(new BaseFilterFragment.OnSelectListener<Category>() {
            @Override
            public void onSelect(Category obj) {
                cat = obj;
                Utils.showToast(SortFilterActivity.this, obj.getName());
            }
        });
        areaFragment.setOnSelectListener(new BaseFilterFragment.OnSelectListener<Area>() {
            @Override
            public void onSelect(Area obj) {
                ar = obj;
                Utils.showToast(SortFilterActivity.this, obj.getName());
            }
        });
    }

    private void setListener() {
        area.setOnClickListener(this);
        category.setOnClickListener(this);
        sort.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.area:
                area();
                break;
            case R.id.category:
                category();
                break;
            case R.id.sort:
                break;
        }
    }

    private void area() {
        //关闭其他
        categoryFragment.hideFragment();
        //打开
        if (areaFragment.isAdded()) {
            if (areaFragment.isVisible()) {
                areaFragment.hideFragment();
            } else {
                areaFragment.showFragment();
                areaFragment.initSelect(ar);//每次点开后保留上次选择
            }
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.sort_filter_content, areaFragment).commitAllowingStateLoss();
        }
    }

    private void category() {
        //关闭其他
        areaFragment.hideFragment();
        //打开
        if (categoryFragment.isAdded()) {
            if (categoryFragment.isVisible()) {
                categoryFragment.hideFragment();
            } else {
                categoryFragment.showFragment();
                categoryFragment.initSelect(cat);//每次点开后保留上次选择
            }
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.sort_filter_content, categoryFragment).commitAllowingStateLoss();
        }
    }

}
