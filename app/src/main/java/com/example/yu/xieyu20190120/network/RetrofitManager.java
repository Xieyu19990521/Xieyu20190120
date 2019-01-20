package com.example.yu.xieyu20190120.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager {
    private static RetrofitManager retrofitManager;
    private BaseApis baseApis;
    private final String BASE_URL="http://www.zhaoapi.cn/product/";
    public static synchronized RetrofitManager getInstance(){
        if(retrofitManager==null){
            retrofitManager=new RetrofitManager();
        }
        return retrofitManager;
    }
    private RetrofitManager(){
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.addInterceptor(interceptor)
                .readTimeout(15,TimeUnit.SECONDS)
                .connectTimeout(15,TimeUnit.SECONDS)
                .writeTimeout(15,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        OkHttpClient okHttpClient=builder.build();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        baseApis=retrofit.create(BaseApis.class);
    }

    //post请求数据
    public void post(String url, Map<String,String> map,HttpListener listener){
        if(map==null){
            map=new HashMap<>();
        }
        baseApis.post(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    public Observer getObserver(final HttpListener listener){
        Observer observer=new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(listener!=null){
                    listener.onFaill(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    if(listener!=null){
                        listener.onSuccess(string);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if(listener!=null){
                        listener.onFaill(e.getMessage());
                    }
                }
            }
        };
        return observer;
    }

    public interface HttpListener{
        void onSuccess(String string);
        void onFaill(String error);
    }
}
