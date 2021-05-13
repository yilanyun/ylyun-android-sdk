package com.yilan.sdk.sdkdemo.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.FrameMetrics;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;

import com.yilan.sdk.common.executor.Dispatcher;
import com.yilan.sdk.common.executor.handler.YLCoroutineScope;
import com.yilan.sdk.common.executor.handler.YLJob;
import com.yilan.sdk.common.util.FSLogcat;
import com.yilan.sdk.data.entity.Channel;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.sdkdemo.view.guide.TipGuideKey;
import com.yilan.sdk.sdkdemo.view.guide.TipLightView;
import com.yilan.sdk.sdkdemo.view.guide.TipViewHelper;
import com.yilan.sdk.ui.category.ChannelFragment;
import com.yilan.sdk.ui.feed.YLFeedFragment;
import com.yilan.sdk.ui.hybridfeed.HybridFeedFragment;
import com.yilan.sdk.ui.hybridfeed.HybridMultiFeedFragment;
import com.yilan.sdk.ui.littlevideo.KSLittleVideoFragment;

public class DemoActivity extends AppCompatActivity {

    private static final int TYPE_FEED = 0;
    private static final int TYPE_KS = 1;
    private static final int TYPE_SINGLE = 2;
    private static final int TYPE_HYBRID = 3;
    private static final int TYPE_HYBRID_DEMO = 4;
    private static final int TYPE_MYLIKE = 5;
    private static final int TYPE_HYBRID_MULTI = 6;
    private static final int TYPE_FEED_SINGLE = 7;
    private int type = -1;
    private int subType = -1;
    private FragmentManager manager;

    public static void startFeed(Context context) {
        Intent intent = new Intent(context, DemoActivity.class);
        intent.putExtra("type", TYPE_FEED);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void startFeedSingle(Context context) {
        Intent intent = new Intent(context, DemoActivity.class);
        intent.putExtra("type", TYPE_FEED_SINGLE);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void startKs(Context context) {
        Intent intent = new Intent(context, DemoActivity.class);
        intent.putExtra("type", TYPE_KS);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void startSingle(Context context, int subType) {
        Intent intent = new Intent(context, DemoActivity.class);
        intent.putExtra("type", TYPE_SINGLE);
        intent.putExtra("subType", subType);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void startHybridFeed(Context context) {
        Intent intent = new Intent(context, DemoActivity.class);
        intent.putExtra("type", TYPE_HYBRID);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void startHybridFeedMulti(Context context) {
        Intent intent = new Intent(context, DemoActivity.class);
        intent.putExtra("type", TYPE_HYBRID_MULTI);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void startHybridFeedDemo(Context context) {
        Intent intent = new Intent(context, DemoActivity.class);
        intent.putExtra("type", TYPE_HYBRID_DEMO);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void startMyLike(Context context) {
        Intent intent = new Intent(context, DemoActivity.class);
        intent.putExtra("type", TYPE_MYLIKE);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showFrameMetrics();
        setContentView(R.layout.activity_feed);
        if (getIntent() != null) {
            type = getIntent().getIntExtra("type", -1);
            subType = getIntent().getIntExtra("subType", -1);
        }
        manager = getSupportFragmentManager();
        Fragment fragment = getFragmentByType();
        if (fragment != null) {
            manager.beginTransaction().replace(R.id.content_layout, fragment).commitAllowingStateLoss();
        }
        YLJob job = YLCoroutineScope.instance.executeDelay(Dispatcher.MAIN, new Runnable() {
            @Override
            public void run() {
                showGuideIfNeed();
            }
        }, 1000);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showFrameMetrics() {
        getWindow().addOnFrameMetricsAvailableListener(new Window.OnFrameMetricsAvailableListener() {
            @Override
            public void onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics, int dropCountSinceLastInvocation) {
                FSLogcat.d("FrameMetrics", "measure + layout = " + frameMetrics.getMetric(FrameMetrics.LAYOUT_MEASURE_DURATION) / 1000000 +
                        "  delay = " + frameMetrics.getMetric(FrameMetrics.UNKNOWN_DELAY_DURATION) / 1000000 +
                        "  anim = " + frameMetrics.getMetric(FrameMetrics.ANIMATION_DURATION) / 1000000 +
                        "  touch = " + frameMetrics.getMetric(FrameMetrics.INPUT_HANDLING_DURATION) / 1000000 +
                        "  draw = " + frameMetrics.getMetric(FrameMetrics.DRAW_DURATION) / 1000000 +
                        "  total = " + frameMetrics.getMetric(FrameMetrics.TOTAL_DURATION) / 1000000 + "\n"
                );
            }
        }, new Handler());
    }

    private void showGuideIfNeed() {
        if (type == TYPE_FEED) {
            showGuideForChannel();
        }
    }

    private void showGuideForChannel() {
        View decorView = this.getWindow().getDecorView();
        TipViewHelper helper = new TipViewHelper(this, "YLLittleVideoActivity");
//        View tabView = decorView.findViewById(R.id.tab_layout);
//        if (tabView != null) {
//            helper.addTipView(tabView, "这里文字可以设置选中颜色", TipGuideKey.KEY_FEED_TAB);
//        }
        View imgHead = decorView.findViewById(com.yilan.sdk.ui.R.id.iv_cp_head);
        if (imgHead != null) {
            ViewParent parent = imgHead.getParent();
            if (parent instanceof View) {
                View parentView = (View) parent;
                helper.addTipView(parentView, "头像可以设置隐藏", TipGuideKey.KEY_FEED_IMG_HEAD);
            }
        }
        View likeView = decorView.findViewById(com.yilan.sdk.ui.R.id.rl_like);
        if (likeView != null) {
            ViewParent parent = likeView.getParent();
            if (parent instanceof View) {
                View parentView = (View) parent;
                helper.addTipView(parentView, "这里可以设置元素的隐藏与否\n图标也是可以修改的", TipGuideKey.KEY_FEED_SOCIAL_GROUP);
            }
        }
        helper.setLightType(TipLightView.TipLightType.Rectangle);
        helper.showTip();
    }

    private Fragment getFragmentByType() {
        if (type == TYPE_KS) {
            return KSLittleVideoFragment.newInstance();
        } else if (type == TYPE_FEED) {
            return new ChannelFragment();
        } else if (type == TYPE_HYBRID) {
            return new HybridFeedFragment();
        } else if (type == TYPE_HYBRID_MULTI) {
            return new HybridMultiFeedFragment();
        } else if (type == TYPE_SINGLE) {
            return SubFragment.newInstance(subType);
        } else if (type == TYPE_FEED_SINGLE) {
            Channel channel = new Channel();
            channel.setName("推荐");
            channel.setId("1291");
            return YLFeedFragment.newInstance(channel);
        } else if (type == TYPE_MYLIKE) {
            return new StartByIDFragment();
        }
        return null;
    }
}