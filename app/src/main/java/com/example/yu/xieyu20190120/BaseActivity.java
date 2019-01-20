package com.example.yu.xieyu20190120;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        initView(savedInstanceState);
        initData();
    }

    protected abstract void initData();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract int setLayoutId();

}
