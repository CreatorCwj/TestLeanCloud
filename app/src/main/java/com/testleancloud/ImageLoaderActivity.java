package com.testleancloud;

import android.os.Bundle;
import android.view.View;

import com.adapter.NormalRecyclerAdapter;
import com.android.volley.Request;
import com.base.BaseActivity;
import com.model.GirlImage;
import com.util.MockData;
import com.util.Utils;
import com.viewholder.ButtonHeaderViewHolder;
import com.volley.Network;
import com.volley.listener.RequestCallback;
import com.widget.rlrView.view.LoadMoreRecyclerView;
import com.widget.rlrView.view.RLRView;

import java.util.ArrayList;
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

    int num = -1;

    enum STATE {
        NORMAL, ERROR, NONE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRLRView();
        initHeaderView();
    }

    private void initHeaderView() {
        rlrView.addHeader(new ButtonHeaderViewHolder(this, R.layout.header_view));
    }

    private void initRLRView() {
        rlrView.setColorSchemeResources(
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        adapter = new NormalRecyclerAdapter(this);
        rlrView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        num++;
        obtainData(true);
    }

    @Override
    public void onLoad() {
//        obtainData(false);
    }

    private void obtainData(final boolean isRefresh) {
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
                        switch (num % STATE.values().length) {
                            case 0://normal
                                List<String> urls;
                                urls = MockData.getImageUrls(rlrView.getSkipCount(), rlrView.getPageSize());
                                if (isRefresh)
                                    rlrView.resetData(urls);
                                else
                                    rlrView.addData(urls);
                                break;
                            case 1://error
                                onRequestError("加载错误");
                                break;
                            case 2://none
                                List<String> urls2 = new ArrayList<>();
                                if (isRefresh)
                                    rlrView.resetData(urls2);
                                else
                                    rlrView.addData(urls2);
                                break;
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
                showLoadingDialog("加载中...");
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
