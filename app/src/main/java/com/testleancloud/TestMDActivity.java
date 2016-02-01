package com.testleancloud;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.base.BaseActivity;
import com.util.MockData;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_test_md)
public class TestMDActivity extends BaseActivity {

    @InjectView(R.id.testSimpleAnim)
    private Button testSimpleAnim;

    @InjectView(R.id.testDefaultBehavior)
    private Button testDefaultBehavior;

    @InjectView(R.id.testScrollAndHide)
    private Button testScrollAndHide;

    @InjectView(R.id.testTabLayout)
    private Button testTabLayout;

    @InjectView(R.id.testBehavior)
    private Button testBehavior;

    @InjectView(R.id.testJianShu)
    private Button testJianShu;

    @InjectView(R.id.testSticky)
    private Button testSticky;

    @InjectView(R.id.testBigBitmap)
    private Button testBigBitmap;

    @Override
    protected void setListener() {
        testSimpleAnim.setOnClickListener(this);
        testDefaultBehavior.setOnClickListener(this);
        testScrollAndHide.setOnClickListener(this);
        testTabLayout.setOnClickListener(this);
        testBehavior.setOnClickListener(this);
        testJianShu.setOnClickListener(this);
        testSticky.setOnClickListener(this);
        testBigBitmap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.testSimpleAnim:
                startActivity(new Intent(TestMDActivity.this, SimpleAnimActivity.class));
                break;
            case R.id.testDefaultBehavior:
                startActivity(new Intent(TestMDActivity.this, CollapsingActivity.class));
                break;
            case R.id.testScrollAndHide:
                startActivity(new Intent(TestMDActivity.this, ScrollAndHideActivity.class));
                break;
            case R.id.testTabLayout:
                startActivity(new Intent(TestMDActivity.this, TestTabLayout.class));
                break;
            case R.id.testBehavior:
                startActivity(new Intent(TestMDActivity.this, BehaviorActivity.class));
                break;
            case R.id.testJianShu:
                startActivity(new Intent(TestMDActivity.this, JianShuActivity.class));
                break;
            case R.id.testSticky:
                startActivity(new Intent(TestMDActivity.this, StickyActivity.class));
                break;
            case R.id.testBigBitmap:
                List<String> urls = MockData.getImageUrls(0, 4);
                Intent intent = new Intent(TestMDActivity.this, BigBitmapActivity.class);
                intent.putStringArrayListExtra(BigBitmapActivity.INTENT_URL_KEY, (ArrayList<String>) urls);
                intent.putExtra(BigBitmapActivity.INTENT_FIRST_PAGE_KEY, 2);
                startActivity(intent);
                break;
        }
    }
}