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
import com.widget.rlrView.view.RLRView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_jian_shu)
public class JianShuActivity extends BaseActivity implements RLRView.OnRefreshListener, RLRView.OnLoadListener {

    @InjectView(R.id.rlrView)
    private RLRView rlrView;

    private NormalRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NormalRecyclerAdapter(this);
        rlrView.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        rlrView.setOnRefreshListener(this);
        rlrView.setOnLoadListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onLoad() {
        obtainData();
    }

    @Override
    public void onRefresh() {
        adapter.clearData();
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
                        Utils.showToast(JianShuActivity.this, errorMessage);
                        rlrView.rlError();
                    }

                    @Override
                    public void onRequestFinally() {
                        rlrView.stopRL();
                    }
                }).execute();
    }
}
