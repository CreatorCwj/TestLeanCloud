package com.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.base.BaseFragmentPagerAdapter;
import com.fragment.BigBitmapFragment;

import java.util.List;

/**
 * Created by cwj on 16/1/30.
 */
public class BigBitmapFragmentAdapter extends BaseFragmentPagerAdapter<BigBitmapFragment> {

    public BigBitmapFragmentAdapter(Context context, FragmentManager fm, ViewPager viewPager, List<BigBitmapFragment> fragments) {
        super(context, fm, viewPager, fragments);
    }

    public BigBitmapFragmentAdapter(Context context, FragmentManager fm, ViewPager viewPager, List<BigBitmapFragment> fragments, int firstPage) {
        super(context, fm, viewPager, fragments, firstPage);
    }
}
