package com.yilan.sdk.sdkdemo.ad.banner;

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

public class BannerAdActivity extends BaseAdActivity {

    IYLAdEngine bannerAd;
    IYLAdEngine expressBannerAd;
    ViewGroup bannerContainer;
    ViewGroup expressContainer;

    boolean expressSucceed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_ad);
        bannerContainer = findViewById(R.id.banner_container);
        expressContainer = findViewById(R.id.express_banner_container);
        bannerAd = YLAdManager.with(this).getEngine(YLAdConstants.AdName.BANNER, "");
        expressBannerAd = YLAdManager.with(this).getEngine(YLAdConstants.AdName.EXPRESS_BANNER, "");
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

    YLInnerAdListener expressListener = new YLAdSimpleListener() {


        @Override
        public void onSuccess(int source, boolean type, YLAdEntity entity) {
            super.onSuccess(source, type, entity);
            expressSucceed = true;
            dialog.dismiss();
            ToastUtil.show(context, "广告请求成功：来源：" + Utils.getSourceName(source));
        }

        @Override
        public void onError(int source, YLAdEntity entity, int code, String msg) {
            super.onError(source, entity, code, msg);
            expressSucceed = false;
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