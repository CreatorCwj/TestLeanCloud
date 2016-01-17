package com.base;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cwj on 16/1/16.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> dataList;

    public BaseAdapter() {
        dataList = new ArrayList<>();
    }

    public void addData(List<T> data) {
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        dataList.clear();
        notifyDataSetChanged();
    }

    public void setData(List<T> data) {
        dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        if (position >= 0 && position < dataList.size())
            return dataList.get(position);
        return null;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
