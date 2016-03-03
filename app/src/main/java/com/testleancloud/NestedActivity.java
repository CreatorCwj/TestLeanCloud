package com.testleancloud;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.adapter.NormalRecyclerAdapter;
import com.base.BaseActivity;
import com.util.MockData;
import com.widget.rlrView.view.LoadMoreRecyclerView;
import com.widget.rlrView.view.RLRView;

import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_nested)
public class NestedActivity extends BaseActivity {

//    @InjectView(R.id.nestedScrollView)
//    private NestedScrollView nestedScrollView;

    @InjectView(R.id.rlrView)
    private RLRView rlrView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rlrView.setAdapter(new NormalRecyclerAdapter(this));
    }

    @Override
    protected void setListener() {
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        List<String> urls = MockData.getImageUrls(0, 15);
        rlrView.addData(urls);
        reset();
    }

    private void reset() {
        rlrView.getLoadMoreView().setNestedScrollingEnabled(false);//禁止滚动
        LoadMoreRecyclerView loadMoreRecyclerView = rlrView.getLoadMoreView();
        int total = 0;
        int count = loadMoreRecyclerView.getLayoutManager().getItemCount();
        for (int i = 0; i < count; i++) {
            View view = loadMoreRecyclerView.getAdapter().onCreateViewHolder(loadMoreRecyclerView, loadMoreRecyclerView.getAdapter().getItemViewType(i)).itemView;
            if (view != null) {
                view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                total += view.getMeasuredHeight();
                Rect out = new Rect();
                loadMoreRecyclerView.getLayoutManager().calculateItemDecorationsForChild(view, out);
                total += out.bottom - out.top;
            }
        }
        rlrView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, total));
    }
}
