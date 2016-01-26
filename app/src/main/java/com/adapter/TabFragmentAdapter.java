package com.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.base.BaseFragmentPagerAdapter;
import com.fragment.TabFragment;

import java.util.List;

/**
 * Created by cwj on 16/1/28.
 */
public class TabFragmentAdapter extends BaseFragmentPagerAdapter<TabFragment> {

    public TabFragmentAdapter(Context context, FragmentManager fm, ViewPager viewPager, List<TabFragment> fragments) {
        super(context, fm, viewPager, fragments);
    }

    public TabFragmentAdapter(Context context, FragmentManager fm, ViewPager viewPager, List<TabFragment> fragments, int firstPage) {
        super(context, fm, viewPager, fragments, firstPage);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "TAB" + position;
    }

}
