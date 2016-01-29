package com.behavior;

import android.animation.Animator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by cwj on 16/1/29.
 * Behavior监听内部view滚动的生命周期
 */
public class FooterScrollBehavior extends CoordinatorLayout.Behavior<View> {

    private static final int NONE = 0;//静止
    private static final int DOWN = 1;//向下
    private static final int UP = -1;//向上

    private final Interpolator interpolator = new FastOutSlowInInterpolator();

    private int scrollDirection = NONE;

    public FooterScrollBehavior() {
    }

    public FooterScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void showChild(final View child) {
        //translationY:相对于应有位置的位移
        //translationYBy:相对于现在位置的位移
        child.animate().translationY(0).setInterpolator(interpolator).setDuration(200)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        hideChild(child);
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
    }

    private void hideChild(final View child) {
        child.animate().translationY(child.getHeight()).setInterpolator(interpolator).setDuration(200)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        showChild(child);
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
    }

    private void cancelAnimate(View child) {
        child.animate().cancel();//清除所有动画属性参数
    }

    private int getScrollDirection(int dy) {
        if (dy > 0)
            return DOWN;
        if (dy < 0)
            return UP;
        return NONE;
    }

    //刚开始滚动时,返回true说明要捕获滚动来做出相应事件,进行后续方法调用,否则下面方法都不调用
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;//垂直滚动
    }

    //接受捕获时调用
    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    //接受后每次(一次滚动分为不同次移动,每次移动前调用)开始滚动前调用
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        //新方向
        int newDirection = getScrollDirection(dy);
        if (scrollDirection == NONE || scrollDirection != newDirection) {
            cancelAnimate(child);
            scrollDirection = newDirection;
            if (scrollDirection == DOWN) {
                hideChild(child);
            } else if (scrollDirection == UP) {
                showChild(child);
            }
        }
    }

    //每次滚动中(参考onNestedPreScroll)
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    //在释放滚动进入自由滑动前,返回true说明消耗了fling事件,将不会fling和调用onNestedFling方法,每次fling调用一次
    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    //自由滑动时
    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    //停止滚动或自由滑动时
    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        scrollDirection = NONE;//重置
    }

}
