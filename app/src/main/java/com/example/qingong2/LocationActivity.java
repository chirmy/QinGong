package com.example.qingong2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.qingong2.R;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity {

    public LocationClient mLocationClient;

    private TextView positionText;

    private MapView mapView;

    private BaiduMap baiduMap;

    private boolean isFirstLocate = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //初始化
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_location);
        //创建实例
        Log.d("11111","laile");
        mLocationClient = new LocationClient(getApplicationContext());
        //获取到位置信息时会回调该定位监听器
        mLocationClient.registerLocationListener(new MyLocationListener());
        //绑定控件ID
        positionText = (TextView) findViewById(R.id.tv_location_position);
        //动态申请权限
        List<String> permissionList = new ArrayList<>();
        //依次判断三权限有没有被授权
        if(ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            //List转换成数组
            String[] permissions =permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(LocationActivity.this, permissions, 1);
        }
        else{
            requestLocation();
        }


        //获取实例
        mapView = (MapView)findViewById(R.id.mapVi_location_map);

        //显示当前位置地图 获取实例
        baiduMap = mapView.getMap();

        //把我显示在地图上
        baiduMap.setBaiduHeatMapEnabled(true);

    }

    private void navigateTo(BDLocation bdLocation){
        if(isFirstLocate){
//设置地图缩放级别和将地图移动到当前经纬度
            LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }

        //设备在地图上显示的位置应随着设备的移动而实时改变，执行多次
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(bdLocation.getLatitude());
        locationBuilder.longitude(bdLocation.getLongitude());

        MyLocationData myLocationData = locationBuilder.build();
        baiduMap.setMyLocationData(myLocationData);

    }

    private void requestLocation() {
        //开始定位，定位结果会回调到前面注册的监听器中
        mLocationClient.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0){
                    for(int result : grantResults){
                        if(result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this, "You must allow all the permissions", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }


    private void initLocation(){
        //创建对象
        LocationClientOption option = new LocationClientOption();
        //5秒更新
        option.setScanSpan(3000);
        //设置定位模式为传感器模式
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        //获取当前位置详细的位置信息
        option.setIsNeedAddress(true);


        mLocationClient.setLocOption(option);
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
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
            if(bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                navigateTo(bdLocation);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition = new StringBuilder();
                    //获取纬度
                    currentPosition.append("纬度：").append(bdLocation.getLatitude()).append("\n");
                    //获取精度
                    currentPosition.append("经度：").append(bdLocation.getLongitude()).append("\n");

                    //国家，省，市，区，街道
                    currentPosition.append("国家：").append(bdLocation.getCountry()).append("\n");
                    currentPosition.append("省：").append(bdLocation.getProvince()).append("\n");
                    currentPosition.append("市：").append(bdLocation.getCity()).append("\n");
                    currentPosition.append("区：").append(bdLocation.getDistrict()).append("\n");
                    currentPosition.append("街道：").append(bdLocation.getStreet()).append("\n");

                    currentPosition.append("定位方式：");
                    //获取定位方式
                    if(bdLocation.getLocType() == BDLocation.TypeGpsLocation){
                        currentPosition.append("GPS");
                    }else if(bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                        currentPosition.append("网络");
                    }
                    positionText.setText(currentPosition);
                }
            });
        }


    }


}