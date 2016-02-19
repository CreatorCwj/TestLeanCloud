package com.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.volley.Network;

import roboguice.fragment.RoboFragment;

/**
 * Created by cwj on 16/1/28.
 */
public abstract class BaseFragment extends RoboFragment {

    protected final Object NETWORK_TAG = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity fa = getActivity();
        if (fa instanceof BaseFragmentActivity)
            ((BaseFragmentActivity) fa).setOnBackPress(this);
    }

    /**
     * 回退事件
     *
     * @return false为未消费, true为消费
     */
    public boolean onBackPress() {
        return false;
    }

    @Override
    public void onDestroy() {
        Network.cancelRequest(NETWORK_TAG);
        super.onDestroy();
    }
}
