package com.example.yu.xieyu20190120.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.EventLog;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.yu.xieyu20190120.Apis;
import com.example.yu.xieyu20190120.BaseActivity;
import com.example.yu.xieyu20190120.R;
import com.example.yu.xieyu20190120.adapter.ViewPagerAdapter;
import com.example.yu.xieyu20190120.bean.DetailBean;
import com.example.yu.xieyu20190120.presenter.IpresenterImpl;
import com.example.yu.xieyu20190120.view.Iview;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

public class DetailActivity extends BaseActivity implements Iview {

    @BindView(R.id.detail_simple)
    SimpleDraweeView detailSimple;
    @BindView(R.id.detail_viewpager)
    ViewPager detailViewpager;
    @BindView(R.id.detail_title)
    TextView detailTitle;
    @BindView(R.id.detail_price)
    TextView detailPrice;
    private IpresenterImpl ipresenter;
    private int i;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            detailViewpager.setCurrentItem(i);
            i++;
            handler.sendEmptyMessageDelayed(0,3000);
        }
    };

    @Override
    protected void initData() {
        //获取传过来的pid
        Intent intent = getIntent();
        int pid = intent.getIntExtra("pid", 0);
        Map<String,String> map=new HashMap<>();
        map.put("pid",pid+"");
        //请求数据
        ipresenter.onRequestStart(Apis.DETAIl,map,DetailBean.class);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        ipresenter=new IpresenterImpl(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_detail;
    }

    @OnTouch(R.id.detail_viewpager)
    public boolean setViewpager(MotionEvent event){
        //viewpager按下不能轮播
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            handler.removeMessages(0);
        }else{
            handler.sendEmptyMessageDelayed(i,3000);
        }
        return true;
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof DetailBean) {
            DetailBean detailBean = (DetailBean) data;
            List<String> list=new ArrayList<>();
            String[] split = detailBean.getData().getImages().split("\\|");
            for (int i=0;i<split.length;i++){
                list.add(split[i]);
            }
            ViewPagerAdapter pagerAdapter=new ViewPagerAdapter(DetailActivity.this,list);
            detailViewpager.setAdapter(pagerAdapter);
            i=detailViewpager.getCurrentItem();
            handler.sendEmptyMessage(i);
            detailTitle.setText(detailBean.getData().getTitle());
            detailPrice.setText("¥"+detailBean.getData().getPrice());
            Uri uri=Uri.parse(detailBean.getSeller().getIcon());
            detailSimple.setImageURI(uri);
        }
    }

    @Override
    public void onFail(String error) {
        //打印错误信息
        Log.i("TAG", error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁
        ipresenter.onDetach();
        handler.removeMessages(0);
    }
}
