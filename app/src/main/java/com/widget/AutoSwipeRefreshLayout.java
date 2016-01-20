package com.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.util.UIUtils;
import com.widget.loadmorerecyclerview.LoadMoreRecyclerView;

import java.lang.reflect.Field;

/**
 * Created by cwj on 16/1/16.
 * 可设置自动刷新与否以及外部手动刷新的刷新控件
 * 支持刷新不加载,加载不刷新
 * 与{@link LoadMoreRecyclerView}联合使用
 */
public class AutoSwipeRefreshLayout extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener {

    private boolean autoRefresh = true;
    private OnRefreshListener onRefreshListener;

    /**
     * 刷新请实现该接口
     */
    public interface OnRefreshListener {
        void onRefresh();
    }

    public AutoSwipeRefreshLayout(Context context) {
        super(context);
        init();
    }

    public AutoSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //一定要设置offset,否则调用setRefreshing(true)时没有效果
        this.setProgressViewOffset(false, -UIUtils.dp2px(getContext(), 24), UIUtils.dp2px(getContext(), 24));
        this.setOnRefreshListener(this);
        //onResume回调后view会回调此方法来监听layout的改变
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //可以自动刷新且没有在加载的时候调用刷新
                if (isAutoRefresh() && !isLoading()) {
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
            if (obj != null && obj instanceof SwipeRefreshLayout.OnRefreshListener) {
                setRefreshing(true);
                ((SwipeRefreshLayout.OnRefreshListener) obj).onRefresh();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //判断子view是否处于加载状态,子view只能是LoadMoreRecyclerView
    private boolean isLoading() {
        LoadMoreRecyclerView child = getRecyclerView();
        if (child != null) {
            return child.isLoading();
        }
        return false;
    }

    private LoadMoreRecyclerView getRecyclerView() {
        for (int index = 0; index < getChildCount(); ++index) {
            View v = getChildAt(index);
            if (v instanceof LoadMoreRecyclerView) {
                return ((LoadMoreRecyclerView) v);
            }
        }
        return null;
    }

    /**
     * 外部主动调用
     */
    public void refresh() {
        //刷新或加载时不允许刷新
        if (isRefreshing() || isLoading())
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
     * 默认为允许自动刷新
     *
     * @param autoRefresh
     */
    public void setAutoRefresh(boolean autoRefresh) {
        this.autoRefresh = autoRefresh;
    }

    /**
     * 设置自定义刷新监听器
     *
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.onRefreshListener = listener;
    }

    /**
     * 自带listener只能是自己,需要传入自定义的listener来监听刷新
     *
     * @param listener
     */
    @Override
    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        super.setOnRefreshListener(this);
    }

    @Override
    final public void onRefresh() {
        //swipe手势刷新时会调用此方法将refresh状态设置为true,如果此时在加载,不可以刷新,而且要讲刷新状态设置为false
        if (isLoading()) {
            setRefreshing(false);
            return;
        }
        //否则可以调用刷新,记住要恢复加载状态
        resetCanLoadState();
        if (onRefreshListener != null) {
            onRefreshListener.onRefresh();
        }
    }

    private void resetCanLoadState() {
        LoadMoreRecyclerView view = getRecyclerView();
        if (view != null) {
            Boolean b = view.getCanLoadMoreInit();
            if (b == null || b) {
                view.setCanLoadMore(true);
            } else {
                view.setCanLoadMore(false);
            }
        }
    }
}
