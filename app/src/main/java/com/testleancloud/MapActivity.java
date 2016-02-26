package com.testleancloud;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.base.BaseActivity;
import com.model.Place;
import com.util.LocationUtils;
import com.util.Utils;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_map)
public class MapActivity extends BaseActivity implements OnGetRoutePlanResultListener {

    @InjectView(R.id.map)
    private MapView mapView;

    @InjectView(R.id.locate)
    private Button locate;

    @InjectView(R.id.tranRoute)
    private Button tranRoute;

    @InjectView(R.id.driveRoute)
    private Button driveRoute;

    @InjectView(R.id.walkRoute)
    private Button walkRoute;

    @InjectView(R.id.transitButton)
    private Button transitButton;

    RoutePlanSearch search;

    private BaiduMap map;
    private List<Marker> placeMarkers = new ArrayList<>();

    private PlanNode startLoc;
    private PlanNode endLoc;

    private ArrayList<TransitRouteLine> routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        map = mapView.getMap();
        mapView.showZoomControls(false);
        search = RoutePlanSearch.newInstance();
        search.setOnGetRoutePlanResultListener(this);

        //定位自身
        locateMySelf();

        //获取place
        refresh();

        //点击监听
        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getExtraInfo() != null) {
                    Place place = marker.getExtraInfo().getParcelable("place");
                    if (place != null) {
                        Utils.showToast(MapActivity.this, place.getName());
                    }
                }
                return false;
            }
        });
    }

    private void refresh() {
        showLoadingDialog("正在获取...");
        AVQuery<Place> query = AVQuery.getQuery(Place.class);
        query.findInBackground(new FindCallback<Place>() {
            @Override
            public void done(List<Place> list, AVException e) {
                if (e == null) {
                    addOverlay(list);
                } else {
                    Utils.showToast(MapActivity.this, "获取失败");
                }
                cancelLoadingDialog();
            }
        });
    }

    private void addOverlay(List<Place> list) {
        if (list == null)
            return;
        //清空之前的marker
        for (Marker marker : placeMarkers) {
            marker.remove();
        }
        placeMarkers.clear();
        //加入新的
        for (int i = 0; i < list.size(); ++i) {
            Place place = list.get(i);
            Button button = new Button(this);
            button.setBackgroundColor(Color.BLACK);
            button.setTextColor(Color.WHITE);
            button.setText(place.getName());
            BitmapDescriptor bd = BitmapDescriptorFactory.fromView(button);
            MarkerOptions options = new MarkerOptions().position(new LatLng(place.getLocation().getLatitude(), place.getLocation().getLongitude()))
                    .icon(bd).animateType(MarkerOptions.MarkerAnimateType.grow);
            Bundle bundle = new Bundle();
            bundle.putParcelable("place", place);
            options.extraInfo(bundle);
            Marker marker = (Marker) map.addOverlay(options);
            placeMarkers.add(marker);
        }
        //导航点
        LatLng start = new LatLng(list.get(0).getLocation().getLatitude(), list.get(0).getLocation().getLongitude());
        LatLng end = new LatLng(list.get(list.size() - 1).getLocation().getLatitude(), list.get(list.size() - 1).getLocation().getLongitude());
        startLoc = PlanNode.withLocation(start);
        endLoc = PlanNode.withLocation(end);
    }

    private void locateMySelf() {
        LocationUtils.requestLocation(this, new LocationUtils.OnLocationListener() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onSuccess(BDLocation location) {
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_start);
                OverlayOptions options = new MarkerOptions().position(ll).icon(bitmapDescriptor).animateType(MarkerOptions.MarkerAnimateType.grow).title("start");
                map.addOverlay(options);
                map.setMapStatus(MapStatusUpdateFactory.newLatLngZoom(ll, 14));//设为中心且进行一定缩放
            }

            @Override
            public void onFailed() {
                Utils.showToast(MapActivity.this, "定位失败");
            }

            @Override
            public void onFinally() {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        search.destroy();
    }

    @Override
    protected void setListener() {
        locate.setOnClickListener(this);
        tranRoute.setOnClickListener(this);
        driveRoute.setOnClickListener(this);
        walkRoute.setOnClickListener(this);
        transitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.locate:
                refresh();
                break;
            case R.id.tranRoute:
                tranRoute();
                break;
            case R.id.driveRoute:
                driveRoute();
                break;
            case R.id.walkRoute:
                walkRoute();
                break;
            case R.id.transitButton:
                toTransit();
                break;
        }
    }

    private void toTransit() {
        if (routes == null)
            return;
        Intent intent = new Intent(MapActivity.this, RoutesActivity.class);
        intent.putParcelableArrayListExtra(RoutesActivity.KEY, routes);
        startActivity(intent);
    }

    private void walkRoute() {
        search.walkingSearch(new WalkingRoutePlanOption().from(startLoc).to(endLoc));
    }

    private void driveRoute() {
        search.drivingSearch(new DrivingRoutePlanOption().from(startLoc).to(endLoc));
    }

    private void tranRoute() {
        search.transitSearch(new TransitRoutePlanOption().from(startLoc).city("北京").to(endLoc));
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            return;
        }
        WalkingRouteLine route = result.getRouteLines().get(0);
        WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(map);
        map.setOnMarkerClickListener(overlay);
        overlay.setData(route);
        overlay.addToMap();
        overlay.zoomToSpan();
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            return;
        }
        TransitRouteLine route = result.getRouteLines().get(0);
        TransitRouteOverlay overlay = new MyTransitRouteOverlay(map);
        map.setOnMarkerClickListener(overlay);
        overlay.setData(route);
        overlay.addToMap();
        overlay.zoomToSpan();
        routes = (ArrayList<TransitRouteLine>) result.getRouteLines();
    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            return;
        }
        DrivingRouteLine route = result.getRouteLines().get(0);
        DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(map);
        map.setOnMarkerClickListener(overlay);
        overlay.setData(route);
        overlay.addToMap();
        overlay.zoomToSpan();
    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult result) {
    }

    private class MyTransitRouteOverlay extends TransitRouteOverlay {

        public MyTransitRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_start);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_end);
        }
    }

    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_start);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_end);
        }
    }

    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_start);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_end);
        }
    }
}
