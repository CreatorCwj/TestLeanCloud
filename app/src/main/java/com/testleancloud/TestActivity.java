package com.testleancloud;

import android.os.Bundle;
import android.view.View;

import com.adapter.PlaceRecyclerAdapter;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.base.BaseActivity;
import com.leancloud.SafeFindCallback;
import com.model.Place;
import com.widget.rlrView.view.RLRView;

import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_test)
public class TestActivity extends BaseActivity implements RLRView.OnRefreshListener, RLRView.OnLoadListener {

    @InjectView(R.id.rlrView)
    private RLRView rlrView;

    private PlaceRecyclerAdapter adapter;

    private SafeFindCallback<Place> safeFindCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PlaceRecyclerAdapter(this);
        rlrView.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        rlrView.setOnRefreshListener(this);
        rlrView.setOnLoadListener(this);
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView:
                rlrView.refresh();
                break;
        }
    }

    @Override
    public void onLoad() {
        obtainData(false);
    }

    @Override
    public void onRefresh() {
        rlrView.clearData();
        obtainData(true);
    }

    private void obtainData(boolean isRefresh) {
        handleCallback();
        AVQuery.getQuery(Place.class).setSkip(rlrView.getSkipCount()).limit(rlrView.getPageSize())
                .findInBackground(safeFindCallback);
        if (!isRefresh) {//load more
            rlrView.refresh();
        }
    }

    //取消当前正在进行的请求
    private void handleCallback() {
        if (safeFindCallback != null)
            safeFindCallback.cancel();
        safeFindCallback = new SafeFindCallback<Place>(this) {
            @Override
            public void findResult(List<Place> objects, AVException e) {
                if (e == null) {
                    rlrView.addData(objects);
                } else {
                    rlrView.rlError();
                }
                rlrView.stopRL();
            }
        };
    }
}
