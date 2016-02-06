package com.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.model.CitySiftModel;
import com.util.UIUtils;

import java.util.List;

/**
 * Created by cwj on 16/2/5.
 */
public class SiftCityAdapter extends BaseAdapter implements SectionIndexer {

    private final String sections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private Context context;
    private List<CitySiftModel> list;

    public SiftCityAdapter(Context context, List<CitySiftModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CitySiftModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler = null;
        if (convertView == null) {
            convertView = new TextView(context);
            convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dp2px(context, 80)));
            viewHodler = new ViewHodler();
            viewHodler.textView = (TextView) convertView;
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        viewHodler.textView.setText(getItem(position).getName());
        return convertView;
    }

    @Override
    public String[] getSections() {
        String[] strings = new String[sections.length()];
        for (int i = 0; i < sections.length(); ++i) {
            strings[i] = sections.charAt(i) + "";
        }
        return strings;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        // 如果当前部分没有item，则之前的部分将被选择
        for (int i = sectionIndex; i >= 0; i--) {
            for (int j = 0; j < getCount(); j++) {
                if (i == 0) { // #
                    // For numeric section 数字
                    /*for (int k = 0; k <= 9; k++) {// 1...9
                        // 字符串第一个字符与1~9之间的数字进行匹配
                        if (StringMatcher.match(
                                String.valueOf(getItem(j).charAt(0)),
                                String.valueOf(k)))
                            return j;
                    }*/
                    return 0;
                } else { // A~Z
                    String py = String.valueOf(getItem(j).getPinyin().charAt(0) + "").toLowerCase();
                    String sec = getSections()[i].toLowerCase();
                    if (py.equals(sec))
                        return j;
                }
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    class ViewHodler {
        TextView textView;
    }
}
