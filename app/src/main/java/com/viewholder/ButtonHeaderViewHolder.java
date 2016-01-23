package com.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.testleancloud.R;
import com.util.Utils;
import com.widget.loadmorerecyclerview.viewholder.HeaderViewHolder;

/**
 * Created by cwj on 16/1/23.
 */
public class ButtonHeaderViewHolder extends HeaderViewHolder {

    private Button button;

    public ButtonHeaderViewHolder(final Context context, int layoutId) {
        super(context, layoutId);
        button = (Button) itemView.findViewById(R.id.headerButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showToast(context, "I am header Button");
            }
        });
    }

}
