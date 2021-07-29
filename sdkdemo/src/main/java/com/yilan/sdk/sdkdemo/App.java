package com.yilan.sdk.sdkdemo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.yilan.sdk.player.ylplayer.YLPlayerConfig;
import com.yilan.sdk.player.ylplayer.callback.OnPlayerCallBack;
import com.yilan.sdk.ui.YLUIInit;
import com.yilan.sdk.ui.configs.CommentConfig;
import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.ui.configs.FeedScrollListener;
import com.yilan.sdk.ui.configs.LittleVideoConfig;
import com.yilan.sdk.ui.configs.YLUIConfig;
import com.yilan.sdk.ui.configs.callback.CommentSimpleCallback;
import com.yilan.sdk.ui.configs.callback.LikeCallback;
import com.yilan.sdk.ui.configs.callback.OnAvatarClickListener;
import com.yilan.sdk.ui.configs.callback.OnLittleVideoCallBack;
import com.yilan.sdk.ui.configs.callback.OnRelateVideoListener;
import com.yilan.sdk.ylad.IAdExtraDataListener;
import com.yilan.sdk.ylad.IYLAdListener;
import com.yilan.sdk.ylad.config.YLAdConfig;
import com.yilan.sdk.ylad.constant.YLAdConstants;

public class App extends Application {

    public static final String TAG = "YL_AD_CALLBACK";
    public static final String TAG_LITTLE = "YL_VIDEO_CALLBACK";

    @Override
    public void onCreate() {
        super.onCreate();
        YLUIInit.getInstance()
                .setCrashOpen(false)
                .setApplication(this)
                .setAccessKey("ylel2vek386q")
                .setAccessToken("talb5el4cbw3e8ad3jofbknkexi1z8r4")
                .logEnable(true)
                .build();

        //请在合适的时机设置信息收集开关，可提升视频只能推荐效果，和广告收益，此开关默认false。详细请阅读文档-合规说明
        // 1.用户第一次安装并打开时，在用户同意授权之后调用，
        // 2.第二次及之后每次打开app 可以 Application 的onCreate中调用
        YLUIInit.submitPolicyGrantResult(true);

        YLUIConfig.getInstance()
                .feedPlayStyle(FeedConfig.STYLE_FEED_PLAY)
                .recommendHintEnable(true)
                .littleLikeShow(true)
                .littleShareShow(true)
                .littleShowGuide(true)
                .littleShowRelate(false)
                .littleShowAvatar(true)
                .feedShowAvatar(true)
                .littleComment(CommentConfig.CommentType.SHOW_COMMENT_ALL)
                .videoComment(CommentConfig.CommentType.SHOW_COMMENT_ALL)
                .setVideoSurfaceModel(YLPlayerConfig.SURFACE_MODEL_CROP)
                .videoLikeShow(true)
                .videoShareShow(true)
                .followAvailable(true)
                .followChannelAvailable(true)
                .feedAvatarClickable(true)
                .feedPlayAuto(false)
                .setLittleTitleBottom(0)
                .setLittleHotBarBottom(0)
                .registerFeedScrollCallBack(new FeedScrollListener() {
                    @Override
                    public void onScrollTop() {
                        super.onScrollTop();
                        Log.i(TAG, "top");

                    }

                    @Override
                    public void onScrollBottom() {
                        super.onScrollBottom();
                        Log.i(TAG, "bottom");
                    }
                })
                .registerAdListener(new IYLAdListener() {

                    @Override
                    public void onSuccess(String adType, int source, String reqId, String pid) {
                        Log.i(TAG, "[onSuccess] " + "adType = " + adType + " source = " + source + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onError(String adType, int source, String reqId, int code, String msg, String pid) {
                        Log.i(TAG, "[onError] " + "adType = " + adType + " source = " + source + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onRenderError(String adType, int source, String reqId, int code, String msg, String pid) {
                        Log.i(TAG, "[onRenderError] " + "adType = " + adType + " source = " + source + " reqId = " + code + " pid = " + pid);
                    }

                    @Override
                    public void onShow(String adType, int source, String reqId, String pid) {
                        Log.i(TAG, "[onShow] " + "adType = " + adType + " source = " + source + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onClick(String adType, int source, String reqId, String pid) {
                        Log.i(TAG, "[onClick] " + "adType = " + adType + " source = " + source + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onSkip(String adType, int source, String reqId, String pid) {
                        Log.i(TAG, "[onSkip] " + "adType = " + adType + " source = " + source + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onTimeOver(String adType, int source, String reqId, String pid) {
                        Log.i(TAG, "[onTimeOver] " + "adType = " + adType + " source = " + source + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onClose(String adType, int source, String reqId, String pid) {
                        Log.i(TAG, "[onClose] " + "adType = " + adType + " source = " + source + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onAdEmpty(String adType, int source, String reqId, String pid) {
                        Log.i(TAG, "[onAdEmpty] " + "adType = " + adType + " source = " + source + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onVideoStart(String adType, int source, String reqId, String pid) {
                        Log.i(TAG, "[onVideoStart] " + "adType = " + adType + " source = " + source + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onVideoPause(String adType, int source, String reqId, String pid) {
                        Log.i(TAG, "[onVideoPause] " + "adType = " + adType + " source = " + source + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onVideoResume(String adType, int source, String reqId, String pid) {
                        Log.i(TAG, "[onVideoResume] " + "adType = " + adType + " source = " + source + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onVideoComplete(String adType, int source, String reqId, String pid) {
                        Log.i(TAG, "[onVideoComplete] " + "adType = " + adType + " source = " + source + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onVideoError(String adType, int source, String reqId, String pid) {
                        Log.i(TAG, "[onVideoError] " + "adType = " + adType + " source = " + source + " reqId = " + reqId + " pid = " + pid);
                    }
                });
        YLUIConfig.getInstance().registerCommentCallBack(new CommentSimpleCallback() {
            @Override
            public void onCommentClick(String videoID) {
                Log.e("Comment", "评论点击");
            }

            @Override
            public void onCommentSend(String videoID) {
                Log.e("Comment", "评论发送");
            }

            @Override
            public boolean onCommentShow(String videoID) {
                Log.e("Comment", "评论展示");
                return false;
            }

            @Override
            public void onCommentHide(String videoID) {
                Log.e("Comment", "评论关闭");
            }
        }).registerLikeCallBack(new LikeCallback() {
            @Override
            public void onLike(String videoID, boolean isLike) {
                Log.e("onLike", isLike ? "点赞：" + videoID : "取消点赞：" + videoID);
            }
        }).registerAvatarClick(new OnAvatarClickListener() {
            @Override
            public void onAvatarClick() {
                Log.e("Avatar", "头像被点击了");
            }
        }).registerRelateClick(new OnRelateVideoListener() {
            @Override
            public void onRelateClick(String videoID) {
                Log.e("Relate", "相关视频被点击了：" + videoID);
            }
        }).registerLittleVideoCallBack(new OnLittleVideoCallBack() {
            @Override
            public void onPositionChange(int position) {
                Log.e("onPositionChange", "当前位置：" + position);

            }
        });
        LittleVideoConfig.getInstance()
                .setVideoLoop(true)
                .registerPlayerCallBack(new OnPlayerCallBack() {
                    @Override
                    public void onStart(String pager, String videoID, String taskID) {
                        Log.d(TAG_LITTLE, "播放状态---onStart [pager：" + pager + "  videoID：" + videoID + "]");
                    }

                    @Override
                    public void onPause(String pager, String videoID, String taskID) {
                        Log.d(TAG_LITTLE, "播放状态---onPause [pager：" + pager + "  videoID：" + videoID + "]");
                    }

                    @Override
                    public void onResume(String pager, String videoID, String taskID) {
                        Log.d(TAG_LITTLE, "播放状态---onResume [pager：" + pager + "  videoID：" + videoID + "]");
                    }

                    @Override
                    public void onComplete(String pager, String videoID, String taskID) {
                        Log.d(TAG_LITTLE, "播放状态---onComplete [pager：" + pager + "  videoID：" + videoID + "]");
                    }

                    @Override
                    public void onLoopComplete(String pager, String videoID, String taskID, int num) {
                        Log.d(TAG_LITTLE, "播放状态---onLoopComplete [pager：" + pager + "  videoID：" + videoID + "]");
                    }

                    @Override
                    public void onStuckStart(String pager, String videoID, String taskID) {
                        Log.d(TAG_LITTLE, "播放状态---onStuckStart [pager：" + pager + "  videoID：" + videoID + "]");
                    }

                    @Override
                    public void onStuckEnd(String pager, String videoID, String taskID) {
                        Log.d(TAG_LITTLE, "播放状态---onStuckEnd [pager：" + pager + "  videoID：" + videoID + "]");
                    }

                    @Override
                    public void onError(String pager, String videoID, String taskID) {
                        Log.d(TAG_LITTLE, "播放状态---onError [pager：" + pager + "  videoID：" + videoID + "]");
                    }

                    @Override
                    public void onStop(String pager, String videoID, String taskID) {
                        Log.d(TAG_LITTLE, "播放状态---onStop [pager：" + pager + "  videoID：" + videoID + "]");
                    }
                });
        YLAdConfig.getInstance().registerAdExtraDataListener(new IAdExtraDataListener() {
            @Override
            public String getExtraData(int source) {
                if (source == YLAdConstants.ALLI_TOUTIAO_STENCIL) {
                    return "穿山甲的额外数据。。。。。";
                } else if (source == YLAdConstants.ALLI_GDT_EXPRESS) {
                    return "广点通的额外数据。。。。。";
                }
                return null;
            }

            @Override
            public String getUserId(int source) {
                return null;
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
