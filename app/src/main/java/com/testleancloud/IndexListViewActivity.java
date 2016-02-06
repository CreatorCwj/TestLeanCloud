package com.testleancloud;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.adapter.SiftCityAdapter;
import com.base.BaseActivity;
import com.model.CitySiftModel;
import com.util.MockData;
import com.util.Utils;
import com.widget.indexableListView.IndexableListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_index_list_view)
public class IndexListViewActivity extends BaseActivity {

    @InjectView(R.id.indexLV)
    private IndexableListView listView;

    private SiftCityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<CitySiftModel> cities = MockData.getCities();
        Collections.sort(cities, new Comparator<CitySiftModel>() {
            @Override
            public int compare(CitySiftModel lhs, CitySiftModel rhs) {
                String left = String.valueOf(lhs.getPinyin().charAt(0)).toLowerCase();
                String right = String.valueOf(rhs.getPinyin().charAt(0)).toLowerCase();
                return left.compareTo(right);
            }
        });
        adapter = new SiftCityAdapter(this, cities);
        listView.setAdapter(adapter);
        listView.setFastScrollEnabled(true);
        listView.showIndexScrollerAlwas();
    }

    @Override
    protected void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utils.showToast(IndexListViewActivity.this, adapter.getItem(position).getName());
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
