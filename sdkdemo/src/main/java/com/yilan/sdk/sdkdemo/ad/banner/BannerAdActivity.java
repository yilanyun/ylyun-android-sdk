package com.yilan.sdk.sdkdemo.ad.banner;

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

public class BannerAdActivity extends BaseAdActivity {

    IYLAdEngine bannerAd;
    IYLAdEngine expressBannerAd;
    ViewGroup bannerContainer;
    ViewGroup expressContainer;
    private YLAdManager adManager;
    boolean expressSucceed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_ad);
        bannerContainer = findViewById(R.id.banner_container);
        expressContainer = findViewById(R.id.express_banner_container);
        adManager = YLAdManager.with(this);
        bannerAd = adManager.getEngine(YLAdConstants.AdName.BANNER, "");
        expressBannerAd = adManager.getEngine(YLAdConstants.AdName.EXPRESS_BANNER, "");
    }

    public void requestBannerAndShow(View view) {
        ToastUtil.show(context, "开始请求");
        dialog.show();
        bannerAd.reset();
        bannerAd.setAdListener(listener);
        bannerAd.request(bannerContainer);
    }

    public void requestBanner(View view) {
        ToastUtil.show(context, "开始请求");
        dialog.show();
        bannerAd.reset();
        bannerAd.setAdListener(listener);
        bannerAd.preRequest(bannerContainer);
    }

    public void renderBanner(View view) {
        if (succeed && bannerAd.getState() == AdState.SUCCESS) {
            bannerAd.renderAd();
        } else {
            ToastUtil.show(context, "还没有请求banner广告或请求失败，请尝试重新请求");
        }
    }

    public void requestExpressAndShow(View view) {
        ToastUtil.show(context, "开始请求");
        dialog.show();
        expressBannerAd.reset();
        expressBannerAd.setAdListener(expressListener);
        expressBannerAd.request(expressContainer);
    }

    public void requestExpressBanner(View view) {
        ToastUtil.show(context, "开始请求");
        dialog.show();
        expressBannerAd.reset();
        expressBannerAd.setAdListener(expressListener);
        expressBannerAd.preRequest(expressContainer);
    }

    public void renderExpressBanner(View view) {
        if (expressSucceed && expressBannerAd.getState() == AdState.SUCCESS) {
            expressBannerAd.renderAd();
        } else {
            ToastUtil.show(context, "还没有请求express banner广告或请求失败，请尝试重新请求");
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

    YLAdListener expressListener = new YLAdListener() {

        @Override
        public void onSuccess(String adType, int source, String reqId, String pid) {
            super.onSuccess(adType, source, reqId, pid);
            expressSucceed = true;
            dialog.dismiss();
            ToastUtil.show(context, "广告请求成功：来源：" + Utils.getSourceName(source));
        }

        @Override
        public void onError(String adType, int source, String reqId, int code, String msg, String pid) {
            super.onError(adType, source, reqId, code, msg, pid);
            expressSucceed = false;
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