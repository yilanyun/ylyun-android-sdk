package com.yilan.sdk.sdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yilan.sdk.common.util.YLUIUtil;
import com.yilan.sdk.ui.little.channel.YLLittleChannelFragment;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        YLUIUtil.FragmentOperate.with(getSupportFragmentManager()).replace(R.id.fragment_container,new YLLittleChannelFragment());
    }

}
