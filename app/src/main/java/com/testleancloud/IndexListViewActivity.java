package com.testleancloud;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.adapter.SiftCityAdapter;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.base.BaseActivity;
import com.dao.dbHelpers.CityHelper;
import com.google.inject.Inject;
import com.model.City;
import com.util.Utils;
import com.widget.indexableListView.IndexableListView;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_index_list_view)
public class IndexListViewActivity extends BaseActivity {

    @InjectView(R.id.indexLV)
    private IndexableListView listView;

    private SiftCityAdapter adapter;

    @Inject
    private CityHelper cityHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView.setFastScrollEnabled(true);
        listView.showIndexScrollerAlwas();

        //判断数据来源
        if (cityHelper.isEmpty()) {//网络获取
            loadFromNet();
        } else {//本地获取
            loadFromLocal();
        }
    }

    private void loadFromLocal() {
        showLoadingDialog("本地加载数据中...");
        List<com.dao.generate.City> result = cityHelper.findAll();
        setData(result);
        cancelLoadingDialog();
    }

    private void loadFromNet() {
        showLoadingDialog("网络加载数据中...");
        AVQuery<City> query = AVQuery.getQuery(City.class);
        query.setLimit(1000);
        query.findInBackground(new FindCallback<City>() {
            @Override
            public void done(List<City> list, AVException e) {
                if (e == null) {
                    //转成本地类
                    List<com.dao.generate.City> cities = new ArrayList<>();
                    for (City city : list) {
                        cities.add(new com.dao.generate.City(city.getCityId(), city.getName(), city.getPinyin(), city.getShortPinyin()));
                    }
                    //放入数据库
                    cityHelper.insertData(cities);
                    //设置数据
                    setData(cities);
                } else {
                    Utils.showToast(IndexListViewActivity.this, "加载数据失败");
                }
                cancelLoadingDialog();
            }
        });
    }

    private void setData(List<com.dao.generate.City> cities) {
        if (cities == null)
            return;
        adapter = new SiftCityAdapter(this);
        adapter.addList(cities);
        listView.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utils.showToast(IndexListViewActivity.this, adapter.getItem(position).getName());
                //拿到点击的city,设置最近使用时间
                com.dao.generate.City selectCity = adapter.getItem(position);
                selectCity.setLastUseTime(System.currentTimeMillis());
                //放入到数据库中(检查最近使用个数)
                cityHelper.updateRecentlyUsed(selectCity);
                IndexListViewActivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
