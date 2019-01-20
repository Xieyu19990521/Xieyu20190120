package com.example.yu.xieyu20190120.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.yu.xieyu20190120.BaseActivity;
import com.example.yu.xieyu20190120.R;

public class MapActivity extends BaseActivity {
    MapView mMapView = null;
    private AMap aMap;
    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //找 id
        mMapView = findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(2000);
        //设置小蓝点样式
        aMap.setMyLocationStyle(myLocationStyle);
        //显示我的位置
        aMap.setMyLocationEnabled(true);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
}
