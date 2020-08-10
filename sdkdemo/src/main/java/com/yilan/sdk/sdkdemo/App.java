package com.yilan.sdk.sdkdemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.yilan.sdk.common.util.FSLogcat;
import com.yilan.sdk.common.util.FSString;
import com.yilan.sdk.common.util.NFLib;
import com.yilan.sdk.entity.MediaInfo;
import com.yilan.sdk.player.PlayerUIConfig;
import com.yilan.sdk.player.UserCallback;
import com.yilan.sdk.player.entity.PlayData;
import com.yilan.sdk.player.utils.Constant;
import com.yilan.sdk.ui.YLUIInit;
import com.yilan.sdk.ui.ad.entity.AdEntity;
import com.yilan.sdk.ui.configs.AdCallback;
import com.yilan.sdk.ui.configs.AdListener;
import com.yilan.sdk.ui.configs.AdVideoCallback;
import com.yilan.sdk.ui.configs.CommentConfig;
import com.yilan.sdk.ui.configs.CommentSimpleCallback;
import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.ui.configs.LikeCallback;
import com.yilan.sdk.ui.configs.LittleVideoConfig;
import com.yilan.sdk.ui.configs.OnAvatarClickListener;
import com.yilan.sdk.ui.configs.OnRelateVideoListener;
import com.yilan.sdk.ui.configs.YLUIConfig;
import com.yilan.sdk.ui.configs.onLittleVideoCallBack;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FSLogcat.DEBUG = true;

        String processName = FSString.getProcessName(this, android.os.Process.myPid());
        FSLogcat.e("App value", "" + processName);
        if (!this.getPackageName().equals(processName)) {
            return;
        }
        YLUIInit.getInstance()
                .setCrashOpen(false)
                .setApplication(this)
                .setAccessKey("")
                .setAccessToken("")
                .setWebStyle(2)
                .logEnable(true)
                .build();
        YLUIConfig.getInstance()
                .littleLikeShow(true)
                .littleShareShow(true)
                .littleComment(CommentConfig.CommentType.SHOW_COMMENT_ALL)
                .videoComment(CommentConfig.CommentType.SHOW_COMMENT_ALL)
                .setVideoSurfaceModel(PlayerUIConfig.SURFACE_MODEL_CROP)
                .videoLikeShow(true)
                .videoShareShow(true)
                .followAvailable(true)
                .followChannelAvailable(true)
                .feedAvatarClickable(true)
                .feedPlayAuto(true)
                .setAdListener(new AdListener() {
                    @Override
                    public void onAdClick(String ylID, String reqId, int type) {
                        super.onAdClick(ylID, reqId, type);
                        FSLogcat.e("AdListener_onAdClick:", "ylID:" + ylID + " reqId:" + reqId + " type:" + type);
                    }

                    @Override
                    public void onAdShow(String ylID, String reqId, int type) {
                        super.onAdShow(ylID, reqId, type);
                        FSLogcat.e("AdListener_onAdShow:", "ylID:" + ylID + " reqId:" + reqId + " type:" + type);

                    }

                    @Override
                    public void onPlay(String ylID, String reqId, int type) {
                        super.onPlay(ylID, reqId, type);
                        FSLogcat.e("AdListener_onPlay:", "ylID:" + ylID + " reqId:" + reqId + " type:" + type);

                    }

                    @Override
                    public void onPause(String ylID, String reqId, int type) {
                        super.onPause(ylID, reqId, type);
                        FSLogcat.e("AdListener_onPause:", "ylID:" + ylID + " reqId:" + reqId + " type:" + type);

                    }

                    @Override
                    public void onError(String ylID, String reqId, int type, String msg) {
                        super.onError(ylID, reqId, type, msg);
                        FSLogcat.e("AdListener_onError:", "ylID:" + ylID + " reqId:" + reqId + " type:" + type);

                    }

                    @Override
                    public void onComplete(String ylID, String reqId, int type) {
                        super.onComplete(ylID, reqId, type);
                        FSLogcat.e("AdListener_onComplete:", "ylID:" + ylID + " reqId:" + reqId + " type:" + type);

                    }
                })
                .setAdCallback(new AdCallback() {
                    @Override
                    public void onAdClick(View view, int i) {
                        super.onAdClick(view, i);
                        FSLogcat.e("AdCallback", "click 穿山甲模版");
                    }

                    @Override
                    public void onAdShow(View view, int i) {
                        super.onAdShow(view, i);
                        FSLogcat.e("AdCallback", "show 穿山甲模版");

                    }

                    @Override
                    public void onAdClick(View view, TTNativeAd ad) {
                        super.onAdClick(view, ad);
                        FSLogcat.e("AdCallback", "click 穿山甲原生");
                    }

                    @Override
                    public void onAdShow(View view, TTNativeAd ad) {
                        super.onAdShow(view, ad);
                        FSLogcat.e("AdCallback", "show 穿山甲原生");
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
        })
                .registerLittleVideoCallBack(new onLittleVideoCallBack() {
                    @Override
                    public void onPositionChange(int position) {
                        FSLogcat.e("onPositionChange", "当前位置：" + position);

                    }
                });
        LittleVideoConfig.getInstance()
                .setUserCallback(new UserCallback() {
                    @Override
                    public boolean event(int type, PlayData data, int playerHash) {
                        switch (type) {
                            case Constant.STATE_PLAYING_BUFFERING_START:
                                break;
                            case Constant.STATE_PLAYING_BUFFERING_END:
                                break;
                            case Constant.STATE_PREPARED:
                                break;
                            case Constant.STATE_ERROR:
                                break;
                            case Constant.STATE_PLAYING:
                                Log.d("LittleVideoUserCallback", "当前播放状态 : 正在播放");
                                break;
                            case Constant.STATE_COMPLETE:
                                Log.d("LittleVideoUserCallback", "当前播放状态 : 播放完成");
                                break;
                            case Constant.STATE_PAUSED:
                                Log.d("LittleVideoUserCallback", "当前播放状态 : 暂停");
                                break;
                        }
                        return false;
                    }
                })
                .setAdVideoCallback(new AdVideoCallback() {
                    @Override
                    public void onVideoLoad(AdEntity entity) {
                        Log.d("adadad", "视频加载成功" + entity.getAdSlotId());
                    }

                    @Override
                    public void onVideoError(int errorCode, AdEntity entity) {
                        Log.d("adadad", "视频播放错误：errorCode=" + errorCode + entity.getAdSlotId());
                    }

                    @Override
                    public void onVideoAdStartPlay(AdEntity entity) {
                        Log.d("adadad", "视频开始播放" + entity.getAdSlotId());
                    }

                    @Override
                    public void onVideoAdPaused(AdEntity entity) {
                        Log.d("adadad", "视频暂停播放" + entity.getAdSlotId());
                    }

                    @Override
                    public void onVideoAdContinuePlay(AdEntity entity) {
                        Log.d("adadad", "视频继续播放" + entity.getAdSlotId());
                    }

                    @Override
                    public void onVideoAdComplete(AdEntity entity) {
                        Log.d("adadad", "视频播放完成" + entity.getAdSlotId());
                    }
                });
        FeedConfig.getInstance()
                .setUserCallback(new UserCallback() {
                    @Override
                    public boolean event(int type, PlayData data, int playerHash) {
                        Log.d("FeedVideoUserCallback", "当前播放状态 : " + type);
                        switch (type) {
                            case Constant.STATE_PREPARED:
                                break;
                            case Constant.STATE_ERROR:
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
        NFLib.initLib(base);
    }
}
