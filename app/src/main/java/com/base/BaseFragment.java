package com.base;

import com.volley.Network;

import roboguice.fragment.RoboFragment;

/**
 * Created by cwj on 16/1/28.
 */
public abstract class BaseFragment extends RoboFragment {

    protected final Object NETWORK_TAG = this;

    @Override
    public void onDestroy() {
        Network.cancelRequest(NETWORK_TAG);
        super.onDestroy();
    }
}
