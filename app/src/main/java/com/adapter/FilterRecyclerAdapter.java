package com.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dao.base.BaseFilterModel;
import com.testleancloud.R;
import com.util.DrawableUtils;
import com.widget.rlrView.adapter.RecyclerViewAdapter;


/**
 * Created by cwj on 15/12/11.
 * 筛选器的adapter
 */
public class FilterRecyclerAdapter<T extends BaseFilterModel> extends RecyclerViewAdapter<T> {

    public FilterRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public void onHolderBind(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder != null) {
            FilterViewHolder filterViewHolder = (FilterViewHolder) viewHolder;
            filterViewHolder.textView.setText(getDataItem(position).getFilterName());
            //设置背景(二级的不变选中的背景,按下时的变)
            if (getDataItem(position).isFirstFilter() || getDataItem(position).isAllFirstFilter()) {//一级
                Drawable drawable = DrawableUtils.getStateDrawable(new DrawableUtils.RectStateDrawable(new int[]{DrawableUtils.STATE_PRESSED}, Color.parseColor("#F2F2F2"))
                        , new DrawableUtils.RectStateDrawable(new int[]{DrawableUtils.STATE_SELECTED}, Color.parseColor("#F2F2F2"))
                        , new DrawableUtils.RectStateDrawable(new int[]{}, Color.TRANSPARENT));
                filterViewHolder.textView.setBackground(drawable);
            } else {//二级
                Drawable drawable = DrawableUtils.getStateDrawable(new DrawableUtils.RectStateDrawable(new int[]{DrawableUtils.STATE_PRESSED}, Color.parseColor("#F2F2F2"))
                        , new DrawableUtils.RectStateDrawable(new int[]{}, Color.TRANSPARENT));
                filterViewHolder.textView.setBackground(drawable);
            }
            //设置文字颜色
            ColorStateList colorStateList = DrawableUtils.getStateColor(new DrawableUtils.StateColor(new int[]{DrawableUtils.STATE_SELECTED}, context.getResources().getColor(R.color.colorPrimary))
                    , new DrawableUtils.StateColor(new int[]{}, context.getResources().getColor(R.color.black)));
            filterViewHolder.textView.setTextColor(colorStateList);
            //是否选中
            filterViewHolder.textView.setSelected(isSelected(position));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new FilterViewHolder(layoutInflater.inflate(R.layout.filter_item, parent, false));
    }

    class FilterViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public FilterViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.filter_name);
        }
    }
}
