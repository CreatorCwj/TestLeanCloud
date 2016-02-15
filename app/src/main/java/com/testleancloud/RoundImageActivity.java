package com.testleancloud;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.base.BaseActivity;
import com.util.UIUtils;
import com.widget.RoundImageView;

import roboguice.inject.ContentView;

@ContentView(R.layout.activity_round_image)
public class RoundImageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout view = (LinearLayout) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        RoundImageView imageView = new RoundImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(UIUtils.dp2px(this, 80), UIUtils.dp2px(this, 80)));
        imageView.setImageType(RoundImageView.ROUND);
        imageView.setRadius(UIUtils.dp2px(this, 5));
        imageView.setStrokeWidth(UIUtils.dp2px(this, 5));
        imageView.setStrokeColor(Color.BLACK);
        imageView.setImageResource(R.drawable.ic_contact);
        view.addView(imageView);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
