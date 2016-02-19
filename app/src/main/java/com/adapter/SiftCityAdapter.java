package com.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.dao.dbHelpers.CityHelper;
import com.dao.generate.City;
import com.google.inject.Inject;
import com.util.UIUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import roboguice.RoboGuice;

/**
 * Created by cwj on 16/2/5.
 */
public class SiftCityAdapter extends BaseAdapter implements SectionIndexer {

    private final String sections = "最ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private Context context;
    private List<City> list = new ArrayList<>();

    @Inject
    private CityHelper cityHelper;

    public SiftCityAdapter(Context context) {
        this.context = context;
        RoboGuice.getInjector(context).injectMembersWithoutViews(this);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public City getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
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

    public void addList(List<City> cities) {
        //构建数据
        list.addAll(cities);
        //添加A-Z的item
        for (char i = 'A'; i <= 'Z'; i++) {
            City cA = new City();
            cA.setPinyin(String.valueOf(i));
            cA.setName(String.valueOf(i));
            list.add(cA);
        }
        //排序
        Collections.sort(list, new Comparator<City>() {
            @Override
            public int compare(City city1, City city2) {
                String sc1 = city1.getPinyin().toLowerCase();
                String sc2 = city2.getPinyin().toLowerCase();
                return sc1.compareTo(sc2);
            }
        });
        //加入最近使用
        addRecentUse();
//        //加入全部城市item
//        City allCity = new City();
//        allCity.setName("全部城市");
//        allCity.setPinyin("全部城市");
//        list.add(0, allCity);
    }

    private void addRecentUse() {
        //从数据库取出指定数量最近使用城市
        List<City> recentlyUsed = cityHelper.getRecentlyUsed();
        if (recentlyUsed.size() <= 0)
            return;
        //增加最近使用item
        City recentlyCity = new City();
        recentlyCity.setName("最近使用");
        recentlyCity.setPinyin("最近使用");
        recentlyUsed.add(0, recentlyCity);
        //放入到集合中
        list.addAll(0, recentlyUsed);
    }

    class ViewHodler {
        TextView textView;
    }
}
