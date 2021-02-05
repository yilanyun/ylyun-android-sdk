package com.yilan.sdk.sdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;

import com.yilan.sdk.ylad.YLAdListener;
import com.yilan.sdk.ylad.constant.YLAdConstants;
import com.yilan.sdk.ylad.engine.IYLAdEngine;
import com.yilan.sdk.ylad.manager.YLAdManager;

/**
 * Author And Date: liurongzhi on 2020/1/20.
 * Description: com.yilan.sdk.sdkdemo
 */
public class SplashActivity extends FragmentActivity {
    private IYLAdEngine splashEngine;
    private ViewGroup spContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        spContainer = findViewById(R.id.sp_container);
        splashEngine = YLAdManager.with(this).getEngine(YLAdConstants.AdName.SPLASH);
        if (!splashEngine.hasAd()) {
            spContainer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jumpToMain();
                }
            }, 2000);
            return;
        }
        splashEngine.setAdListener(new YLAdListener() {
            @Override
            public void onSuccess(String adType, int source, String reqId, String pid) {
                super.onSuccess(adType, source, reqId, pid);
                System.out.println("-------onSuccess:" + adType);
            }

            @Override
            public void onError(String adType, int source, String reqId, int code, String msg, String pid) {
                super.onError(adType, source, reqId, code, msg, pid);
                System.out.println("-------onError:" + msg);
                jumpToMain();
            }

            @Override
            public void onRenderError(String adType, int source, String reqId, int code, String msg, String pid) {
                super.onRenderError(adType, source, reqId, code, msg, pid);
                System.out.println("-------onRenderError:" + msg);
                jumpToMain();
            }

            @Override
            public void onShow(String adType, int source, String reqId, String pid) {
                super.onShow(adType, source, reqId, pid);
                System.out.println("-------onShow:" + adType);
            }

            @Override
            public void onSkip(String adType, int source, String reqId, String pid) {
                super.onSkip(adType, source, reqId, pid);
                System.out.println("-------onSkip:");
                jumpToMain();
            }

            @Override
            public void onTimeOver(String adType, int source, String reqId, String pid) {
                super.onTimeOver(adType, source, reqId, pid);
                System.out.println("-------onTimeOver:");
                jumpToMain();
            }

            @Override
            public void onAdEmpty(String adType, int source, String reqId, String pid) {
                super.onAdEmpty(adType, source, reqId, pid);
                jumpToMain();
            }
        });
        splashEngine.request(spContainer);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashEngine.onDestroy();
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
        } else {
            SplashActivity.this.startActivity(new Intent(this, MainPagerActivity.class));
        }

    }
}
