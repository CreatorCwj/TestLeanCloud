package com.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import com.testleancloud.R;
import com.util.UIUtils;
import com.widget.loadmorerecyclerview.LoadMoreRecyclerView;
import com.widget.loadmorerecyclerview.Page;
import com.widget.loadmorerecyclerview.adapter.BaseRecyclerViewAdapter;
import com.widget.loadmorerecyclerview.adapter.RecyclerViewAdapter;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by cwj on 16/1/16.
 * 可设置自动刷新与否以及外部手动刷新的刷新控件
 * 支持刷新不加载,加载不刷新
 * 与{@link LoadMoreRecyclerView}联合使用
 */
public class RLRView extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.OnLoadListener {

    private boolean autoRefresh = true;
    private LoadMoreRecyclerView loadMoreRecyclerView;
    private Page page;

    private OnRefreshListener onRefreshListener;
    private OnLoadListener onLoadListener;

    /**
     * 线性
     */
    public static final int LINEAR = 0;

    /**
     * 网格
     */
    public static final int GRID = 1;

    /**
     * 瀑布流
     */
    public static final int WATER_FALL = 2;

    private int layoutType = -1;

    /**
     * 刷新请实现该接口
     */
    public interface OnRefreshListener {
        void onRefresh();
    }

    /**
     * 加载请实现该接口
     */
    public interface OnLoadListener {
        void onLoad();
    }

    public RLRView(Context context) {
        this(context, null);
    }

    public RLRView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addChild();//先添加可加载的view
        initAttrs(context, attrs);//初始化布局属性
        initProps();//初始化一些样式
        initPage();//初始化page(把分页逻辑统一)
    }

    private void initPage() {
        page = new Page();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RLRView);
            int layoutType = typedArray.getInt(R.styleable.RLRView_layoutType, LINEAR);//默认线性
            int columnCount = typedArray.getInt(R.styleable.RLRView_columnCount, 2);//需要的话默认2列
            setLayoutType(layoutType, columnCount);
            typedArray.recycle();
        }
    }

    /**
     * 设置layoutManager和列数
     *
     * @param layoutType
     * @param columnCount
     */
    private void setLayoutType(int layoutType, int columnCount) {
        if (this.layoutType != layoutType) {//和原来相同则不变
            this.layoutType = layoutType;
            switch (this.layoutType) {
                case LINEAR:
                    loadMoreRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    break;
                case GRID:
                    loadMoreRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), columnCount));
                    break;
                case WATER_FALL:
                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
                    manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
                    loadMoreRecyclerView.setLayoutManager(manager);
                    break;
            }
        }
    }

    private void addChild() {
        loadMoreRecyclerView = new LoadMoreRecyclerView(getContext());
        loadMoreRecyclerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(loadMoreRecyclerView);
    }

    private void initProps() {
        this.setOnRefreshListener(this);
        loadMoreRecyclerView.setOnLoadListener(this);
        //一定要设置offset,否则调用setRefreshing(true)时没有效果
        this.setProgressViewOffset(false, -UIUtils.dp2px(getContext(), 24), UIUtils.dp2px(getContext(), 24));
        //onResume回调后view会回调此方法来监听layout的改变
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //可以自动刷新且没有在加载的时候调用刷新
                if (isAutoRefresh() && !isLoading()) {
                    invokeRefresh();
                }
                //防止后续调用,调用一次后即可取消
                RLRView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    //通过反射拿到listener
    private void invokeRefresh() {
        try {
            Field field = SwipeRefreshLayout.class.getDeclaredField("mListener");
            field.setAccessible(true);
            Object obj = field.get(this);
            if (obj != null && obj instanceof SwipeRefreshLayout.OnRefreshListener) {
                setRefreshing(true);
                ((SwipeRefreshLayout.OnRefreshListener) obj).onRefresh();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置adapter,放入recyclerView里
     *
     * @param adapter
     */
    public void setAdapter(RecyclerViewAdapter adapter) {
        loadMoreRecyclerView.setAdapter(adapter);
    }

    /**
     * recyclerView是否在加载
     *
     * @return
     */
    public boolean isLoading() {
        return loadMoreRecyclerView.isLoading();
    }

    /**
     * 外部主动调用
     */
    public void refresh() {
        //刷新或加载时不允许刷新
        if (isRefreshing() || isLoading())
            return;
        invokeRefresh();
    }

    /**
     * 是否自动刷新
     *
     * @return
     */
    public boolean isAutoRefresh() {
        return autoRefresh;
    }

    /**
     * 设置是否允许第一次自动刷新
     * 默认为允许自动刷新
     *
     * @param autoRefresh
     */
    public void setAutoRefresh(boolean autoRefresh) {
        this.autoRefresh = autoRefresh;
    }

    /**
     * 设置自定义刷新监听器
     *
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.onRefreshListener = listener;
    }

    /**
     * 设置自定义加载监听器
     *
     * @param onLoadListener
     */
    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    /**
     * itemClick
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(BaseRecyclerViewAdapter.OnItemClickListener onItemClickListener) {
        loadMoreRecyclerView.setOnItemClickListener(onItemClickListener);
    }

    /**
     * itemLongClick
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(BaseRecyclerViewAdapter.OnItemLongClickListener onItemLongClickListener) {
        loadMoreRecyclerView.setOnItemLongClickListener(onItemLongClickListener);
    }

    /**
     * 自带listener只能是自己,需要传入自定义的listener来监听刷新
     *
     * @param listener
     */
    @Override
    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        super.setOnRefreshListener(this);
    }

    @Override
    public void onLoad() {
        page.nextPage();//下一页
        if (onLoadListener != null)
            onLoadListener.onLoad();
    }

    @Override
    final public void onRefresh() {
        //swipe手势刷新时会调用此方法将refresh状态设置为true,如果此时在加载,不可以刷新,而且要讲刷新状态设置为false
        if (isLoading()) {
            setRefreshing(false);
            return;
        }
        //重置页数
        page.reset();
        //否则可以调用刷新,记住要恢复加载状态
        resetCanLoadState();
        if (onRefreshListener != null) {
            onRefreshListener.onRefresh();
        }
    }

    /**
     * 刷新加载失败,页数恢复
     */
    public void rlError() {
        page.prePage();
    }

    /**
     * 拦截一下添加数据,自动判断是否无法加载更多
     *
     * @param dataList
     */
    public void addData(List dataList) {
        RecyclerView.Adapter adapter = loadMoreRecyclerView.getAdapter();
        if (adapter != null && adapter instanceof RecyclerViewAdapter) {
            if (dataList.size() < page.getPageSize()) {
                setCanLoadMore(false);
            }
            ((RecyclerViewAdapter) adapter).addData(dataList);
        }
    }

    /**
     * 重置加载状态,当初设置可以加载的话,刷新时候应该重置为可加载
     */
    private void resetCanLoadState() {
        Boolean b = loadMoreRecyclerView.getCanLoadMoreInit();
        if (b == null || b) {
            loadMoreRecyclerView.setCanLoadMore(true);
        } else {
            loadMoreRecyclerView.setCanLoadMore(false);
        }
    }

    /**
     * 设置是否可以加载更多
     *
     * @param canLoadMore
     */
    public void setCanLoadMore(boolean canLoadMore) {
        loadMoreRecyclerView.setCanLoadMore(canLoadMore);
    }

    private void stopRefresh() {
        setRefreshing(false);
    }

    private void stopLoadMore() {
        loadMoreRecyclerView.stopLoadMore();
    }

    /**
     * 结束刷新加载
     */
    public void stopRL() {
        stopRefresh();
        stopLoadMore();
    }

    /**
     * 获取recyclerView
     *
     * @return
     */
    public LoadMoreRecyclerView getLoadMoreView() {
        return loadMoreRecyclerView;
    }

    /**
     * 返回当前页数
     *
     * @return
     */
    public int getPageNo() {
        return page.getPageNo();
    }

    /**
     * 返回当前页数据多少
     *
     * @return
     */
    public int getPageSize() {
        return page.getPageSize();
    }

    /**
     * 返回从第几个开始
     *
     * @return
     */
    public int getSkipCount() {
        return page.getSkipCount();
    }
}
