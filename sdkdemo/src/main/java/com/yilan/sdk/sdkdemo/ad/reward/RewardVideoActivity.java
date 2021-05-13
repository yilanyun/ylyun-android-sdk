package com.yilan.sdk.sdkdemo.ad.reward;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yilan.sdk.common.util.ToastUtil;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.sdkdemo.ad.BaseAdActivity;
import com.yilan.sdk.sdkdemo.util.Utils;
import com.yilan.sdk.ylad.YLAdListener;
import com.yilan.sdk.ylad.constant.YLAdConstants;
import com.yilan.sdk.ylad.engine.IYLAdEngine;
import com.yilan.sdk.ylad.entity.AdState;
import com.yilan.sdk.ylad.manager.YLAdManager;

public class RewardVideoActivity extends BaseAdActivity {

    IYLAdEngine rewardEngine;
    LinearLayout container;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_video);
        container = findViewById(R.id.container);
        editText = findViewById(R.id.edit_num);
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString().trim();
                switch (s) {
                    case "":
                        rewardEngine = YLAdManager.with(RewardVideoActivity.this).getEngine(YLAdConstants.AdName.REWARD_VIDEO, "");
                        break;
                    case "2":
                        rewardEngine = YLAdManager.with(RewardVideoActivity.this).getEngine(YLAdConstants.AdName.REWARD_VIDEO2, "");
                        break;
                    case "3":
                        rewardEngine = YLAdManager.with(RewardVideoActivity.this).getEngine(YLAdConstants.AdName.REWARD_VIDEO3, "");
                        break;
                    case "4":
                        rewardEngine = YLAdManager.with(RewardVideoActivity.this).getEngine(YLAdConstants.AdName.REWARD_VIDEO4, "");
                        break;
                    case "5":
                        rewardEngine = YLAdManager.with(RewardVideoActivity.this).getEngine(YLAdConstants.AdName.REWARD_VIDEO5, "");
                        break;
                }
            }
        });
        rewardEngine = YLAdManager.with(this).getEngine(YLAdConstants.AdName.REWARD_VIDEO, "");
    }

    public void requestReward(View view) {
        ToastUtil.show(RewardVideoActivity.this, "开始请求");
        dialog.show();
        rewardEngine.reset();
        rewardEngine.setAdListener(new YLAdListener() {
            @Override
            public void onSuccess(String adType, int source, String reqId, String pid) {
                super.onSuccess(adType, source, reqId, pid);
                succeed = true;
                dialog.dismiss();
                ToastUtil.show(RewardVideoActivity.this, "广告请求成功：来源：" + Utils.getSourceName(source));
            }

            @Override
            public void onError(String adType, int source, String reqId, int code, String msg, String pid) {
                super.onError(adType, source, reqId, code, msg, pid);
                succeed = false;
                dialog.dismiss();
                ToastUtil.show(RewardVideoActivity.this, "广告请求失败！");
            }


            @Override
            public void onClick(String adType, int source, String reqId, String pid) {
                ToastUtil.show(RewardVideoActivity.this, "广告点击");
            }

            @Override
            public void onTimeOver(String adType, int source, String reqId, String pid) {
                ToastUtil.show(RewardVideoActivity.this, "倒计时完毕");

            }

            @Override
            public void onVideoStart(String adType, int source, String reqId, String pid) {
                ToastUtil.show(RewardVideoActivity.this, "视频播放开始");
            }

            @Override
            public void onAdEmpty(String adType, int source, String reqId, String pid) {
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