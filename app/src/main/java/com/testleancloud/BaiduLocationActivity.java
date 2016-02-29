package com.testleancloud;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.base.BaseActivity;
import com.location.Location;
import com.location.OnLocationListener;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_baidu_location)
public class BaiduLocationActivity extends BaseActivity {

    @InjectView(R.id.locate)
    private Button locate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        locate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.locate:
                locate();
                break;
        }
    }

    private void locate() {
        Location.requestLocation(this, new OnLocationListener() {
            @Override
            public void onPreExecute() {
                textView.setText("正在定位...");
            }

            @Override
            public void onSuccess(BDLocation location) {
                textView.setText("city:" + location.getCity());
            }

            @Override
            public void onFailed() {
                textView.setText("定位失败");
            }

            @Override
            public void onFinally() {

            }
        });
    }
}
