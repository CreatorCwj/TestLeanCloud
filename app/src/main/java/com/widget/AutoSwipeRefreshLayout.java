package com.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import java.lang.reflect.Field;

/**
 * Created by cwj on 16/1/16.
 * 可设置自动刷新与否以及主动刷新的刷新控件
 */
public class AutoSwipeRefreshLayout extends SwipeRefreshLayout {

    private boolean autoRefresh = true;

    public AutoSwipeRefreshLayout(Context context) {
        super(context);
        init();
    }

    public AutoSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //onResume回调后view会回调此方法来监听layout的改变
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (isAutoRefresh()) {
                    invokeRefresh();
                }
                //防止后续调用,调用一次后即可取消
                AutoSwipeRefreshLayout.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    //通过反射拿到listener
    private void invokeRefresh() {
        try {
            Field field = SwipeRefreshLayout.class.getDeclaredField("mListener");
            field.setAccessible(true);
            Object obj = field.get(this);
            if (obj != null && obj instanceof OnRefreshListener) {
                setRefreshing(true);
                ((OnRefreshListener) obj).onRefresh();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 外部主动调用
     */
    public void refresh() {
        if (isRefreshing())
            return;
        invokeRefresh();
    }

    /**
     * 是否自动刷新
     *
     * @return
     */
    public boolean isAutoRefresh() {
        return autoRefresh;
    }

    /**
     * 设置是否允许第一次自动刷新
     *
     * @param autoRefresh
     */
    public void setAutoRefresh(boolean autoRefresh) {
        this.autoRefresh = autoRefresh;
    }
}
