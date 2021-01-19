package com.yilan.sdk.sdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;

import com.yilan.sdk.ylad.YLAdSimpleListener;
import com.yilan.sdk.ylad.constant.YLAdConstants;
import com.yilan.sdk.ylad.engine.IYLAdEngine;
import com.yilan.sdk.ylad.entity.YLAdEntity;
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
        splashEngine.setAdListener(new YLAdSimpleListener() {
            @Override
            public void onSuccess(int source, boolean type, YLAdEntity entity) {
                super.onSuccess(source, type, entity);
                System.out.println("-------onSuccess:" + type);
            }


            @Override
            public void onShow(int source, boolean type, YLAdEntity entity) {
                super.onShow(source, type, entity);
                System.out.println("-------onShow:" + type);
            }

            @Override
            public void onError(int source, YLAdEntity entity, int code, String msg) {
                super.onError(source, entity, code, msg);
                System.out.println("-------onError:" + msg);
                jumpToMain();
            }

            @Override
            public void onRenderError(int source, YLAdEntity entity, int code, String msg) {
                super.onRenderError(source, entity, code, msg);
                System.out.println("-------onRenderError:" + msg);
                jumpToMain();
            }

            @Override
            public void onSkip(int source, boolean type, YLAdEntity entity) {
                super.onSkip(source, type, entity);
                System.out.println("-------onSkip:");
                jumpToMain();
            }

            @Override
            public void onTimeOver(int source, boolean type, YLAdEntity entity) {
                super.onTimeOver(source, type, entity);
                System.out.println("-------onTimeOver:");
                jumpToMain();
            }

            @Override
            public void onClick(int source, boolean type, YLAdEntity entity) {
                super.onClick(source, type, entity);
            }

            @Override
            public void onAdEmpty(int source, boolean type, YLAdEntity entity) {
                super.onAdEmpty(source, type, entity);
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
