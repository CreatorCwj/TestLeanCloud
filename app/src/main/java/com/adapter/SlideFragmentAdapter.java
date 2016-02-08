package com.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.testleancloud.R;

import java.util.List;

/**
 * Created by cwj on 16/2/8.
 * 可滑动的Fragment切换的adapter
 * 可以关闭或打开动画
 * 可以指定首页
 */
public class SlideFragmentAdapter<T extends Fragment> implements RadioGroup.OnCheckedChangeListener {

    private static final int DEFAULT_FIRST_PAGE = 0;//默认首页
    private static final boolean DEFAULT_CAN_SLIDE = true;//默认有动画

    private Context context;
    private FragmentManager fm;
    private List<T> fragments;
    private RadioGroup radioGroup;
    private int contentId;

    private boolean canSlide;//是否有滑动动画
    private int currentPage;//当前页

    public SlideFragmentAdapter(FragmentActivity fa, List<T> fragments, RadioGroup radioGroup, int contentId) {
        this(fa, fragments, radioGroup, contentId, DEFAULT_FIRST_PAGE, DEFAULT_CAN_SLIDE);
    }

    public SlideFragmentAdapter(FragmentActivity fa, List<T> fragments, RadioGroup radioGroup, int contentId, int firstPage) {
        this(fa, fragments, radioGroup, contentId, firstPage, DEFAULT_CAN_SLIDE);
    }

    public SlideFragmentAdapter(FragmentActivity fa, List<T> fragments, RadioGroup radioGroup, int contentId, boolean canSlide) {
        this(fa, fragments, radioGroup, contentId, DEFAULT_FIRST_PAGE, canSlide);
    }

    public SlideFragmentAdapter(FragmentActivity fa, List<T> fragments, RadioGroup radioGroup, int contentId, int firstPage, boolean canSlide) {
        this.context = fa;
        this.fm = fa.getSupportFragmentManager();
        this.fragments = fragments;
        this.radioGroup = radioGroup;
        this.contentId = contentId;
        this.canSlide = canSlide;
        this.currentPage = firstPage;
        setFirstPage();//设置第一页(要在监听器前改变,否则会调用一次listener)
        setListener();//radioGroup监听器
        showFragment(currentPage);//展示第一个fragment
    }

    //展示fragment
    private void showFragment(int newFragment) {
        Fragment fragment = fragments.get(newFragment);
        FragmentTransaction transaction = fm.beginTransaction();
        setFragmentAnimation(newFragment, transaction, true);//设置展示动画
        if (fragment.isAdded()) {//添加过则展示
            fragment.onResume();//展示
            transaction.show(fragment);
        } else {//没添加过则添加
            transaction.add(contentId, fragment);
        }
        transaction.commitAllowingStateLoss();//执行生命周期
    }

    //隐藏当前fragment
    private void hideCurrentFragment(int newFragment) {
        getCurrentFragment().onPause();//暂停
        FragmentTransaction transaction = fm.beginTransaction();
        setFragmentAnimation(newFragment, transaction, false);//设置隐藏动画
        transaction.hide(getCurrentFragment());
        transaction.commitAllowingStateLoss();
    }

    private void setFirstPage() {
        ((RadioButton) radioGroup.getChildAt(currentPage)).setChecked(true);
    }

    private void setListener() {
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i < group.getChildCount(); ++i) {
            RadioButton radioButton = (RadioButton) group.getChildAt(i);
            if (radioButton.getId() == checkedId) {
                hideCurrentFragment(i);//隐藏当前
                showFragment(i);//添加要展示的fragment
                currentPage = i;//更新当前页
                break;
            }
        }
    }

    //设置动画
    private void setFragmentAnimation(int newFragment, FragmentTransaction transaction, boolean isShow) {
        if (!canSlide)//不可用动画
            return;
        if (isShow) {//展示动画
            if (currentPage > newFragment) {
                transaction.setCustomAnimations(R.anim.push_in_from_left, 0);
            } else if (currentPage < newFragment) {
                transaction.setCustomAnimations(R.anim.push_in_from_right, 0);
            }//等于时为第一页,不设置动画
        } else {//隐藏动画
            if (currentPage > newFragment) {
                transaction.setCustomAnimations(0, R.anim.push_out_to_right);
            } else if (currentPage < newFragment) {
                transaction.setCustomAnimations(0, R.anim.push_out_to_left);
            }
        }
    }

    private T getCurrentFragment() {
        return fragments.get(currentPage);
    }
}
