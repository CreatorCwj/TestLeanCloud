package com.widget.loadmorerecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.testleancloud.R;
import com.widget.loadmorerecyclerview.LoadMoreRecyclerView;
import com.widget.loadmorerecyclerview.viewholder.LoadViewHolder;

/**
 * Created by cwj on 16/1/17.
 * 刷新加载界面用的adapter基类
 * RecyclerView使用的adapter的基类,可以添加FOOTER
 * 自己处理基本的逻辑,暴露出自定义的接口来实现adapter
 */
public abstract class RecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T> {

    private LoadMoreRecyclerView loadMoreView;

    public static final int FOOTER = Integer.MIN_VALUE;

    public RecyclerViewAdapter(Context context) {
        super(context);
    }

    /**
     * 依赖的LoadMoreRecyclerView
     *
     * @param loadMoreView
     */
    public void attachLoadMoreView(LoadMoreRecyclerView loadMoreView) {
        this.loadMoreView = loadMoreView;
    }

    /**
     * viewholder已绑定
     *
     * @param holder
     * @param position
     */
    @Override
    final public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) != FOOTER) {
            //点击事件
            View v = holder.itemView;
            if (v != null) {
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (loadMoreView != null)
                            loadMoreView.performItemClick(position);
                    }
                });
                v.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (loadMoreView != null)
                            loadMoreView.performItemLongClick(position);
                        return true;
                    }
                });
            }
            //自己的逻辑
            onHolderBind(holder, position);
        } else {//footer
            //如果可以加载且有数据则可见,否则不可见
            if (loadMoreView != null && loadMoreView.isCanLoadMore() && getDataCount() > 0) {
                holder.itemView.setVisibility(View.VISIBLE);
                //加载中则显示圈,否则显示文字
                if (holder instanceof LoadViewHolder) {
                    if (loadMoreView.isLoading()) {
                        ((LoadViewHolder) holder).setLoadState(true);
                    } else {
                        ((LoadViewHolder) holder).setLoadState(false);
                    }
                }
            } else {
                holder.itemView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 处理后的viewholder已绑定
     *
     * @param holder
     * @param position
     */
    abstract public void onHolderBind(RecyclerView.ViewHolder holder, int position);

    /**
     * 得到item总数
     *
     * @return
     */
    @Override
    final public int getItemCount() {
        return dataList.size() + ((loadMoreView != null && loadMoreView.isCanLoadMore()) ? 1 : 0);
    }

    /**
     * item类型
     *
     * @param position
     * @return
     */
    @Override
    final public int getItemViewType(int position) {
        if (position >= dataList.size()) {
            return FOOTER;
        } else {
            return getItemType(position);
        }
    }

    /**
     * 处理后的item类型
     *
     * @param position
     * @return
     */
    public int getItemType(int position) {
        return 0;
    }

    /**
     * 根据类型创建viewholder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    final public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER) {
            return new LoadViewHolder(layoutInflater.inflate(R.layout.load_view, parent, false));
        }
        return onCreateHolder(parent, viewType);
    }

    /**
     * 根据类型创建处理后的viewholder
     *
     * @param parent
     * @param viewType
     * @return
     */
    abstract public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType);

}
