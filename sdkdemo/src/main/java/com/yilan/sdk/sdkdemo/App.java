package com.yilan.sdk.sdkdemo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.yilan.sdk.common.util.FSLogcat;
import com.yilan.sdk.common.util.FSString;
import com.yilan.sdk.data.entity.MediaInfo;
import com.yilan.sdk.player.ylplayer.YLPlayerConfig;
import com.yilan.sdk.player.ylplayer.callback.OnPlayerCallBack;
import com.yilan.sdk.ui.YLUIInit;
import com.yilan.sdk.ui.configs.CommentConfig;
import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.ui.configs.LittleVideoConfig;
import com.yilan.sdk.ui.configs.YLUIConfig;
import com.yilan.sdk.ui.configs.callback.CommentSimpleCallback;
import com.yilan.sdk.ui.configs.callback.LikeCallback;
import com.yilan.sdk.ui.configs.callback.OnAvatarClickListener;
import com.yilan.sdk.ui.configs.callback.OnLittleVideoCallBack;
import com.yilan.sdk.ui.configs.callback.OnRelateVideoListener;
import com.yilan.sdk.ylad.YLAdListener;

public class App extends Application {

    public static final String TAG = "YL_AD_CALLBACK";
    public static final String TAG_LITTLE = "YL_LI";

    @Override
    public void onCreate() {
        super.onCreate();
        FSLogcat.DEBUG = true;
        String processName = FSString.getProcessName(this, android.os.Process.myPid());
        FSLogcat.e("App value", "" + processName);
        if (!this.getPackageName().equals(processName)) {
            return;
        }
        LeakCanary.install(this);
        YLUIInit.getInstance()
                .setCrashOpen(false)
                .setApplication(this)
                .setAccessKey("")
                .setAccessToken("")
                .logEnable(true)
                .build();
        YLUIConfig.getInstance()
                .littleLikeShow(true)
                .littleShareShow(true)
                .littleComment(CommentConfig.CommentType.SHOW_COMMENT_ALL)
                .videoComment(CommentConfig.CommentType.SHOW_COMMENT_ALL)
                .setVideoSurfaceModel(YLPlayerConfig.SURFACE_MODEL_FIT)
                .videoLikeShow(true)
                .videoShareShow(true)
                .followAvailable(true)
                .followChannelAvailable(true)
                .feedAvatarClickable(true)
                .feedPlayAuto(true)
                .registerAdListener(new YLAdListener() {

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
                FSLogcat.e("Comment", "评论点击");
            }

            @Override
            public void onCommentSend(String videoID) {
                FSLogcat.e("Comment", "评论发送");
            }

            @Override
            public boolean onCommentShow(String videoID) {
                FSLogcat.e("Comment", "评论展示");
                return false;
            }

            @Override
            public void onCommentHide(String videoID) {
                FSLogcat.e("Comment", "评论关闭");
            }
        }).registerLikeCallBack(new LikeCallback() {
            @Override
            public void onLike(String videoID, boolean isLike) {
                FSLogcat.e("onLike", isLike ? "点赞：" + videoID : "取消点赞：" + videoID);
            }
        }).registerAvatarClick(new OnAvatarClickListener() {
            @Override
            public void onAvatarClick() {
                FSLogcat.e("Avatar", "头像被点击了");
            }
        }).registerRelateClick(new OnRelateVideoListener() {
            @Override
            public void onRelateClick(String videoID) {
                FSLogcat.e("Relate", "相关视频被点击了：" + videoID);
            }
        }).registerLittleVideoCallBack(new OnLittleVideoCallBack() {
            @Override
            public void onPositionChange(int position) {
                FSLogcat.e("onPositionChange", "当前位置：" + position);

            }
        }).setVideoSurfaceModel(YLPlayerConfig.SURFACE_MODEL_CROP);//设置视频适配模式
        LittleVideoConfig.getInstance()
                .registerPlayerCallBack(new OnPlayerCallBack() {
                    @Override
                    public void onStart(String pager, String videoID, String taskID) {
                        Log.d(TAG_LITTLE, "当前播放状态 : 开始播放");
                    }

                    @Override
                    public void onPause(String pager, String videoID, String taskID) {
                        Log.d(TAG_LITTLE, "当前播放状态 : 暂停");
                    }

                    @Override
                    public void onResume(String pager, String videoID, String taskID) {

                    }

                    @Override
                    public void onComplete(String pager, String videoID, String taskID) {
                        Log.d(TAG_LITTLE, "当前播放状态 : 卡顿结束");
                    }

                    @Override
                    public void onLoopComplete(String pager, String videoID, String taskID, int num) {
                        Log.d(TAG_LITTLE, "当前播放状态 : 卡顿结束");
                    }

                    @Override
                    public void onStuckStart(String pager, String videoID, String taskID) {
                        Log.d(TAG_LITTLE, "当前播放状态 : 开始卡顿");
                    }

                    @Override
                    public void onStuckEnd(String pager, String videoID, String taskID) {
                        Log.d(TAG_LITTLE, "当前播放状态 : 卡顿结束");
                    }

                    @Override
                    public void onError(String pager, String videoID, String taskID) {

                    }

                    @Override
                    public void onStop(String pager, String videoID, String taskID) {

                    }
                });
        FeedConfig.getInstance().setOnItemClickListener(new FeedConfig.OnClickListener() {
                    @Override
                    public boolean onClick(Context context, MediaInfo info) {
                        Log.e("click ", "点击了 " + info);
                        return false;
                    }//点击回调
                });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
