package com.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by cwj on 16/1/17.
 * 可上拉加载更多的RecyclerView
 */
public class LoadMoreRecyclerView extends RecyclerView {

    private boolean canLoadMore = true;

    public LoadMoreRecyclerView(Context context) {
        super(context);
        init();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.addOnScrollListener(onLoadScrollListener);
    }

    public boolean isCanLoadMore() {
        return canLoadMore;
    }

    public void setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
    }

    private OnScrollListener onLoadScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };
}
