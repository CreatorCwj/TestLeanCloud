package com.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.testleancloud.R;
import com.volley.Network;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Created by cwj on 15/11/25.
 */
public abstract class BaseActivity extends RoboActivity implements View.OnClickListener {

    protected final String TAG = "Activity";

    protected final Object NETWORK_TAG = this;

    @InjectView(R.id.textView)
    protected TextView textView;

    private ProgressDialog progressDialog;
    private final String defalutMsg = "loading...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        setListener();
    }

    abstract protected void setListener();

    public void showProgressDialog(String msg) {
        if (progressDialog == null)
            return;
        if (msg != null)
            progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public void showProgressDialog() {
        showProgressDialog(defalutMsg);
    }

    public void dismissProgressDialog() {
        if (progressDialog == null)
            return;
        progressDialog.cancel();
    }

    /**
     * 保存回调
     */
    protected SaveCallback saveCallback = new SaveCallback() {
        @Override
        public void done(AVException e) {
            if (e == null) {
                textView.setText("保存成功");
            } else {
                textView.setText(e.getMessage());
            }
            dismissProgressDialog();
        }
    };

    @Override
    protected void onDestroy() {
        Network.cancelRequest(NETWORK_TAG);
        super.onDestroy();
    }
}
