package com.testleancloud;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baidu.mapapi.search.route.TransitRouteLine;
import com.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_routes)
public class RoutesActivity extends BaseActivity {

    public static final String KEY = "routes";

    @InjectView(R.id.listView)
    private ListView listView;

    private ArrayAdapter<String> adapter;

    private ArrayList<TransitRouteLine> routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routes = getIntent().getParcelableArrayListExtra(KEY);
        if (routes == null)
            return;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, getRoutes(routes));
        listView.setAdapter(adapter);
    }

    private List<String> getRoutes(ArrayList<TransitRouteLine> routes) {
        List<String> contents = new ArrayList<>();
        for (TransitRouteLine route : routes) {
            StringBuilder sb = new StringBuilder("");
            List<TransitRouteLine.TransitStep> allStep = route.getAllStep();
            //基本信息
            sb.append(getTitle(allStep));
            sb.append(route.getDuration()).append("秒 | ").append(route.getDistance()).append("米 | 步行").append(getWalkDis(allStep)).append("米").append("\n\n\n\n");
            //每段信息
            for (TransitRouteLine.TransitStep step : allStep) {
                sb.append(getStepInfo(step)).append("\n\n");
            }
            contents.add(sb.toString());
        }
        return contents;
    }

    private String getTitle(List<TransitRouteLine.TransitStep> steps) {
        StringBuilder sb = new StringBuilder("");
        for (TransitRouteLine.TransitStep step : steps) {
            if (step.getVehicleInfo() != null) {
                sb.append(step.getVehicleInfo().getTitle()).append("-");
            }
        }
        return sb.toString();
    }

    private String getStepInfo(TransitRouteLine.TransitStep step) {
        StringBuilder sb = new StringBuilder("");
        sb.append(step.getInstructions());
        return sb.toString();
    }

    private int getWalkDis(List<TransitRouteLine.TransitStep> allStep) {
        int count = 0;
        for (TransitRouteLine.TransitStep step : allStep) {
            if (step.getStepType() == TransitRouteLine.TransitStep.TransitRouteStepType.WAKLING)
                count += step.getDistance();
        }
        return count;
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
