package com.fragment;

import android.graphics.Color;
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

        //test color
        int color;
        switch (pos) {
            case 1:
                color = Color.WHITE;
                break;
            case 2:
                color = Color.RED;
                break;
            case 3:
                color = Color.GREEN;
                break;
            case 4:
                color = Color.BLUE;
                break;
            case 5:
                color = Color.YELLOW;
                break;
            case 6:
                color = Color.GRAY;
                break;
            default:
                color = Color.WHITE;
                break;
        }
        view.setBackgroundColor(color);
    }

    /**
     * 展示时显示,完全代替onResume
     */
    @Override
    public void onViewPagerFragmentResume() {
        resumeNum++;
        textView.setText("Resume:" + resumeNum + " Pause:" + pauseNum);
    }

    /**
     * 不显示时调用,完全替代onPause(可以把viewPager的每个fragment看成一个activity的生命周期过程)
     */
    @Override
    public void onViewPagerFragmentPause() {
        pauseNum--;
        textView.setText("Resume:" + resumeNum + " Pause:" + pauseNum);
    }
}
