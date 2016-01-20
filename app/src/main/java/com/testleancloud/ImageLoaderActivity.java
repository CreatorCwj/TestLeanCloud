package com.testleancloud;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.widget.loadmorerecyclerview.LoadMoreRecyclerView;
import com.widget.loadmorerecyclerview.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_image_loader)
public class ImageLoaderActivity extends BaseActivity implements AutoSwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.OnLoadListener {

    @InjectView(R.id.refreshLayout)
    private AutoSwipeRefreshLayout refreshLayout;

    @InjectView(R.id.recyclerView)
    private LoadMoreRecyclerView recyclerView;

    private NormalRecyclerAdapter adapter;

    private Page page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRefreshLayout();
        initRecycleView();
    }

    private void initRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//线性

//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));//gridView

//        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//瀑布流

        adapter = new NormalRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setCanLoadMore(true);

        page = new Page();
        page.setPageSize(1);
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
        page.reset();
        adapter.clearData();
        obtainData();
    }

    @Override
    public void onLoad() {
        page.nextPage();
        page.setPageSize(15);
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
                            List<String> urls = new ArrayList<>();
//                            for (List<String> item : result.getImage()) {
//                                urls.add(item.get(0));
//                            }
                            urls.add("http://img1.imgtn.bdimg.com/it/u=2282547951,3816622274&fm=21&gp=0.jpg");
                            adapter.addData(urls);
                            if (urls.size() < page.getPageSize()) {//可以停止加载了
                                recyclerView.setCanLoadMore(false);
                                page.setPageSize(1);
                            }
                        }
                        refreshLayout.setRefreshing(false);
                        recyclerView.stopLoadMore();
                    }

                    @Override
                    public void onRequestError(String errorMessage) {
                        page.prePage();
                        Utils.showToast(ImageLoaderActivity.this, errorMessage);
                        refreshLayout.setRefreshing(false);
                        recyclerView.stopLoadMore();
                    }
                }).execute();
    }

    @Override
    protected void setListener() {
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setOnLoadListener(this);
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
