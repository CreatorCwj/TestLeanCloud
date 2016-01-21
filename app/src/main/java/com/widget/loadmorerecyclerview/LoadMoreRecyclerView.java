package com.widget.loadmorerecyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;

import com.imageLoader.ImageLoader;
import com.widget.RLRView;
import com.widget.loadmorerecyclerview.adapter.BaseRecyclerViewAdapter;
import com.widget.loadmorerecyclerview.adapter.RecyclerViewAdapter;

/**
 * Created by cwj on 16/1/17.
 * 可上拉加载更多的RecyclerView
 * 支持刷新不加载,加载不刷新
 * 滑动时图片加载模式
 * 与{@link RLRView}联合使用
 */
public class LoadMoreRecyclerView extends RecyclerView {

    private Boolean canLoadMoreInit = null;
    private boolean canLoadMore = true;
    private boolean isLoading = false;

    private OnLoadListener onLoadListener;
    private BaseRecyclerViewAdapter.OnItemClickListener onItemClickListener;
    private BaseRecyclerViewAdapter.OnItemLongClickListener onItemLongClickListener;

    /**
     * 由RLRView控制的load回调
     */
    public interface OnLoadListener {
        void onLoad();
    }

    public LoadMoreRecyclerView(Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    //初始化增加自定义的滑动监听
    private void init() {
        this.addOnScrollListener(onLoadScrollListener);
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    /**
     * itemClick
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(BaseRecyclerViewAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        RecyclerViewAdapter adapter = (RecyclerViewAdapter) getAdapter();
        if (adapter != null) {
            adapter.setOnItemClickListener(onItemClickListener);
        }
    }

    /**
     * itemLongClick
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(BaseRecyclerViewAdapter.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
        RecyclerViewAdapter adapter = (RecyclerViewAdapter) getAdapter();
        if (adapter != null) {
            adapter.setOnItemLongClickListener(onItemLongClickListener);
        }
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void stopLoadMore() {
        this.isLoading = false;
    }

    public boolean isCanLoadMore() {
        return canLoadMore;
    }

    public Boolean getCanLoadMoreInit() {
        return canLoadMoreInit;
    }

    public void setCanLoadMoreInit(Boolean canLoadMoreInit) {
        this.canLoadMoreInit = canLoadMoreInit;
    }

    /**
     * 是否可以加载更多
     * 默认为加载
     *
     * @param canLoadMore
     */
    public void setCanLoadMore(boolean canLoadMore) {
        //第一次设置要记录是否可以加载的状态,一边后续恢复
        if (canLoadMoreInit == null)
            canLoadMoreInit = canLoadMore;
        //设置完可否加载状态后要告知adapter进行刷新
        this.canLoadMore = canLoadMore;
        notifyAdapterCanLoadMoreOrNot();
    }

    /**
     * 1.一定要是继承RecyclerViewAdapter的adapter
     * 2.初次设置adapter要告知adapter进行刷新以及更新监听器
     * 3.如果是grid的,那么footer要跨所有列
     */
    @Override
    public void setAdapter(final Adapter adapter) {
        if (adapter instanceof RecyclerViewAdapter) {
            //1
            notifyAdapterCanLoadMoreOrNot();
            notifyOnItemClickListener((RecyclerViewAdapter) adapter);
            //2
            if (getLayoutManager() instanceof GridLayoutManager) {
                final GridLayoutManager manager = (GridLayoutManager) getLayoutManager();
                manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (adapter.getItemViewType(position) == RecyclerViewAdapter.FOOTER)
                            return manager.getSpanCount();
                        return 1;
                    }
                });
            }
            super.setAdapter(adapter);
        }
    }

    private void notifyOnItemClickListener(RecyclerViewAdapter adapter) {
        adapter.setOnItemClickListener(onItemClickListener);
        adapter.setOnItemLongClickListener(onItemLongClickListener);
    }

    private OnScrollListener onLoadScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //空闲状态,可以加载更多,没有在加载和刷新的情况下调用加载
            if (newState == RecyclerView.SCROLL_STATE_IDLE && isCanLoadMore() && !isLoading() && !isRefreshing()) {
                handleLoadMore();
            }
            //根据滑动状态来暂停/开始图片加载
            switch (newState) {
                case RecyclerView.SCROLL_STATE_SETTLING://快速滑动时暂停
                    if (ImageLoader.isInitial())
                        ImageLoader.pause();
                    break;
                default:
                    if (ImageLoader.isInitial())
                        ImageLoader.resume();
                    break;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    //父view是否在刷新(父view只能是RLRView)
    private boolean isRefreshing() {
        ViewParent parent = getParent();
        if (parent instanceof RLRView) {
            return ((RLRView) parent).isRefreshing();
        }
        return false;
    }

    //最后一个view显示时加载
    private void handleLoadMore() {
        Adapter adapter = getAdapter();
        if (adapter != null) {
            int count = adapter.getItemCount();
            if (count > 0) {//没数据不让加载
                LayoutManager manager = getLayoutManager();
                View lastView = manager.findViewByPosition(count - 1);
                if (lastView != null && lastView.getTop() > 0) {
                    this.isLoading = true;
                    if (onLoadListener != null)
                        onLoadListener.onLoad();
                }
            }
        }
    }

    //通知adapter更新是否可以加载的状态
    private void notifyAdapterCanLoadMoreOrNot() {
        RecyclerViewAdapter adapter = (RecyclerViewAdapter) getAdapter();
        if (adapter != null)
            adapter.setCanLoadMore(isCanLoadMore());
    }

}
