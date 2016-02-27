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

    @InjectView(R.id.testMain)
    private Button testMain;

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

    @InjectView(R.id.testDatePicker)
    private Button testDatePicker;

    @InjectView(R.id.testIndexLV)
    private Button testIndexLV;

    @InjectView(R.id.testDialog)
    private Button testDialog;

    @InjectView(R.id.testMainAdapter)
    private Button testMainAdapter;

    @InjectView(R.id.testImageViewPager)
    private Button testImageViewPager;

    @InjectView(R.id.testDrawable)
    private Button testDrawable;

    @InjectView(R.id.testScanner)
    private Button testScanner;

    @InjectView(R.id.testRoundImage)
    private Button testRoundImage;

    @InjectView(R.id.testPullImg)
    private Button testPullImg;

    @InjectView(R.id.testLocation)
    private Button testLocation;

    @InjectView(R.id.testMap)
    private Button testMap;

    @InjectView(R.id.testSortFilter)
    private Button testSortFilter;

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
        testDatePicker.setOnClickListener(this);
        testIndexLV.setOnClickListener(this);
        testDialog.setOnClickListener(this);
        testMain.setOnClickListener(this);
        testMainAdapter.setOnClickListener(this);
        testImageViewPager.setOnClickListener(this);
        testDrawable.setOnClickListener(this);
        testScanner.setOnClickListener(this);
        testRoundImage.setOnClickListener(this);
        testPullImg.setOnClickListener(this);
        testLocation.setOnClickListener(this);
        testMap.setOnClickListener(this);
        testSortFilter.setOnClickListener(this);
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
            case R.id.testDatePicker:
                startActivity(new Intent(TestMDActivity.this, DatePickerActivity.class));
                break;
            case R.id.testIndexLV:
                startActivity(new Intent(TestMDActivity.this, IndexListViewActivity.class));
                break;
            case R.id.testDialog:
                startActivity(new Intent(TestMDActivity.this, DialogActivity.class));
                break;
            case R.id.testMain:
                startActivity(new Intent(TestMDActivity.this, MainActivity.class));
                break;
            case R.id.testMainAdapter:
                startActivity(new Intent(TestMDActivity.this, MainAdapterActivity.class));
                break;
            case R.id.testImageViewPager:
                startActivity(new Intent(TestMDActivity.this, ImageViewPagerActivity.class));
                break;
            case R.id.testDrawable:
                startActivity(new Intent(TestMDActivity.this, DrawableActivity.class));
                break;
            case R.id.testScanner:
                startActivity(new Intent(TestMDActivity.this, ScannerActivity.class));
                break;
            case R.id.testRoundImage:
                startActivity(new Intent(TestMDActivity.this, RoundImageActivity.class));
                break;
            case R.id.testPullImg:
                startActivity(new Intent(TestMDActivity.this, PullImgActivity.class));
                break;
            case R.id.testLocation:
                startActivity(new Intent(TestMDActivity.this, BaiduLocationActivity.class));
                break;
            case R.id.testMap:
                startActivity(new Intent(TestMDActivity.this, MapActivity.class));
                break;
            case R.id.testSortFilter:
                startActivity(new Intent(TestMDActivity.this, SortFilterActivity.class));
                break;
        }
    }

//    private long preTime = -1500;
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK
//                && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - preTime) >= 1500) {
//                Toast.makeText(getApplicationContext(), "再按一次退出应用",
//                        Toast.LENGTH_SHORT).show();
//                preTime = System.currentTimeMillis();
//            } else {
//                finish();
//                System.exit(0);
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
