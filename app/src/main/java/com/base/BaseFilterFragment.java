package com.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adapter.FilterRecyclerAdapter;
import com.dao.base.BaseFilterModel;
import com.testleancloud.R;
import com.widget.rlrView.view.LoadMoreRecyclerView;
import com.widget.rlrView.view.RLRView;

import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by cwj on 16/2/29.
 * 筛选器fragment基类,继承后只需重写获取数据的方法即可
 */
public abstract class BaseFilterFragment<T extends BaseFilterModel> extends BaseFragment {

    @InjectView(R.id.overlay_view)
    private View overlay;

    @InjectView(R.id.left_rlrView)
    private RLRView leftRlrView;

    @InjectView(R.id.right_rlrView)
    private RLRView rightRlrView;

    private FilterRecyclerAdapter<T> firstAdapter;
    private FilterRecyclerAdapter<T> subAdapter;

    private OnSelectListener<T> selectListener;

    public interface OnSelectListener<T> {
        void onSelect(T obj);
    }

    public void setOnSelectListener(OnSelectListener<T> selectListener) {
        this.selectListener = selectListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstAdapter = new FilterRecyclerAdapter<>(getActivity());
        subAdapter = new FilterRecyclerAdapter<>(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOverlay();
        setRLRView();
    }

    @SuppressWarnings("unchecked")
    private void setRLRView() {
        leftRlrView.setAdapter(firstAdapter);
        rightRlrView.setAdapter(subAdapter);
        //一级品类
        List<T> firstFilters = getFirstFilters();
        if (firstFilters == null || firstFilters.size() < 1)//无数据不显示
            return;
        firstFilters.add(0, (T) firstFilters.get(0).getAllFirstFilter());//全部选项
        leftRlrView.addData(firstFilters);
        leftRlrView.setOnItemClickListener(new LoadMoreRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //selected
                leftRlrView.setSelected(position, true, false, true);
                rightRlrView.clearData();
                //二级品类
                List<T> subFilters = getSubFilters(firstAdapter.getDataItem(position).getFilterId());
                //无二级品类
                if (subFilters == null || subFilters.size() < 1) {
                    hideFragment();
                    if (selectListener != null)
                        selectListener.onSelect(firstAdapter.getDataItem(position));
                    return;
                }
                //有二级品类
                subFilters.add(0, (T) subFilters.get(0).getAllSubFilter(firstAdapter.getDataItem(position).getFilterId()));//全部选项
                rightRlrView.resetData(subFilters);
            }
        });
        rightRlrView.setOnItemClickListener(new LoadMoreRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                rightRlrView.setSelected(position, true, false, true);
                hideFragment();
                if (selectListener != null)
                    selectListener.onSelect(subAdapter.getDataItem(position));
            }
        });
        //initFirst
        //一定要注意size可能为0
        initSelect(firstFilters.get(0));
    }

    @SuppressWarnings("unchecked")
    public void initSelect(T filter) {
        if (firstAdapter.getDataCount() < 1)//无数据不显示
            return;
        //null默认为全部
        if (filter == null) {
            leftRlrView.setSelected(0, true, true, true);
            rightRlrView.clearData();
            return;
        }
        if (filter.isAllSubFilter() || filter.isSubFilter()) {//有父品类
            //父品类
            for (int i = 0; i < firstAdapter.getDataCount(); i++) {
                T t = firstAdapter.getDataItem(i);
                if (t.getFilterId() == filter.getFilterParentId()) {//应该选中的父品类
                    leftRlrView.setSelected(i, true, true, true);
                    break;
                }
            }
            //子品类
            List<T> sub = getSubFilters(filter.getFilterParentId());
            sub.add(0, (T) sub.get(0).getAllSubFilter(filter.getFilterParentId()));//全部选项
            rightRlrView.resetData(sub);
            for (int i = 0; i < subAdapter.getDataCount(); i++) {
                T t = subAdapter.getDataItem(i);
                if (filter.equalFilter(t)) {//应该选中的
                    rightRlrView.setSelected(i, true, true, true);
                    break;
                }
            }
        } else {//本身是父品类
            rightRlrView.clearData();
            for (int i = 0; i < firstAdapter.getDataCount(); i++) {
                T t = firstAdapter.getDataItem(i);
                if (t.getFilterId() == filter.getFilterId()) {//应该选中的父品类
                    leftRlrView.setSelected(i, true, true, true);
                    break;
                }
            }
        }
    }

    protected abstract List<T> getSubFilters(int parentFilterId);

    protected abstract List<T> getFirstFilters();

    private void setOverlay() {
        overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFragment();
            }
        });
    }

    @Override
    public boolean onBackPress() {
        hideFragment();
        return true;
    }
}
