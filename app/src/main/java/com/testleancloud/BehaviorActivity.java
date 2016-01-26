package com.testleancloud;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.base.BaseActivity;
import com.util.Utils;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_behavior)
public class BehaviorActivity extends BaseActivity {

    @InjectView(R.id.fab)
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Utils.showSnack(v, "I am snack!");
                break;
        }
    }
}
