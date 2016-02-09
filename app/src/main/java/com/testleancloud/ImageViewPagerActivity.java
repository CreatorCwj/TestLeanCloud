package com.testleancloud;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.base.BaseActivity;
import com.util.MockData;
import com.util.Utils;
import com.widget.viewPagers.imageViewPager.ImageViewPager;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_image_view_pager)
public class ImageViewPagerActivity extends BaseActivity {

    @InjectView(R.id.imageViewPager)
    private ImageViewPager imageViewPager;

    private List<String> urls;
    private List<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urls = MockData.getImageUrls(0, 4);
        imageViewPager.setUrls(urls);
        titles = new ArrayList<>();
        titles.add("我是第一张图片");
        titles.add("我是第二张图片");
        titles.add("我是第三张图片,来测试长度的,我是第三张图片,来测试长度的,我是第三张图片,来测试长度的,我是第三张图片,来测试长度的");
        titles.add("赫赫");
        imageViewPager.setTitles(titles);
        imageViewPager.setOnItemClickListener(new ImageViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(ImageViewPager imageViewPager, int position, ImageView imageView) {
                Utils.showToast(ImageViewPagerActivity.this, "pos:" + position);
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
