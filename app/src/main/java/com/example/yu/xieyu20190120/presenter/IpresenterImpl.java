package com.example.yu.xieyu20190120.presenter;

import com.example.yu.xieyu20190120.model.Imodelmpl;
import com.example.yu.xieyu20190120.mycallback.MyCallBack;
import com.example.yu.xieyu20190120.view.Iview;

import java.util.Map;

public class IpresenterImpl implements Ipresenter{

    Iview iview;
    Imodelmpl imodelmpl;

    public IpresenterImpl(Iview iview) {
        this.iview = iview;
        imodelmpl=new Imodelmpl();
    }

    @Override
    public void onRequestStart(String url, Map<String, String> map, Class clas) {
        imodelmpl.onStartRequest(url, map, clas, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iview.onSuccess(data);
            }

            @Override
            public void onFail(String error) {
                iview.onFail(error);
            }
        });
    }
    public void onDetach(){
        if(iview!=null){
            iview=null;
        }
        if(imodelmpl!=null){
            imodelmpl=null;
        }
    }
}
