package com.testleancloud;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.base.BaseActivity;
import com.widget.rlrView.view.RLRView;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_collapsing)
public class CollapsingActivity extends BaseActivity implements RLRView.OnRefreshListener, RLRView.OnLoadListener {

//    @InjectView(R.id.rlrView)
//    private LoadMoreRecyclerView rlrView;

    @InjectView(R.id.collapsing_toolbar_layout)
    private CollapsingToolbarLayout collapsing_toolbar_layout;

    @InjectView(R.id.toolbar)
    private Toolbar toolbar;

//    private NormalRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCollapsing();
//        adapter = new NormalRecyclerAdapter(this);
//        rlrView.setAdapter(adapter);
//        rlrView.addData(MockData.getImageUrls(0, 15));
    }

    private void initCollapsing() {
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.getMenu().add("action").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        collapsing_toolbar_layout.setTitle("I am Title");
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.WHITE);
        collapsing_toolbar_layout.setExpandedTitleColor(Color.BLACK);
    }

    @Override
    public void onRefresh() {
//        adapter.clearData();
//        obtainData();
    }

    @Override
    public void onLoad() {
//        obtainData();
    }

    /*private void obtainData() {
        final Map<String, String> params = new HashMap<>();
        params.put("keyword", "足球");
        params.put("num", "11");
        new Network<>(this, GirlImage.class)
                .setPathUrl(getResources().getString(R.string.get_images))
                .setMethod(Request.Method.GET)
                .setRequestParams(params)
                .setTag(this)
                .setRequestCallback(new RequestCallback<GirlImage>() {
                    @Override
                    public void onRequestSuccess(GirlImage result) {
                        if (result != null) {
                            List<String> urls;
                            urls = MockData.getImageUrls(rlrView.getSkipCount(), rlrView.getPageSize());
                            rlrView.addData(urls);
                        }
                    }

                    @Override
                    public void onRequestError(String errorMessage) {
                        Utils.showToast(CollapsingActivity.this, errorMessage);
                        rlrView.rlError();
                    }

                    @Override
                    public void onRequestFinally() {
                        rlrView.stopRL();
                    }
                }).execute();
    }*/

    @Override
    protected void setListener() {
        textView.setOnClickListener(this);
//        rlrView.setOnRefreshListener(this);
//        rlrView.setOnLoadListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView:
//                Utils.showSnack(rlrView, "Success!");
                break;
        }
    }
}
