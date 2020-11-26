package com.yilan.sdk.sdkdemo.ad;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yilan.sdk.sdkdemo.BuildConfig;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.sdkdemo.ad.reward.RewardVideoActivity;


public class ADTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_test);
        TextView sdkVersion = findViewById(R.id.sdk_version);
        sdkVersion.setText(String.format("SDK Version %s  ", BuildConfig.VERSION_NAME));
    }


    public void testReward(View view) {
        startAct(RewardVideoActivity.class);
    }

    public void testFull(View view) {
    }

    public void testFeedList(View view) {
    }

    public void testConfigFeedList(View view) {
    }

    public void testDrawAd(View view) {
    }

    private void startAct(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}