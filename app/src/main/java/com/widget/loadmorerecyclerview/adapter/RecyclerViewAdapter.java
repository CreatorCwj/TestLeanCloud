package com.widget.loadmorerecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.testleancloud.R;
import com.widget.loadmorerecyclerview.viewholder.LoadViewHolder;

/**
 * Created by cwj on 16/1/17.
 * RecyclerView使用的adapter的基类,可以添加FOOTER
 * 自己处理基本的逻辑,暴露出自定义的接口来实现adapter
 */
public abstract class RecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T> {

    public static final int FOOTER = Integer.MIN_VALUE;

    private boolean canLoadMore = false;//canLoadMore决定是否有footer

    public RecyclerViewAdapter(Context context) {
        super(context);
    }

    /**
     * viewholder已绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) != FOOTER) {
            onHolderBinded(holder, position);
        } else {//footer
            //如果可以加载且有数据则可见,否则不可见
            //todo 待优化:数据不够一个界面时不总是显示
            if (isCanLoadMore() && getDataCount() > 0) {
                holder.itemView.setVisibility(View.VISIBLE);
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
    abstract public void onHolderBinded(RecyclerView.ViewHolder holder, int position);

    /**
     * 得到item总数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return dataList.size() + (isCanLoadMore() ? 1 : 0);
    }

    /**
     * item类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

    /**
     * canLoadMore决定是否有footer
     *
     * @return
     */
    public boolean isCanLoadMore() {
        return canLoadMore;
    }

    public void setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
        notifyDataSetChanged();
    }

}
