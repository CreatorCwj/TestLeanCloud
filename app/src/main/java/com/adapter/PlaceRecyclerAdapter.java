package com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.model.Place;
import com.testleancloud.R;
import com.widget.rlrView.adapter.RecyclerViewAdapter;


/**
 * Created by cwj on 15/12/11.
 */
public class PlaceRecyclerAdapter extends RecyclerViewAdapter<Place> {

    public PlaceRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public void onHolderBind(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder != null) {
            PlaceViewHolder placeViewHolder = (PlaceViewHolder) viewHolder;
            placeViewHolder.textView.setText(getDataItem(position).getName());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new PlaceViewHolder(layoutInflater.inflate(R.layout.place_item, parent, false));
    }

    class PlaceViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.placeName);
        }
    }
}
