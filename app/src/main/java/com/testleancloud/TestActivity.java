package com.testleancloud;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListView;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_test)
public class TestActivity extends RoboActivity {

    @InjectView(R.id.lv)
    ListView listView;

    @InjectView(R.id.swipe)
    SwipeRefreshLayout swipe;

    int width = 0;
    int height = 0;

    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = LayoutInflater.from(this).inflate(R.layout.empty, null);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                v.setDrawingCacheEnabled(true);
                v.destroyDrawingCache();
                v.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
                v.layout(0, 0, width, height);
                v.buildDrawingCache();
                Bitmap bitmap = v.getDrawingCache();
                final BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
                swipe.setBackground(bd);
                swipe.setRefreshing(false);
            }
        });

        swipe.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width = swipe.getWidth();
                height = swipe.getHeight();
                swipe.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

}
