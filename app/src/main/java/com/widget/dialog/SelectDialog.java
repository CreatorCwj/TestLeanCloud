package com.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.testleancloud.R;
import com.util.UIUtils;
import com.widget.dialog.base.BaseDialog;

import java.util.List;

/**
 * Created by cwj on 16/2/6.
 * 简单的多个文本选项的dialog
 */
public class SelectDialog extends BaseDialog {

    private ListView listView;
    private List<String> items;

    public SelectDialog(Context context, List<String> items) {
        super(context);
        this.items = items;
    }

    public SelectDialog(Context context, int themeResId, List<String> items) {
        super(context, themeResId);
        this.items = items;
    }

    /**
     * 可以动态的增加item
     *
     * @param items
     */
    public void addItems(List<String> items) {
        this.items.addAll(items);
    }

    public ListView getListView() {
        return listView;
    }

    @Override
    protected View onCreateView() {
        listView = new ListView(context);
        return listView;
    }

    @Override
    protected void onViewCreated(View view) {
        listView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setDivider(context.getResources().getDrawable(R.color.dividerColor, null));
        listView.setDividerHeight(UIUtils.dp2px(context, 1));
        listView.setVerticalScrollBarEnabled(false);
    }

    @Override
    public void show() {
        if (listView.getAdapter() == null)
            listView.setAdapter(new SelectDialogAdapter(context, items));
        //根据个数决定显示高度
        int height = getListViewHeight();
        if (height > maxHeight)
            height = maxHeight;
        listView.setLayoutParams(new FrameLayout.LayoutParams(maxWidth, height));
        super.show();
    }

    private int getListViewHeight() {
        ListAdapter adapter = listView.getAdapter();
        int height = 0;
        for (int i = 0; i < adapter.getCount(); ++i) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        height += listView.getDividerHeight() * (adapter.getCount() - 1);
        return height;
    }

    private class SelectDialogAdapter extends BaseAdapter {

        private Context context;
        private List<String> items;

        public SelectDialogAdapter(Context context, List<String> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public String getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.select_dialog_item, null);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.select_dialog_item_textView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(getItem(position));
            return convertView;
        }

        class ViewHolder {
            TextView textView;
        }
    }
}
