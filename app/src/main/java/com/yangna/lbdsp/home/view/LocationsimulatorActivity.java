package com.yangna.lbdsp.home.view;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.locationsimulator.LocationListener;
import com.yangna.lbdsp.common.locationsimulator.LocationUtil;
import com.yangna.lbdsp.common.locationsimulator.MyMapOnclickListener;
import com.yangna.lbdsp.common.locationsimulator.MyOnGetSuggestionResultListener;
import com.yangna.lbdsp.common.locationsimulator.MyOnQueryTextListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;

import butterknife.ButterKnife;
import butterknife.OnClick;

/* 虚拟GPS定位 */
public class LocationsimulatorActivity extends AppCompatActivity {

    private boolean IS_MOCK_SERVICE_START = false;
    private Button beginMock, stopMock, moveToLocation;
    private String city = "南宁";//写南宁无效
    private LatLng selected_latlng;//被模拟的定位
    private LatLng current_latlng;//用户当前实际位置
    //百度地图相关
    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    //搜索相关
    private SearchView searchView;
    private ListView search_result_lv;
    private PoiSearch poiSearch;
    private SuggestionSearch suggestionSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationsimulator);
        ButterKnife.bind(this);
        checkPermission();//检查权限

        beginMock = findViewById(R.id.begin_button);//开始定位 按钮
        stopMock = findViewById(R.id.stop_button);//结束定位 按钮
        moveToLocation = findViewById(R.id.move_to_location);//当前位置 按钮
        search_result_lv = findViewById(R.id.search_result);//搜索结果 页面
        searchView = findViewById(R.id.search_view);//透明搜索框

        /* 初始化搜索相关 地图图层布局*/
        mapView = findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        locationClient = new LocationClient(this);
        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setIsNeedAddress(true);
        option.setScanSpan(1001);
        locationClient.setLocOption(option);
        LocationListener locationListener = new LocationListener(baiduMap, mapView, this);
        locationClient.registerLocationListener(locationListener);
        locationClient.start();
        baiduMap.setOnMapClickListener(new MyMapOnclickListener(baiduMap, this));
        poiSearch = PoiSearch.newInstance();
        suggestionSearch = SuggestionSearch.newInstance();

        /* 初始化多个监听器 */
        /* 百度地图搜索建议监听 */
        suggestionSearch.setOnGetSuggestionResultListener(new MyOnGetSuggestionResultListener(baiduMap, search_result_lv, LocationsimulatorActivity.this));
        /* 安卓X系统自带的SearchView控件监听 */
        searchView.setOnQueryTextListener(new MyOnQueryTextListener(searchView, LocationsimulatorActivity.this, suggestionSearch, poiSearch));
        /* 开始定位 监听 */
        beginMock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGpsOpened()) {
                    showGpsDialog();
                } else {
                    if (!isMockSwithOn(MockLocationService.LOCATION_MANAGER)) {
                        Toast.makeText(LocationsimulatorActivity.this, "请打开开发者模式，并将此应用设置为模拟定位应用", Toast.LENGTH_SHORT).show();
                    } else {
                        if (selected_latlng == null) {
                            Toast.makeText(LocationsimulatorActivity.this, "请先在地图上选择目标地点", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LocationsimulatorActivity.this, "开始模拟位置", Toast.LENGTH_SHORT).show();
                            Intent startMockServiceIntent = new Intent(LocationsimulatorActivity.this, MockLocationService.class);
                            Bundle bundle = new Bundle();
                            double[] loc = LocationUtil.gcj02_To_Gps84(selected_latlng.latitude, selected_latlng.longitude);
                            bundle.putDouble("LATITUDE", loc[0]);
                            bundle.putDouble("LONGITUDE", loc[1]);
                            startMockServiceIntent.putExtra(MockLocationService.INPUT_KEY, bundle);
                            startService(startMockServiceIntent);
                            beginMock.setVisibility(View.INVISIBLE);
                            stopMock.setVisibility(View.VISIBLE);
                            IS_MOCK_SERVICE_START = true;
                        }
                    }
                }
            }
        });
        /* 停止定位 监听 */
        stopMock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IS_MOCK_SERVICE_START) {
                    Intent stopMockServiceIntent = new Intent(LocationsimulatorActivity.this, MockLocationService.class);
                    stopService(stopMockServiceIntent);
                    Toast.makeText(LocationsimulatorActivity.this, "停止位置模拟", Toast.LENGTH_SHORT).show();
                    IS_MOCK_SERVICE_START = false;
                    baiduMap.clear();
                    beginMock.setVisibility(View.VISIBLE);
                    stopMock.setVisibility(View.INVISIBLE);
                    setSelected_latlng(null);
                    baiduMap.clear();
                }
            }
        });
        /* 当前位置 监听 点了之后可以拿到经纬度坐标 latitude: 24.28708, longitude: 109.38956 */
        moveToLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_latlng != null) {
                    MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(current_latlng);
                    baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(20).build()));
                    baiduMap.animateMapStatus(mapStatusUpdate);
                    /* 准备做 按距离查询店铺列表功能 /tWebshopShop/distanceList */
                }
            }
        });
        /* 搜索结果列表监听 */
        search_result_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String lng = ((TextView) view.findViewById(R.id.poi_longitude)).getText().toString();
                String lat = ((TextView) view.findViewById(R.id.poi_latitude)).getText().toString();
                LatLng latLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
                setSelected_latlng(latLng);
                search_result_lv.setVisibility(View.GONE);
                searchView.clearFocus();
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.animateMapStatus(mapStatusUpdate);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(20).build()));
            }
        });
    }

    @OnClick(R.id.dingwei_back)
    public void doBack(View view) {
        finish();
    }

    //判断手机是否选择了使用当前应用为模拟位置应用
    public boolean isMockSwithOn(LocationManager locationManager) {
        boolean canMockPosition = false;
        try {
            String providerStr = LocationManager.GPS_PROVIDER;
            try {
                locationManager.removeTestProvider(LocationManager.GPS_PROVIDER);
            } catch (Exception e) {
                Log.e("删除测试提供者失败", e.getMessage());
            }
            LocationProvider provider = locationManager.getProvider(providerStr);
            if (provider != null) {
                try {
                    locationManager.addTestProvider(provider.getName(), provider.requiresNetwork(), provider.requiresSatellite(), provider.requiresCell(), provider.hasMonetaryCost(), provider.supportsAltitude(), provider.supportsSpeed(), provider.supportsBearing(), provider.getPowerRequirement(), provider.getAccuracy());
                } catch (SecurityException e) {
                    return false;
                }
            } else {
                locationManager.addTestProvider(providerStr, true, true, false, false, true, true, true, Criteria.POWER_HIGH, Criteria.ACCURACY_FINE);
            }
            locationManager.setTestProviderEnabled(providerStr, true);
            locationManager.setTestProviderStatus(providerStr, LocationProvider.AVAILABLE, null, System.currentTimeMillis());

            // 模拟位置可用
            canMockPosition = true;
            locationManager.setTestProviderEnabled(providerStr, false);
            locationManager.removeTestProvider(providerStr);
        } catch (Exception e) {
            Log.e("error", e.toString());
        }
        return canMockPosition;
    }

    /* 检查权限 */
    private void checkPermission() {
        MockLocationService.LOCATION_MANAGER = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        final String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        List<String> ungrantedPermisionList = new ArrayList<String>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(LocationsimulatorActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
                ungrantedPermisionList.add(permission);
            }
        }
        if (!ungrantedPermisionList.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(ungrantedPermisionList.toArray(new String[ungrantedPermisionList.size()]), 1);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(LocationsimulatorActivity.this, "请同意使用所有权限,否则无法正常使用...", Toast.LENGTH_LONG).show();
//                finish();
            }
        }
    }

//    private void initPoiSearch() {
//        poiSearch = PoiSearch.newInstance();
//        //poiSearch.setOnGetPoiSearchResultListener(new MyOnGetPoiSearchResultListener());
//    }


//    private void initSuggestionSearch() {
//        suggestionSearch = SuggestionSearch.newInstance();
//    }

    //判断GPS是否打开
    private boolean isGpsOpened() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void showGpsDialog() {
        new AlertDialog.Builder(LocationsimulatorActivity.this)
                .setTitle("Tips")//这里是表头的内容
                .setMessage("是否开启GPS定位服务?")//这里是中间显示的具体信息
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(intent, 0);
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                .show();
    }

    public void setSelected_latlng(LatLng selected_latlng) {
        this.selected_latlng = selected_latlng;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCurrent_latlng(LatLng current_latlng) {
        this.current_latlng = current_latlng;
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        poiSearch.destroy();
        suggestionSearch.destroy();
        String providerName = LocationManager.GPS_PROVIDER;
        try {
            if (MockLocationService.LOCATION_MANAGER.isProviderEnabled(providerName) || (null != MockLocationService.LOCATION_MANAGER.getProvider(providerName))) {
                MockLocationService.LOCATION_MANAGER.removeTestProvider(providerName);
            }
        } catch (Exception e) {
            Log.e("删除提供者 " + providerName + " 失败 :", e.getMessage());
        }
    }

}
