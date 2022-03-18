package com.yilan.sdk.sdkdemo.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.sdk.sdkdemo.AppActivity;
import com.yilan.sdk.sdkdemo.BaseFragment;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.sdkdemo.view.guide.TipGuideKey;
import com.yilan.sdk.sdkdemo.view.guide.TipLightView;
import com.yilan.sdk.sdkdemo.view.guide.TipViewHelper;
import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.ui.configs.KSStyle;
import com.yilan.sdk.ui.configs.YLUIConfig;
import com.yilan.sdk.ui.little.LittlePageConfig;
import com.yilan.sdk.ui.little.YLLittleVideoActivity;

import java.util.Objects;

/**
 *
 */
public class FeedFragment extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_demo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.littleDraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLittleDraw(v);
            }
        });
        view.findViewById(R.id.little_follow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLittleFollow(v);
            }
        });

        view.findViewById(R.id.feedCurrent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFeedCurrent(v);
            }
        });
        view.findViewById(R.id.feedDetail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFeedDetail(v);
            }
        });
        view.findViewById(R.id.feed_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFeedSingle(v);
            }
        });
        view.findViewById(R.id.ksGrid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKsGrid(v);
            }
        });
        view.findViewById(R.id.ksStaggered).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKsStaggered(v);
            }
        });
        view.findViewById(R.id.hybrid_feed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHybridFeed(v);
            }
        });
        view.findViewById(R.id.hybrid_feed_multi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHybridMultiFeed(v);
            }
        });
        view.findViewById(R.id.hybrid_feed_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHybridFeedDemo(v);
            }
        });
        view.findViewById(R.id.start_by_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickStartByID(v);
            }
        });
    }

    private void onClickStartByID(View v) {
        DemoActivity.startMyLike(v.getContext());
    }

    /**
     * 混合式信息流(单排)
     *
     * @param v
     */
    private void onClickHybridFeed(View v) {
        DemoActivity.startHybridFeed(v.getContext());
    }

    /**
     * 混合式信息流（双排）
     *
     * @param v
     */
    private void onClickHybridMultiFeed(View v) {
        DemoActivity.startHybridFeedMulti(v.getContext());
    }

    /**
     * 混合式信息流
     *
     * @param v
     */
    private void onClickHybridFeedDemo(View v) {
        DemoActivity.startHybridFeedDemo(v.getContext());
    }

    /**
     * 沉浸式信息流
     *
     * @param v
     */
    private void onClickLittleDraw(View v) {
        Objects.requireNonNull(getActivity()).getApplication().registerActivityLifecycleCallbacks(callbacks);
        YLLittleVideoActivity.start(v.getContext(), LittlePageConfig.DefaultConfig());
    }

    private void onClickLittleFollow(View v) {
        startActivity(new Intent(v.getContext(), AppActivity.class));
    }

    /**
     * 抽屉式信息流-当前页播放
     *
     * @param v
     */
    private void onClickFeedCurrent(View v) {
        YLUIConfig.getInstance().feedPlayStyle(FeedConfig.STYLE_FEED_PLAY);
        DemoActivity.startFeed(v.getContext());
    }

    /**
     * 抽屉式信息流-播放页播放
     *
     * @param v
     */
    private void onClickFeedDetail(View v) {
        YLUIConfig.getInstance().feedPlayStyle(FeedConfig.STYLE_NATIVE);
        DemoActivity.startFeed(v.getContext());
    }

    /**
     * 抽屉式信息流-单频道
     *
     * @param v
     */
    private void onClickFeedSingle(View v) {
        YLUIConfig.getInstance().feedPlayStyle(FeedConfig.STYLE_FEED_PLAY);
        DemoActivity.startFeedSingle(v.getContext());
    }

    /**
     * 宫格型信息流-整齐型
     *
     * @param v
     */
    private void onClickKsGrid(View v) {
        YLUIConfig.getInstance().setKsStyle(KSStyle.STYLE_GRID);
        DemoActivity.startKs(v.getContext());
    }

    /**
     * 宫格型信息流-错落型
     *
     * @param v
     */
    private void onClickKsStaggered(View v) {
        YLUIConfig.getInstance().setKsStyle(KSStyle.STYLE_STAGGER);
        DemoActivity.startKs(v.getContext());
    }


    SimpleActivityLifecycleCallbacks callbacks = new SimpleActivityLifecycleCallbacks() {
        @Override
        public void onActivityResumed(Activity activity) {
            super.onActivityResumed(activity);
            if (activity instanceof YLLittleVideoActivity) {
                showGuideForLittle(activity);
            }
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            super.onActivityDestroyed(activity);
            if (activity instanceof YLLittleVideoActivity) {
                removeLifeListener();
            }
        }
    };

    private void removeLifeListener() {
        Objects.requireNonNull(getActivity()).getApplication().unregisterActivityLifecycleCallbacks(callbacks);
    }

    private void showGuideForLittle(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.postDelayed(new Runnable() {
            @Override
            public void run() {
                View cpNameView = decorView.findViewById(com.yilan.sdk.ui.R.id.title);
                TipViewHelper helper = new TipViewHelper(activity, "YLLittleVideoActivity");
                if (cpNameView != null) {
                    helper.addTipView(cpNameView, "这里可以设置底部边距", TipGuideKey.KEY_LITTLE_DP_BOTTOM);
                }
//                View hotBar = decorView.findViewById(com.yilan.sdk.ui.R.id.hot_enter_container);
//                if (hotBar != null) {
//                    helper.addTipView(hotBar, "这里也可以设置底部边距", TipGuideKey.KEY_LITTLE_HOT_BOTTOM);
//                }
                View littleRightView = decorView.findViewById(com.yilan.sdk.ui.R.id.ll_ui_layout);
                if (littleRightView != null) {
                    helper.addTipView(littleRightView, "这里可以设置元素的隐藏与否", TipGuideKey.KEY_LITTLE_HOT_BOTTOM);
                }
                View backView = decorView.findViewById(com.yilan.sdk.ui.R.id.player_goback);
                if (backView != null) {
                    helper.addTipView(backView, "点击这里返回到首页->右上角设置->小视频相关配置，可以更改小视频相关设置", TipGuideKey.KEY_LITTLE_HOT_BOTTOM);
                }
                helper.setLightType(TipLightView.TipLightType.Rectangle);
                helper.showTip();
            }
        }, 2000);

    }

}