package com.base;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboFragmentActivity;

/**
 * Created by cwj on 16/2/19.
 */
public class BaseFragmentActivity extends RoboFragmentActivity {

    private List<BaseFragment> fragments = new ArrayList<>();

    /**
     * 设置监听回退的fragment
     */
    public void setOnBackPress(BaseFragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public void onBackPressed() {
        //先循环每个子fragment的回退事件,都返回false则交由系统处理
        boolean isConsumed = false;
        if (fragments != null && fragments.size() > 0) {
            for (BaseFragment fragment : fragments) {
                if (fragment.onBackPress()) {//有一个消费则不再给系统处理
                    isConsumed = true;
                }
            }
        }
        if (!isConsumed)
            super.onBackPressed();
    }
}
