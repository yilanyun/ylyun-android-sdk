package com.yilan.sdk.sdkdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.yilan.sdk.common.util.ExecutorUtil;
import com.yilan.sdk.common.util.FSDevice;
import com.yilan.sdk.ui.ad.ylad.AdEngineFactory;
import com.yilan.sdk.ui.ad.ylad.engine.IYLAdEngine;
import com.yilan.sdk.ui.ad.ylad.engine.YLAdEngine;
import com.yilan.sdk.ui.ad.ylad.YLAdSimpleListener;
import com.yilan.sdk.ui.ad.constant.AdConstants;
import com.yilan.sdk.ui.ad.entity.AdEntity;
import com.yilan.sdk.ui.ad.ylad.view.AdFrameLayout;

/**
 * Author And Date: liurongzhi on 2020/1/20.
 * Description: com.yilan.sdk.sdkdemo
 */
public class SplashActivity extends Activity {
    private IYLAdEngine<AdFrameLayout> splashEngine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashEngine = AdEngineFactory.getInstance().createSplashEngine();
        if (!splashEngine.hasAd()) {
            jumpToMain();
            return;
        }
        ExecutorUtil.instance.executeInMain(new Runnable() {
            @Override
            public void run() {
                if (!isFinish && noAd) {
                    jumpToMain();
                }
            }
        }, 3000);
        AdFrameLayout rootView = findViewById(R.id.sp_container);
        splashEngine.setAdListener(new YLAdSimpleListener() {
            @Override
            public void onSuccess(int source, boolean type, View view, AdEntity entity) {
                super.onSuccess(source, type, view, entity);
                System.out.println("-------onSuccess:" + type);
                noAd = false;
            }

            @Override
            public void onShow(int source, boolean type, AdEntity entity) {
                super.onShow(source, type, entity);
                System.out.println("-------onShow:" + type);
            }

            @Override
            public void onError(int source, AdEntity entity, String msg) {
                super.onError(source, entity, msg);
                System.out.println("-------onError:" + msg);
                jumpToMain();
            }

            @Override
            public void onSkip(int source, boolean type, AdEntity entity) {
                super.onSkip(source, type, entity);
                System.out.println("-------onSkip:");
                jumpToMain();
            }

            @Override
            public void onTimeOver(int source, boolean type, AdEntity entity) {
                super.onTimeOver(source, type, entity);
                System.out.println("-------onTimeOver:");
                if (!isStop) {
                    jumpToMain();
                }
            }

            @Override
            public void onClick(int source, boolean type, AdEntity entity) {
                super.onClick(source, type, entity);
            }

            @Override
            public void onAdEmpty(int source, boolean type, AdEntity entity) {
                super.onAdEmpty(source, type, entity);
                jumpToMain();
            }
        });
        splashEngine.request(rootView);

    }

    private boolean isStop = false;

    @Override
    protected void onRestart() {
        super.onRestart();
        isStop = false;
        jumpToMain();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isStop = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashEngine.onDestroy();
    }

    private boolean isFinish = false;
    private boolean noAd = true;

    private void jumpToMain() {
        SplashActivity.this.finish();
        if (isFinish) return;
        isFinish = true;
        SplashActivity.this.startActivity(new Intent(this, MainActivity.class));
    }
}
