package com.testleancloud;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.adapter.NormalRecyclerAdapter;
import com.android.volley.Request;
import com.base.BaseActivity;
import com.model.GirlImage;
import com.util.UIUtils;
import com.util.Utils;
import com.volley.Network;
import com.volley.listener.RequestCallback;
import com.widget.AutoSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_image_loader)
public class ImageLoaderActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.refreshLayout)
    private AutoSwipeRefreshLayout refreshLayout;

    @InjectView(R.id.recyclerView)
    private RecyclerView recyclerView;

    private NormalRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRefreshLayout();
        initRecycleView();
    }

    private void initRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//线性
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));//gridView
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//瀑布流
        adapter = new NormalRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //一定要设置offset,否则调用setRefreshing(true)时没有效果
        refreshLayout.setProgressViewOffset(false, -UIUtils.dp2px(this, 24), UIUtils.dp2px(this, 24));
        refreshLayout.setAutoRefresh(true);
    }

    @Override
    public void onRefresh() {
        adapter.clearData();
        obtainData();
    }

    private void obtainData() {
        Map<String, String> params = new HashMap<>();
        params.put("keyword", "美女");
        new Network<>(this, GirlImage.class)
                .setPathUrl(getResources().getString(R.string.get_images))
                .setMethod(Request.Method.GET)
                .setRequestParams(params)
                .setTag(this)
                .setRequestCallback(new RequestCallback<GirlImage>() {
                    @Override
                    public void onRequestSuccess(GirlImage result) {
                        if (result != null) {
                            List<String> urls = new ArrayList<>();
                            for (List<String> item : result.getImage()) {
                                urls.add(item.get(0));
                            }
                            adapter.addData(urls);
                        }
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onRequestError(String errorMessage) {
                        Utils.showToast(ImageLoaderActivity.this, errorMessage);
                        refreshLayout.setRefreshing(false);
                    }
                }).execute();
    }

    @Override
    protected void setListener() {
        refreshLayout.setOnRefreshListener(this);
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView:
                refreshLayout.refresh();
                break;
            default:
                break;
        }
    }

}
