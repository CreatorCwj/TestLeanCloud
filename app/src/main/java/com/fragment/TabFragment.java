package com.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.base.BaseViewPagerFragment;
import com.testleancloud.R;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;


public class TabFragment extends BaseViewPagerFragment {

    public static final String KEY = "position";

    private int pos;
    private int resumeNum;
    private int pauseNum;

    @InjectView(R.id.fragment_content)
    private TextView textView;

    @InjectView(R.id.lv)
    private ListView listView;

    private ChildFragment childFragment;

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
        childFragment = new ChildFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add("" + i);
        }
        listView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, data));

        //测试childFragment
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fm = getChildFragmentManager().beginTransaction();
                if (childFragment.isAdded()) {
                    if (childFragment.isVisible()) {
                        fm.hide(childFragment);
                    } else {
                        fm.show(childFragment);
                    }
                } else {
                    fm.add(R.id.child_fragment_content, childFragment);
                }
                fm.commitAllowingStateLoss();
            }
        });
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
