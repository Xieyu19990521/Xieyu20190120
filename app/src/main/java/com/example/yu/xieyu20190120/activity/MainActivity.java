package com.example.yu.xieyu20190120.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.example.yu.xieyu20190120.Apis;
import com.example.yu.xieyu20190120.BaseActivity;
import com.example.yu.xieyu20190120.MyApplication;
import com.example.yu.xieyu20190120.R;
import com.example.yu.xieyu20190120.adapter.RecyclerAdapter;
import com.example.yu.xieyu20190120.bean.DaoBean;
import com.example.yu.xieyu20190120.bean.ProductBean;
import com.example.yu.xieyu20190120.greendao.DaoMaster;
import com.example.yu.xieyu20190120.greendao.DaoSession;
import com.example.yu.xieyu20190120.presenter.IpresenterImpl;
import com.example.yu.xieyu20190120.view.Iview;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements Iview {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private IpresenterImpl ipresenter;
    private RecyclerAdapter recyclerAdapter;
    private DaoMaster.DevOpenHelper openHelper;
    private DaoSession daoSession;
    private DaoMaster daoMaster;
    private SQLiteDatabase database;

    @Override
    protected void initData() {
        //请求数据
        Map<String,String> map=new HashMap<>();
        map.put("keywords","笔记本");
        map.put("page",1+"");
        ipresenter.onRequestStart(Apis.PRODUCT,map,ProductBean.class);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        ipresenter = new IpresenterImpl(this);
        //加载greendao
        setGreenDao();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new RecyclerAdapter(this);
        recycler.setAdapter(recyclerAdapter);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.button)
    public void setButtonClick(){
        //点击跳转
        Intent intent=new Intent(MainActivity.this,MapActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof ProductBean) {
            ProductBean productBean= (ProductBean) data;
            recyclerAdapter.setList(productBean.getData());
            for(int i=0;i<productBean.getData().size();i++){
                //把获取到数据保存到数据库中
                ProductBean.Data data1 = productBean.getData().get(i);
                DaoBean daoBean = new DaoBean(data1.getImages(), data1.getPid(), data1.getPrice(), data1.getTitle());
                daoSession.insertOrReplace(daoBean);
            }
        }
    }

    @Override
    public void onFail(String error) {
        Log.i("TAG", error);
        if(error.equals("网络出错，请检查网络状态")){
            //如果网络错误 那就展示数据库的数据
            List<ProductBean.Data> list=new ArrayList<>();
            QueryBuilder<DaoBean> daoBeanQueryBuilder = daoSession.queryBuilder(DaoBean.class);
            Query<DaoBean> build = daoBeanQueryBuilder.build();
            List<DaoBean> list1 = build.list();
            for (int i=0;i<list1.size();i++){
                DaoBean daoBean = list1.get(i);
                ProductBean.Data data = new ProductBean.Data(daoBean.getImages(), (int) daoBean.getPid(), daoBean.getPrice(), daoBean.getTitle());
                list.add(data);
            }
            recyclerAdapter.setList(list);
        }
    }
    public void setGreenDao(){
        //greendao 实例化
        openHelper=new DaoMaster.DevOpenHelper(this,"product-db",null);
        database=openHelper.getWritableDatabase();
        daoMaster=new DaoMaster(database);
        daoSession=daoMaster.newSession();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ipresenter.onDetach();
    }
}
