package com.yilan.sdk.sdkdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.yilan.sdk.common.util.YLUIUtil;
import com.yilan.sdk.sdkdemo.demo.ConfigFragment;

public class ConfigActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        ConfigFragment fragment = ConfigFragment.newInstance();
        YLUIUtil.FragmentOperate.with(getSupportFragmentManager())
                .replace(R.id.fragment_container, fragment);
    }

}
