package com.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.base.BaseFragmentPagerAdapter;
import com.fragment.TabFragment;

import java.util.List;

/**
 * Created by cwj on 16/1/28.
 */
public class TabFragmentAdapter extends BaseFragmentPagerAdapter<TabFragment> {


    public TabFragmentAdapter(FragmentActivity fa, ViewPager viewPager, List<TabFragment> fragments) {
        super(fa, viewPager, fragments);
    }

    public TabFragmentAdapter(FragmentActivity fa, ViewPager viewPager, List<TabFragment> fragments, int firstPage) {
        super(fa, viewPager, fragments, firstPage);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "TAB" + position;
    }

}
