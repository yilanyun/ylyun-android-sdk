package com.yilan.sdk.sdkdemo.ad.interstitial;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.sdk.common.util.ToastUtil;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.sdkdemo.ad.BaseAdActivity;
import com.yilan.sdk.sdkdemo.util.Utils;
import com.yilan.sdk.ylad.YLAdListener;
import com.yilan.sdk.ylad.constant.YLAdConstants;
import com.yilan.sdk.ylad.engine.IYLAdEngine;
import com.yilan.sdk.ylad.entity.AdState;
import com.yilan.sdk.ylad.manager.YLAdManager;

public class InterstitialAdActivity extends BaseAdActivity {
    String adName = "插屏广告";

    IYLAdEngine interstitialAd;
    ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_ad);
        container = findViewById(R.id.root_container);
        interstitialAd = YLAdManager.with(this).getEngine(YLAdConstants.AdName.H5_INTERSTITIAL, "");
    }

    public void requestAndShow(View view) {
        ToastUtil.show(context, "开始请求"+adName);
        dialog.show();
        interstitialAd.reset();
        interstitialAd.setAdListener(listener);
        interstitialAd.request(container);
    }

    public void request(View view) {
        ToastUtil.show(context, "开始请求"+adName);
        dialog.show();
        interstitialAd.reset();
        interstitialAd.setAdListener(listener);
        interstitialAd.preRequest(container);
    }

    public void render(View view) {
        if (succeed && interstitialAd.getState() == AdState.SUCCESS) {
            interstitialAd.renderAd();
        } else {
            ToastUtil.show(context, String.format("还没有请求%s或请求失败，请尝试重新请求",adName));
        }
    }

    YLAdListener listener = new YLAdListener() {
        @Override
        public void onSuccess(String adType, int source, String reqId, String pid) {
            super.onSuccess(adType, source, reqId, pid);
            succeed = true;
            dialog.dismiss();
            ToastUtil.show(context, "广告请求成功：来源：" + Utils.getSourceName(source));
        }

        @Override
        public void onError(String adType, int source, String reqId, int code, String msg, String pid) {
            super.onError(adType, source, reqId, code, msg, pid);
            succeed = false;
            dialog.dismiss();
            ToastUtil.show(context, "广告请求失败！");
        }



        @Override
        public void onClick(String adType, int source, String reqId, String pid) {
            super.onClick(adType, source, reqId, pid);
            ToastUtil.show(context, "广告点击");
        }

        @Override
        public void onSkip(String adType, int source, String reqId, String pid) {
            super.onSkip(adType, source, reqId, pid);
            ToastUtil.show(context, "广告跳过");
        }

        @Override
        public void onTimeOver(String adType, int source, String reqId, String pid) {
            super.onTimeOver(adType, source, reqId, pid);
            ToastUtil.show(context, "倒计时完毕");

        }

        @Override
        public void onVideoStart(String adType, int source, String reqId, String pid) {
            super.onVideoStart(adType, source, reqId, pid);
            ToastUtil.show(context, "视频播放开始");
        }

        @Override
        public void onAdEmpty(String adType, int source, String reqId, String pid) {
            super.onAdEmpty(adType, source, reqId, pid);
            dialog.dismiss();
            ToastUtil.show(context, "广告为空，请先在一览云后台配置该广告！");
        }
    };
}