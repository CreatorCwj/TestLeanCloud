package com.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.BaseViewPagerFragment;
import com.testleancloud.R;


public class TabFragment extends BaseViewPagerFragment {

    public static final String KEY = "position";

    private int pos;
    private int resumeNum;
    private int pauseNum;
    private TextView textView;

    public static TabFragment newInstance(int position) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt(KEY, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pos = getArguments().getInt(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.fragment_content);
        textView.setText("TAB" + pos);
    }

    /**
     * FragmentActivity销毁或HOME键时调用
     */
    @Override
    public void onPause() {
        super.onPause();
        pauseNum--;
        textView.setText("Resume:" + resumeNum + " Pause:" + pauseNum);
    }

    /**
     * 展示时显示,代替onResume
     */
    @Override
    public void onViewPagerFragmentResume() {
        resumeNum++;
        textView.setText("Resume:" + resumeNum + " Pause:" + pauseNum);
    }

    /**
     * 切换走时(非销毁)可在这里写逻辑
     */
    @Override
    public void onViewPagerFragmentPause() {
        pauseNum--;
        textView.setText("Resume:" + resumeNum + " Pause:" + pauseNum);
    }
}
