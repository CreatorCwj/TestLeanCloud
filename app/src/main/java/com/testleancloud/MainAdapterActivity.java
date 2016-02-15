package com.testleancloud;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.adapter.SlideFragmentAdapter;
import com.fragment.TabFragment;
import com.util.DrawableUtils;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_main_adapter)
public class MainAdapterActivity extends RoboFragmentActivity {

    @InjectView(R.id.main_radioGroup)
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < radioGroup.getChildCount(); ++i) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            radioButton.setBackground(DrawableUtils.getStateDrawable(
                    new DrawableUtils.RectStateDrawable(new int[]{DrawableUtils.STATE_CHECKED}, Color.GRAY)
                    , new DrawableUtils.RectStateDrawable(new int[]{DrawableUtils.STATE_PRESSED}, Color.YELLOW)
                    , new DrawableUtils.RectStateDrawable(new int[]{}, Color.BLUE)));
            radioButton.setTextColor(DrawableUtils.getStateColor(new DrawableUtils.StateColor(new int[]{DrawableUtils.STATE_CHECKED}, Color.RED)
                    , new DrawableUtils.StateColor(new int[]{}, Color.GREEN)));
        }
        List<TabFragment> fragments = new ArrayList<>();
        for (int i = 1; i <= 4; ++i) {
            fragments.add(TabFragment.newInstance(i));
        }
        new SlideFragmentAdapter<>(this, fragments, radioGroup, R.id.content_layout);
    }

}