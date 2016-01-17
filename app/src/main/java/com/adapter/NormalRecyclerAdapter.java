package com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.base.BaseAdapter;
import com.imageLoader.ImageLoader;
import com.testleancloud.R;


/**
 * Created by cwj on 15/12/11.
 */
public class NormalRecyclerAdapter extends BaseAdapter<String> {

    private LayoutInflater layoutInflater;

    public NormalRecyclerAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalImageViewHolder(layoutInflater.inflate(R.layout.item_image_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder != null) {
            NormalImageViewHolder normalImageViewHolder = (NormalImageViewHolder) viewHolder;
            ImageView imageView = normalImageViewHolder.imageView;
//            String imgUrl = "http://pic14.nipic.com/20110522/7411759_164157418126_2.jpg";
            String imgUrl = getItem(i);
            ImageLoader.loadImage(imageView, imgUrl);
        }
    }

    class NormalImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public NormalImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
