package com.testleancloud;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.base.BaseActivity;
import com.util.UIUtils;
import com.widget.pullToZoomView.PullToZoomScrollViewEx;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_pull_img)
public class PullImgActivity extends BaseActivity {

//    @InjectView(R.id.pullImg)
//    private ImageView pullImg;
//
//    @InjectView(R.id.btn_container)
//    private LinearLayout container;

    @InjectView(R.id.scroll_view)
    private PullToZoomScrollViewEx scrollView;

    @InjectView(R.id.toolbar)
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView zoomView = new ImageView(this);
        zoomView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        zoomView.setImageResource(R.drawable.ic_contact);
        zoomView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout contentView = new LinearLayout(this);
        contentView.setBackgroundColor(Color.GRAY);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        contentView.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < 10; i++) {
            Button btn = new Button(this);
            btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dp2px(this, 80)));
            btn.setText("Btn:" + i);
            contentView.addView(btn);
        }
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
        scrollView.setHeaderViewSize(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dp2px(this, 170));
    }

    private void addView() {
//        for (int i = 0; i < 10; i++) {
//            Button btn = new Button(this);
//            btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dp2px(this, 80)));
//            btn.setText("Btn:" + i);
//            container.addView(btn);
//        }
    }

    @Override
    protected void setListener() {
        scrollView.setOnScrollToTopListener(new PullToZoomScrollViewEx.OnScrollToTopListener() {
            @Override
            public void onScrollToTop(int moved, int total) {
                if (moved < 0)
                    return;
                int realTotal = total - toolbar.getHeight();
                int alpha = (int) (((float) moved / realTotal) * 255);
                if (alpha > 255)
                    alpha = 255;
                if (toolbar.getBackground().getAlpha() != alpha) {
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    toolbar.getBackground().setAlpha(alpha);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
