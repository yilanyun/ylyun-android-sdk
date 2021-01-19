package com.yilan.sdk.sdkdemo.ad.reward;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.yilan.sdk.common.util.ToastUtil;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.sdkdemo.ad.BaseAdActivity;
import com.yilan.sdk.sdkdemo.util.Utils;
import com.yilan.sdk.ylad.YLAdSimpleListener;
import com.yilan.sdk.ylad.constant.YLAdConstants;
import com.yilan.sdk.ylad.engine.IYLAdEngine;
import com.yilan.sdk.ylad.entity.AdState;
import com.yilan.sdk.ylad.entity.YLAdEntity;
import com.yilan.sdk.ylad.manager.YLAdManager;

public class RewardVideoActivity extends BaseAdActivity {

    IYLAdEngine rewardEngine;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_video);
        container = findViewById(R.id.container);
        rewardEngine = YLAdManager.with(this).getEngine(YLAdConstants.AdName.REWARD_VIDEO, "");
    }

    public void requestReward(View view) {
        ToastUtil.show(RewardVideoActivity.this, "开始请求");
        dialog.show();
        rewardEngine.reset();
        rewardEngine.setAdListener(new YLAdSimpleListener() {

            @Override
            public void onSuccess(int source, boolean type, YLAdEntity entity) {
                super.onSuccess(source, type, entity);
                succeed = true;
                dialog.dismiss();
                ToastUtil.show(RewardVideoActivity.this, "广告请求成功：来源：" + Utils.getSourceName(source));
            }

            @Override
            public void onError(int source, YLAdEntity entity, int code, String msg) {
                super.onError(source, entity, code, msg);
                succeed = false;
                dialog.dismiss();
                ToastUtil.show(RewardVideoActivity.this, "广告请求失败！");
            }

            @Override
            public void onClick(int source, boolean type, YLAdEntity entity) {
                super.onClick(source, type, entity);
                ToastUtil.show(RewardVideoActivity.this, "广告点击");
            }

            @Override
            public void onTimeOver(int source, boolean type, YLAdEntity entity) {
                super.onTimeOver(source, type, entity);
                ToastUtil.show(RewardVideoActivity.this, "倒计时完毕");

            }

            @Override
            public void onVideoStart(int source, boolean type, YLAdEntity entity) {
                super.onVideoStart(source, type, entity);
                ToastUtil.show(RewardVideoActivity.this, "视频播放开始");
            }

            @Override
            public void onAdEmpty(int source, boolean type, YLAdEntity entity) {
                super.onAdEmpty(source, type, entity);
                dialog.dismiss();
                ToastUtil.show(context, "广告为空，请先在一览云后台配置该广告！");
            }
        });
        rewardEngine.preRequest(container);
    }


    public void renderReward(View view) {
        if (succeed && rewardEngine.getState() == AdState.SUCCESS) {
            rewardEngine.renderAd();
        } else {
            ToastUtil.show(RewardVideoActivity.this, "还没有请求激励视频或请求失败，请尝试重新请求");
        }
    }
}