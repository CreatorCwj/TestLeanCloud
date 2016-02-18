package com.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.BaseFragment;
import com.testleancloud.R;

import roboguice.inject.InjectView;

public class ChildFragment extends BaseFragment {

    @InjectView(R.id.overlay_view)
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParentFragment() != null) {//可见的话就消失,消费之
                    getParentFragment().getChildFragmentManager()
                            .beginTransaction().hide(ChildFragment.this).commitAllowingStateLoss();
                }
            }
        });
    }

    @Override
    public boolean onBackPress() {
        if (getParentFragment() != null && getParentFragment().isVisible() && isVisible()) {//可见的话就消失,消费之
            getParentFragment().getChildFragmentManager()
                    .beginTransaction().hide(this).commitAllowingStateLoss();
            return true;
        }
        return super.onBackPress();
    }
}
