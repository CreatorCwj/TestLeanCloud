package com.testleancloud;

import android.os.Bundle;
import android.view.View;

import com.adapter.NormalRecyclerAdapter;
import com.android.volley.Request;
import com.base.BaseActivity;
import com.model.GirlImage;
import com.util.MockData;
import com.util.Utils;
import com.volley.Network;
import com.volley.listener.RequestCallback;
import com.widget.RLRView;
import com.widget.loadmorerecyclerview.LoadMoreRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_image_loader)
public class ImageLoaderActivity extends BaseActivity implements RLRView.OnRefreshListener, RLRView.OnLoadListener, LoadMoreRecyclerView.OnItemClickListener, LoadMoreRecyclerView.OnItemLongClickListener {

    @InjectView(R.id.rlrView)
    private RLRView rlrView;

    private NormalRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRefreshLayout();
        initRecycleView();
    }

    private void initRecycleView() {
        adapter = new NormalRecyclerAdapter(this);
        rlrView.setAdapter(adapter);
//        rlrView.setCanLoadMore(true);
    }

    private void initRefreshLayout() {
        rlrView.setColorSchemeResources(
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
//        rlrView.setAutoRefresh(true);
    }

    @Override
    public void onRefresh() {
        adapter.clearData();
        obtainData();
    }

    @Override
    public void onLoad() {
        obtainData();
    }

    private void obtainData() {
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
                        Utils.showToast(ImageLoaderActivity.this, errorMessage);
                        rlrView.rlError();
                    }

                    @Override
                    public void onRequestFinally() {
                        rlrView.stopRL();
                    }
                }).execute();
    }

    @Override
    protected void setListener() {
        rlrView.setOnRefreshListener(this);
        rlrView.setOnLoadListener(this);
        rlrView.setOnItemClickListener(this);
        rlrView.setOnItemLongClickListener(this);
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView:
                rlrView.backToTop();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        Utils.showToast(this, "short:" + position + ":" + adapter.getDataItem(position));
    }

    @Override
    public void onItemLongClick(int position) {
        Utils.showToast(this, "long:" + position + ":" + adapter.getDataItem(position));
    }
}
