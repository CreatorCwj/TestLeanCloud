package com.testleancloud;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.util.DrawableUtils;
import com.util.UIUtils;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_drawable)
public class DrawableActivity extends RoboActivity {

    @InjectView(R.id.content_container)
    private LinearLayout contentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addButton(DrawableUtils.getDrawable(Color.RED));
        addButton(DrawableUtils.getDrawable(20, Color.RED, 5, Color.GRAY));

        addButton(DrawableUtils.getStateDrawable(new DrawableUtils.RectStateDrawable(new int[]{DrawableUtils.STATE_PRESSED}, Color.RED)
                , new DrawableUtils.RectStateDrawable(new int[]{DrawableUtils.STATE_UNPRESSED}, Color.BLUE)));
        addButton(DrawableUtils.getStateDrawable(new DrawableUtils.CornerStateDrawable(new int[]{DrawableUtils.STATE_PRESSED}, 20, Color.RED, 5, Color.GRAY)
                , new DrawableUtils.CornerStateDrawable(new int[]{DrawableUtils.STATE_UNPRESSED}, 20, Color.BLUE, 5, Color.GRAY)));

        addButton(DrawableUtils.getLayerDrawable(Color.RED, 6, 6, 6, 6, Color.GRAY));
        addButton(DrawableUtils.getLayerDrawable(20, Color.RED, 6, 6, 6, 6, Color.GRAY));

        addButton(DrawableUtils.getStateDrawable(new DrawableUtils.LayerStateDrawable(new int[]{DrawableUtils.STATE_PRESSED}, Color.RED, 6, 6, 6, 6, Color.GRAY)
                , new DrawableUtils.LayerStateDrawable(new int[]{DrawableUtils.STATE_UNPRESSED}, Color.BLUE, 6, 6, 6, 6, Color.GRAY)));
        addButton(DrawableUtils.getStateDrawable(new DrawableUtils.LayerStateDrawable(new int[]{DrawableUtils.STATE_PRESSED}, 20, 0, 0, 20, Color.RED, 6, 6, 0, 6, Color.GRAY)
                , new DrawableUtils.LayerStateDrawable(new int[]{DrawableUtils.STATE_UNPRESSED}, 20, 0, 0, 20, Color.BLUE, 6, 6, 0, 6, Color.GRAY)));
    }

    private void addButton(Drawable drawable) {
        Button button = new Button(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = UIUtils.dp2px(this, 10);
        button.setLayoutParams(params);
        button.setText("Test");
        button.setBackground(drawable);
        contentContainer.addView(button);
    }

}
