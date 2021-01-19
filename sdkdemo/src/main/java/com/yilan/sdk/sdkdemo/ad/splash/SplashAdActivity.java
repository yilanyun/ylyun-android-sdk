package com.yilan.sdk.sdkdemo.ad.splash;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.sdk.common.util.ToastUtil;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.sdkdemo.ad.BaseAdActivity;
import com.yilan.sdk.sdkdemo.util.Utils;
import com.yilan.sdk.ylad.YLAdSimpleListener;
import com.yilan.sdk.ylad.YLInnerAdListener;
import com.yilan.sdk.ylad.constant.YLAdConstants;
import com.yilan.sdk.ylad.engine.IYLAdEngine;
import com.yilan.sdk.ylad.entity.AdState;
import com.yilan.sdk.ylad.entity.YLAdEntity;
import com.yilan.sdk.ylad.manager.YLAdManager;

public class SplashAdActivity extends BaseAdActivity {

    IYLAdEngine splashAd;
    ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_ad);
        container = findViewById(R.id.root_container);
        splashAd = YLAdManager.with(this).getEngine(YLAdConstants.AdName.SPLASH, "");
    }

    public void requestSplashAndShow(View view) {
        ToastUtil.show(context, "开始请求");
        dialog.show();
        splashAd.reset();
        splashAd.setAdListener(listener);
        splashAd.request(container);
    }

    public void requestSplash(View view) {
        ToastUtil.show(context, "开始请求");
        dialog.show();
        splashAd.reset();
        splashAd.setAdListener(listener);
        splashAd.preRequest(container);
    }

    public void renderSplash(View view) {
        if (succeed && splashAd.getState() == AdState.SUCCESS) {
            splashAd.renderAd();
        } else {
            ToastUtil.show(context, "还没有请求开屏广告或请求失败，请尝试重新请求");
        }
    }

    YLInnerAdListener listener = new YLAdSimpleListener() {


        @Override
        public void onSuccess(int source, boolean type, YLAdEntity entity) {
            super.onSuccess(source, type, entity);
            succeed = true;
            dialog.dismiss();
            ToastUtil.show(context, "广告请求成功：来源：" + Utils.getSourceName(source));
        }

        @Override
        public void onError(int source, YLAdEntity entity, int code, String msg) {
            super.onError(source, entity, code, msg);
            succeed = false;
            dialog.dismiss();
            ToastUtil.show(context, "广告请求失败！");
        }

        @Override
        public void onClick(int source, boolean type, YLAdEntity entity) {
            super.onClick(source, type, entity);
            ToastUtil.show(context, "广告点击");
        }

        @Override
        public void onSkip(int source, boolean type, YLAdEntity entity) {
            super.onSkip(source, type, entity);
            ToastUtil.show(context, "广告跳过");
        }

        @Override
        public void onTimeOver(int source, boolean type, YLAdEntity entity) {
            super.onTimeOver(source, type, entity);
            ToastUtil.show(context, "倒计时完毕");

        }

        @Override
        public void onVideoStart(int source, boolean type, YLAdEntity entity) {
            super.onVideoStart(source, type, entity);
            ToastUtil.show(context, "视频播放开始");
        }

        @Override
        public void onAdEmpty(int source, boolean type, YLAdEntity entity) {
            super.onAdEmpty(source, type, entity);
            dialog.dismiss();
            ToastUtil.show(context, "广告为空，请先在一览云后台配置该广告！");
        }
    };
}