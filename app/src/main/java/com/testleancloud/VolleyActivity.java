package com.testleancloud;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.base.BaseActivity;
import com.imageLoader.ImageLoader;
import com.imageLoader.listener.ImageProgressStateListener;
import com.model.NowWeather;
import com.util.Utils;
import com.volley.Network;
import com.volley.listener.RequestCallback;
import com.volley.listener.RequestCancelCallback;

import java.util.HashMap;
import java.util.Map;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_volley)
public class VolleyActivity extends BaseActivity {

    @InjectView(R.id.sendGet)
    private Button sendGet;

    @InjectView(R.id.sendPost)
    private Button sendPost;

    @InjectView(R.id.cancelTag)
    private Button cancelTag;

    @InjectView(R.id.setCity)
    private EditText setCity;

    @InjectView(R.id.weatherImage)
    private ImageView weatherImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void cancelTag() {
        Network.cancelRequest(this);
    }

    private void sendPost() {

    }

    /**
     * 发送get请求
     */
    private void sendGet() {
        String city = setCity.getText().toString();
        if (TextUtils.isEmpty(city)) {
            Utils.showToast(this, "请输入城市名");
            return;
        }
        showProgressDialog();
        Map<String, String> params = new HashMap<>();
        params.put("area", city);
        new Network<>(this, NowWeather.class)
                .setPathUrl(getResources().getString(R.string.get_address_weather))
                .setMethod(Request.Method.GET)
                .setRequestParams(params)
                .setTag(this)
                .setRequestCallback(new RequestCallback<NowWeather>() {
                    @Override
                    public void onRequestSuccess(NowWeather result) {
                        if (result == null || result.getShowapi_res_body() == null) {
                            dismissProgressDialog();
                            return;
                        }
                        textView.setText(result.toString());
                        if (result.getShowapi_res_body().getNow() != null) {
                            String img = result.getShowapi_res_body().getNow().getWeather_pic();
                            ImageLoader.loadImage(weatherImage, img, new ImageProgressStateListener() {

                                @Override
                                public void onLoadingStarted(ImageView imageView, String imgUrl) {
                                    super.onLoadingStarted(imageView, imgUrl);
                                }

                                @Override
                                public void onProgress(ImageView imageView, String imgUrl, int current, int total, int progress) {
                                    textView.setText(progress + "%");
                                }
                            });
                        }
                        dismissProgressDialog();
                    }

                    @Override
                    public void onRequestError(String errorMessage) {
                        textView.setText(errorMessage);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onRequestFinally() {

                    }
                })
                .setRequestCancelCallback(new RequestCancelCallback() {
                    @Override
                    public void onCanceled() {
                        Utils.showToast(VolleyActivity.this, "Canceled");
                    }
                }).execute();
    }

    @Override
    protected void setListener() {
        sendGet.setOnClickListener(this);
        sendPost.setOnClickListener(this);
        cancelTag.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendGet:
                sendGet();
                break;
            case R.id.sendPost:
                sendPost();
                break;
            case R.id.cancelTag:
                cancelTag();
                break;
            default:
                break;
        }
    }

}
