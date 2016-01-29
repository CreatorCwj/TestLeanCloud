package com.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cwj on 16/1/28.
 */
public class FooterBehavior extends CoordinatorLayout.Behavior<View> {

    public FooterBehavior() {
    }

    public FooterBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        int h = dependency.getHeight();//高度
        int top = -dependency.getTop();//顶部
        float r = (float) top / h;//比例
        float tran = child.getHeight() * r;
        child.setTranslationY(tran);
        return true;//改变了位置或大小时返回true
    }
}
