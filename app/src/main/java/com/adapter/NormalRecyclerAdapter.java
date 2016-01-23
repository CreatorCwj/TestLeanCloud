package com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.imageLoader.ImageLoader;
import com.testleancloud.R;
import com.widget.loadmorerecyclerview.adapter.RecyclerViewAdapter;


/**
 * Created by cwj on 15/12/11.
 */
public class NormalRecyclerAdapter extends RecyclerViewAdapter<String> {

    public NormalRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public void onHolderBind(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder != null) {
            NormalImageViewHolder normalImageViewHolder = (NormalImageViewHolder) viewHolder;
            ImageView imageView = normalImageViewHolder.imageView;
//            String imgUrl = "http://pic14.nipic.com/20110522/7411759_164157418126_2.jpg";
            String imgUrl = getDataItem(position);
            ImageLoader.loadImage(imageView, imgUrl);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new NormalImageViewHolder(layoutInflater.inflate(R.layout.item_image_view, parent, false));
    }

    class NormalImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public NormalImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
