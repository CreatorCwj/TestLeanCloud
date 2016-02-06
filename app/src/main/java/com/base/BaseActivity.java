package com.base;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.testleancloud.R;
import com.volley.Network;
import com.widget.dialog.LoadingDialog;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Created by cwj on 15/11/25.
 * Activity基类
 */
public abstract class BaseActivity extends RoboActivity implements View.OnClickListener {

    protected final String TAG = "Activity";

    protected final Object NETWORK_TAG = this;

    @InjectView(R.id.textView)
    protected TextView textView;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog(this);
        setListener();
    }

    abstract protected void setListener();

    public void showLoadingDialog(String msg) {
        loadingDialog.show(msg);
    }

    public void showLoadingDialog() {
        loadingDialog.show();
    }

    public void cancelLoadingDialog() {
        loadingDialog.cancel();
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
            cancelLoadingDialog();
        }
    };

    @Override
    protected void onDestroy() {
        Network.cancelRequest(NETWORK_TAG);
        super.onDestroy();
    }
}
