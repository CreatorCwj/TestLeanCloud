package com.testleancloud;

import android.os.Bundle;
import android.view.View;

import com.adapter.MyAdapter;
import com.base.BaseActivity;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

@ContentView(R.layout.activity_sticky)
public class StickyActivity extends BaseActivity {

    @InjectView(R.id.list)
    private StickyListHeadersListView stickyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyAdapter adapter = new MyAdapter(this);
        stickyList.setAdapter(adapter);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
