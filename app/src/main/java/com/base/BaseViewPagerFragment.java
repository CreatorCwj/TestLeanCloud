package com.base;

/**
 * Created by cwj on 16/1/28.
 * {@link BaseFragmentPagerAdapter}
 */
public abstract class BaseViewPagerFragment extends BaseFragment {

    /**
     * 使用onViewPagerFragmentResume替代更准确
     */
    @Override
    final public void onResume() {
        super.onResume();
    }

    /**
     * viewPager里的fragment展现时调用（可以完全替代onResume）
     */
    public void onViewPagerFragmentResume() {

    }

    /**
     * viewPager里的fragment切走时调用
     */
    public void onViewPagerFragmentPause() {

    }
}
