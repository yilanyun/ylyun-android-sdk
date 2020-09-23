package com.yilan.sdk.sdkdemo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.Toast;

import com.squareup.leakcanary.LeakCanary;
import com.yilan.sdk.common.util.BaseApp;
import com.yilan.sdk.common.util.FSLogcat;
import com.yilan.sdk.common.util.FSString;
import com.yilan.sdk.data.entity.MediaInfo;
import com.yilan.sdk.player.UserCallback;
import com.yilan.sdk.data.entity.PlayData;
import com.yilan.sdk.player.utils.Constant;
import com.yilan.sdk.player.ylplayer.YLPlayerConfig;
import com.yilan.sdk.player.ylplayer.callback.OnPlayerCallBack;
import com.yilan.sdk.ui.YLUIInit;
import com.yilan.sdk.ui.ad.entity.AdEntity;
import com.yilan.sdk.ui.configs.AdVideoCallback;
import com.yilan.sdk.ui.configs.CommentConfig;
import com.yilan.sdk.ui.configs.CommentSimpleCallback;
import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.ui.configs.LikeCallback;
import com.yilan.sdk.ui.configs.LittleVideoConfig;
import com.yilan.sdk.ui.configs.OnAvatarClickListener;
import com.yilan.sdk.ui.configs.OnRelateVideoListener;
import com.yilan.sdk.ui.configs.PageConstant;
import com.yilan.sdk.ui.configs.PageJumpCallback;
import com.yilan.sdk.ui.configs.YLUIConfig;
import com.yilan.sdk.ui.configs.onLittleVideoCallBack;
import com.yilan.sdk.ylad.YLAdListener;
import com.yilan.sdk.ylad.constant.YLAdConstants;

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
                    public void onSuccess(String adType, String reqId, String pid) {

                        FSLogcat.i(TAG, "[onSuccess] " + "adType = " + adType + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onError(String adType, String reqId, int code, String msg, String pid) {
                        FSLogcat.i(TAG, "[onError] " + "adType = " + adType + " reqId = " + reqId + " pid = " + pid);
                        if (adType.equals(YLAdConstants.AdName.FEED_VERTICAL)) {
                            Toast.makeText(BaseApp.get(), "msg:" + msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onRenderError(String adType, int code, String msg, String pid) {
                        FSLogcat.i(TAG, "[onRenderError] " + "adType = " + adType + " reqId = " + code + " pid = " + pid);
                    }

                    @Override
                    public void onShow(String adType, String reqId, String pid) {
                        FSLogcat.i(TAG, "[onShow] " + "adType = " + adType + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onClick(String adType, String reqId, String pid) {
                        FSLogcat.i(TAG, "[onClick] " + "adType = " + adType + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onSkip(String adType, String reqId, String pid) {
                        FSLogcat.i(TAG, "[onSkip] " + "adType = " + adType + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onTimeOver(String adType, String reqId, String pid) {
                        FSLogcat.i(TAG, "[onTimeOver] " + "adType = " + adType + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onClose(String adType, String reqId, String pid) {
                        FSLogcat.i(TAG, "[onClose] " + "adType = " + adType + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onAdEmpty(String adType, String reqId, String pid) {
                        FSLogcat.i(TAG, "[onAdEmpty] " + "adType = " + adType + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onVideoStart(String adType, String reqId, String pid) {
                        FSLogcat.i(TAG, "[onVideoStart] " + "adType = " + adType + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onVideoPause(String adType, String reqId, String pid) {
                        FSLogcat.i(TAG, "[onVideoPause] " + "adType = " + adType + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onVideoResume(String adType, String reqId, String pid) {
                        FSLogcat.i(TAG, "[onVideoResume] " + "adType = " + adType + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onVideoComplete(String adType, String reqId, String pid) {
                        FSLogcat.i(TAG, "[onVideoComplete] " + "adType = " + adType + " reqId = " + reqId + " pid = " + pid);
                    }

                    @Override
                    public void onVideoError(String adType, String reqId, String pid) {
                        FSLogcat.i(TAG, "[onVideoError] " + "adType = " + adType + " reqId = " + reqId + " pid = " + pid);
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
        }).registerLittleVideoCallBack(new onLittleVideoCallBack() {
            @Override
            public void onPositionChange(int position) {
                FSLogcat.e("onPositionChange", "当前位置：" + position);

            }
        })
                .registerPageJumpCallBack(new PageJumpCallback() {
                    @Override
                    public void onJumpToPage(String pageName) {
                        if (PageConstant.PAGE_UGC.equals(pageName)) {
                            FSLogcat.e("FeedJumpToVideoCallback", "跳转到UGC视频播放页");
                        } else if (PageConstant.PAGE_PGC.equals(pageName)) {
                            FSLogcat.e("FeedJumpToVideoCallback", "跳转到PGC视频播放页");
                        } else if (PageConstant.PAGE_CP_DETAIL.equals(pageName)) {
                            FSLogcat.e("CpJumpCallback", "跳转到CP详情页");
                        }
                    }
                })
                .setVideoSurfaceModel(YLPlayerConfig.SURFACE_MODEL_CROP);//设置视频适配模式
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
                })
                .setAdVideoCallback(new AdVideoCallback() {
                    @Override
                    public void onVideoLoad(AdEntity entity) {
                        Log.d("Callback-littleAd", "视频加载成功" + entity.getAdSlotId());
                    }

                    @Override
                    public void onVideoError(int errorCode, AdEntity entity) {
                        Log.d("Callback-littleAd", "视频播放错误：errorCode=" + errorCode + entity.getAdSlotId() + " reqid:" + entity.getReqId());
                    }

                    @Override
                    public void onVideoAdStartPlay(AdEntity entity) {
                        Log.d("Callback-littleAd", "视频开始播放" + entity.getAdSlotId() + " reqid:" + entity.getReqId());
                    }

                    @Override
                    public void onVideoAdPaused(AdEntity entity) {
                        Log.d("Callback-littleAd", "视频暂停播放" + entity.getAdSlotId() + " reqid:" + entity.getReqId());
                    }

                    @Override
                    public void onVideoAdContinuePlay(AdEntity entity) {
                        Log.d("Callback-littleAd", "视频继续播放" + entity.getAdSlotId() + " reqid:" + entity.getReqId());
                    }

                    @Override
                    public void onVideoAdComplete(AdEntity entity) {
                        Log.d("Callback-littleAd", "视频播放完成" + entity.getAdSlotId() + " reqid:" + entity.getReqId());
                    }
                });
        FeedConfig.getInstance()
                .setUserCallback(new UserCallback() {
                    @Override
                    public boolean event(int type, PlayData data, int playerHash) {
                        switch (type) {
                            case Constant.STATE_PLAYING_BUFFERING_START:
                                Log.d("FeedVideoUserCallback", "当前播放状态 : 开始卡顿");
                                break;
                            case Constant.STATE_PLAYING_BUFFERING_END:
                                Log.d("FeedVideoUserCallback", "当前播放状态 : 卡顿结束");
                                break;
                            case Constant.STATE_PREPARED:
                                Log.d("FeedVideoUserCallback", "当前播放状态 : 正在准备");
                                break;
                            case Constant.STATE_ERROR:
                                Log.d("FeedVideoUserCallback", "当前播放状态 : 播放错误");
                                break;
                            case Constant.STATE_PLAYING:
                                Log.d("FeedVideoUserCallback", "当前播放状态 : 正在播放");
                                break;
                            case Constant.STATE_COMPLETE:
                                break;
                            case Constant.STATE_PAUSED:
                                Log.d("FeedVideoUserCallback", "当前播放状态 : 暂停");
                                break;
                        }
                        return false;
                    }
                })
                .setOnItemClickListener(new FeedConfig.OnClickListener() {
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
