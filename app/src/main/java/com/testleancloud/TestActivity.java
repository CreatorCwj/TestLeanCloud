package com.testleancloud;

import android.os.Bundle;
import android.view.View;

import com.base.BaseActivity;
import com.model.MyCategory;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContentView;

@ContentView(R.layout.activity_test)
public class TestActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView:
                start();
                break;
        }
    }

    private void start() {
        List<String> categories = new ArrayList<>();
        categories.add("美发");
        categories.add("美甲");
        categories.add("美容SPA");
        categories.add("瑜伽/舞蹈");

        int startId = 1050;
        for (int i = 0; i < categories.size(); i++) {
            MyCategory category = new MyCategory();
            category.setParentId(1006);
            category.setName(categories.get(i));//名字
            category.setCategoryId(startId + i);//自己的id
            category.saveInBackground(saveCallback);
        }
    }

}
