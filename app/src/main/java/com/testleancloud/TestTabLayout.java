package com.testleancloud;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.adapter.TabFragmentAdapter;
import com.fragment.TabFragment;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_test_tab_layout)
public class TestTabLayout extends RoboFragmentActivity {

    @InjectView(R.id.tabLayout)
    private TabLayout tabLayout;

    @InjectView(R.id.viewPager)
    private ViewPager viewPager;

    private TabFragmentAdapter tabFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewPager();
        initTabLayout();
    }

    private void initViewPager() {
        List<TabFragment> list = new ArrayList<>();
        for (int i = 1; i <= 6; ++i) {
            list.add(TabFragment.newInstance(i));
        }
        tabFragmentAdapter = new TabFragmentAdapter(this, getSupportFragmentManager(), viewPager, list, 2);
    }

    private void initTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

}
