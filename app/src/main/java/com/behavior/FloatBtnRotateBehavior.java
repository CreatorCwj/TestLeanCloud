package com.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by cwj on 16/1/28.
 */
public class FloatBtnRotateBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {

    public FloatBtnRotateBehavior() {

    }

    public FloatBtnRotateBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //决定依赖项
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    //依赖项变化时
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        float translationY = getFabTranslationYForSnackbar(parent, child);
        float percentComplete = -translationY / dependency.getHeight();//移动的百分比
        child.setRotation(-90 * percentComplete);//旋转
        child.setTranslationY(translationY);//平移
        return false;
    }

    private float getFabTranslationYForSnackbar(CoordinatorLayout parent, FloatingActionButton fab) {
        float minOffset = 0;
        final List<View> dependencies = parent.getDependencies(fab);//拿到fab所依赖的view(由layoutDependsOn决定)
        for (int i = 0, z = dependencies.size(); i < z; i++) {
            final View view = dependencies.get(i);
            //doViewsOverlap:两个view是否有重叠
            if (view instanceof Snackbar.SnackbarLayout && parent.doViewsOverlap(fab, view)) {
                float translationY = ViewCompat.getTranslationY(view);//拿到y轴当前剩余移动距离
                int height = view.getHeight();//总高度
                float cha = translationY - height;//已移动的距离的负值
                minOffset = Math.min(minOffset, cha);
            }
        }
        return minOffset;
    }
}
