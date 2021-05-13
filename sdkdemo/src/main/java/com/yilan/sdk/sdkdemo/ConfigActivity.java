package com.yilan.sdk.sdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yilan.sdk.common.util.YLUIUtil;
import com.yilan.sdk.sdkdemo.demo.ConfigFragment;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        ConfigFragment fragment = ConfigFragment.newInstance();
        YLUIUtil.FragmentOperate.with(getSupportFragmentManager())
                .replace(R.id.fragment_container, fragment);
    }

}
