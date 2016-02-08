package com.testleancloud;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.base.BaseActivity;
import com.model.Place;
import com.model.Post;
import com.volley.Network;

import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_location)
public class LocationActivity extends BaseActivity {

    @InjectView(R.id.textView)
    private TextView textView;

    @InjectView(R.id.setPlaceId)
    private EditText setPlaceId;

    @InjectView(R.id.queryNearby)
    private Button queryNearby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 按距离排序，查找指定place附近的place
     * 也可指定某距离(长度或半径)内的,以及在某一区域内的(提供东北角和西南角坐标即可)
     */
    public void queryNearby() {
        String placeId = setPlaceId.getText().toString();
        if (TextUtils.isEmpty(placeId))
            return;
        showLoadingDialog("正在查询指定place...");
        AVQuery<Place> specQuery = AVObject.getQuery(Place.class);
        specQuery.getInBackground(placeId, new GetCallback<Place>() {
            @Override
            public void done(Place object, AVException e) {
                if (e == null) {
                    cancelLoadingDialog();
                    showLoadingDialog("正在查找目标place...");
                    AVQuery<Place> query = AVObject.getQuery(Place.class);
//                    query.whereNear(Place.PLACE_LOCATION, object.getLocation());//按距离排序由近到远
                    query.whereWithinKilometers(Place.PLACE_LOCATION, object.getLocation(), 5);//5km之内的
                    query.findInBackground(new FindCallback<Place>() {
                        @Override
                        public void done(List<Place> objects, AVException e) {
                            if (e == null) {
                                StringBuilder sb = new StringBuilder("");
                                for (Place place : objects) {
                                    sb.append(place.text());
                                }
                                textView.setText(sb.toString());
                            } else {
                                textView.setText(e.getMessage());
                            }
                            cancelLoadingDialog();
                        }
                    });
                } else {
                    textView.setText(e.getMessage());
                    cancelLoadingDialog();
                }
            }
        });
        Network<List<Post>> network;
    }

    @Override
    protected void setListener() {
        queryNearby.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.queryNearby:
                queryNearby();
                break;
            default:
                break;
        }
    }
}
