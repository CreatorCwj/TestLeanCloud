package com.testleancloud;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.adapter.SlideFragmentAdapter;
import com.fragment.TabFragment;

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
        List<TabFragment> fragments = new ArrayList<>();
        for (int i = 1; i <= 4; ++i) {
            fragments.add(TabFragment.newInstance(i));
        }
        new SlideFragmentAdapter<>(this, fragments, radioGroup, R.id.content_layout);
    }

}