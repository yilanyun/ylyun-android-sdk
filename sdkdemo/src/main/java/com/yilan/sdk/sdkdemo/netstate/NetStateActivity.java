package com.yilan.sdk.sdkdemo.netstate;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.yilan.sdk.sdkdemo.R;

/**
 * Author And Date: liurongzhi on 2020/7/21.
 * Description: com.yilan.sdk.sdkdemo
 */
public class NetStateActivity extends Activity {
    TextView contentView;
    private NetStatePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netstate);
        this.presenter = new NetStatePresenter(this);
        initView();
        presenter.initData();
    }


    public void initView() {
        contentView = findViewById(R.id.text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
