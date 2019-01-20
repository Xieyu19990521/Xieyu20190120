package com.example.yu.xieyu20190120.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.yu.xieyu20190120.MyApplication;
import com.example.yu.xieyu20190120.mycallback.MyCallBack;
import com.example.yu.xieyu20190120.network.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

public class Imodelmpl implements Imodel{
    @Override
    public void onStartRequest(String url, Map<String, String> map, final Class cals, final MyCallBack myCallBack) {
        if(!isNetWork()){
            myCallBack.onFail("网络出错，请检查网络状态");
        }else{
            RetrofitManager.getInstance().post(url, map, new RetrofitManager.HttpListener() {
                @Override
                public void onSuccess(String string) {
                    Object o = new Gson().fromJson(string, cals);
                    myCallBack.onSuccess(o);
                }

                @Override
                public void onFaill(String error) {
                    myCallBack.onFail(error);
                }
            });
        }

    }

    public boolean isNetWork(){
        ConnectivityManager connectivityManager= (ConnectivityManager) MyApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo.isAvailable();
    }
}
