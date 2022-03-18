package com.yilan.sdk.sdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;

import com.yilan.sdk.sdkdemo.demo.DemoActivity;

/**
 * Author And Date: liurongzhi on 2020/1/20.
 * Description: com.yilan.sdk.sdkdemo
 */
public class SplashActivity extends FragmentActivity {
    private ViewGroup spContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        spContainer = findViewById(R.id.sp_container);
        spContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpToMain();
            }
        },500);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean isFinish = false;

    private void jumpToMain() {
        SplashActivity.this.finish();
        if (isFinish) return;
        isFinish = true;
        if (BuildConfig.buildType == 0) {
            SplashActivity.this.startActivity(new Intent(this, MainDemoActivity.class));
        } else if (BuildConfig.buildType == 1) {
            SplashActivity.this.startActivity(new Intent(this, MainActivity.class));
        } else if (BuildConfig.buildType == 2) {
            SplashActivity.this.startActivity(new Intent(this, MainPagerActivity.class));
        } else if (BuildConfig.buildType == 3) {
            DemoActivity.startHybridFeedDemo(this);
        } else {
            SplashActivity.this.startActivity(new Intent(this, MainActivity.class));
        }
    }
}
