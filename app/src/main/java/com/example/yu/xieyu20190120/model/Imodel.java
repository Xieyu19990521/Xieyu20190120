package com.example.yu.xieyu20190120.model;

import com.example.yu.xieyu20190120.mycallback.MyCallBack;

import java.util.Map;

public interface Imodel {
    void onStartRequest(String url, Map<String,String> map, Class cals, MyCallBack myCallBack);
}
