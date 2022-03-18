package com.yilan.sdk.sdkdemo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.yilan.sdk.data.entity.MediaInfo;
import com.yilan.sdk.player.ylplayer.YLPlayerConfig;
import com.yilan.sdk.player.ylplayer.callback.OnPlayerCallBack;
import com.yilan.sdk.ui.YLUIInit;
import com.yilan.sdk.ui.configs.CommentConfig;
import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.ui.configs.FeedScrollListener;
import com.yilan.sdk.ui.configs.KSStyle;
import com.yilan.sdk.ui.configs.LittleStyle;
import com.yilan.sdk.ui.configs.LittleVideoConfig;
import com.yilan.sdk.ui.configs.YLUIConfig;
import com.yilan.sdk.ui.configs.callback.CommentSimpleCallback;
import com.yilan.sdk.ui.configs.callback.FollowCallback;
import com.yilan.sdk.ui.configs.callback.LikeCallback;
import com.yilan.sdk.ui.configs.callback.LoginCallback;
import com.yilan.sdk.ui.configs.callback.OnAvatarClickListener;
import com.yilan.sdk.ui.configs.callback.OnLittleVideoCallBack;
import com.yilan.sdk.ui.configs.callback.OnRelateVideoListener;
import com.yilan.sdk.ui.configs.callback.ShareCallback;

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
                // ------ 全局配置 -------
                //全局，设置视频画面适配模式 SURFACE_MODEL_CROP(默认值)：裁剪模式，SURFACE_MODEL_FIT：等比拉伸
                .setVideoSurfaceModel(YLPlayerConfig.SURFACE_MODEL_CROP)
                // 全局：视频来源设置，TEXT：文字形式，LOGO：logo形式， 默认值：TEXT
                .setVideoSource(YLUIConfig.TEXT)
                .followAvailable(true)// 全局，是否显示关注 默认值：true

                // ------ 横视频信息流配置 -------
                //播放模式，STYLE_FEED_PLAY(默认值)：当前页面播放，STYLE_NATIVE：跳转到播放页播放，STYLE_WEB：跳转到web页面播放
                .feedPlayStyle(FeedConfig.STYLE_FEED_PLAY)
                //设置是否自动播放视频，仅在当前页面播放时有效（STYLE_FEED_PLAY）
                .feedPlayAuto(false)
                //设置频道标题的字体大小 默认18，单位dp
                .setFeedTitleTextSize(18)
                //设置频道标题是否显示下划线 默认值：true
                .setFeedUseIndicator(true)
                //设置频道标题被选中颜色，默认值 #5698F5
                .setFeedTitleSelectColor(R.color.color_festival)
                //设置频道标题被未选中颜色，默认值 #666666
                .setFeedTitleUnSelectColor(R.color.color_dark)
                //设置每次刷新内容后，是否有toast提示，默认值：false
                .recommendHintEnable(true) //feed信息流加载内容后的toast 提示开关，默认不显示
                //设置评论的样式 SHOW_COMMENT_ALL:即可查看，也可发布评论（需要登陆），SHOW_COMMENT_LIST：只能查看评论，不能发布评论，DISMISS_COMMENT：不显示评论
                .videoComment(CommentConfig.CommentType.SHOW_COMMENT_ALL)
                //设置是否显示点赞按钮 默认值：true
                .videoLikeShow(true)
                //设置是否显示分享按钮，默认值：true
                .videoShareShow(true)
                //在登陆后，设置是否显示单独的关注频道，默认值：true
                .followChannelAvailable(true)
                //设置头像是否可点击，默认值：true
                .feedAvatarClickable(true)
                //是否展示头像，默认值：true
                .feedShowAvatar(true)
                //设置下拉刷新是否可用，默认值：true 可用
                .feedSwipeRefreshEnable(true)
                // ------ 小视频信息流配置 -------
                //设置小视频页面样式：STYLE_RIGHT(默认值)：交互按钮在右侧，STYLE_BOTTOM：交互按钮在底部
                .setLittleStyle(LittleStyle.STYLE_RIGHT)
                //设置小视频+关注页面（YLLittleChannelFragment）频道字号，默认值（19）
                .setLittleTitleTextSize(19)
                //设置频道被选中时的颜色。默认值：#ffffff
                .setLittleTitleSelectColor(R.color.white)
                //设置频道未被选中时的颜色。默认值：#bbffffff
                .setLittleTitleUnSelectColor(R.color.yl_color_bf)
                //设置点赞是否显示，默认值：true
                .littleLikeShow(true)
                //设置分享是否显示，默认值：true
                .littleShareShow(true)
                //第一次展示时是否显示新手引导，默认值：true
                .littleShowGuide(true)
                //设置是否暂时相关视频，默认值：true
                .littleShowRelate(true)
                //是否展示头像，默认值：true
                .littleShowAvatar(true)
                //设置评论的样式 SHOW_COMMENT_ALL:即可查看，也可发布评论（需要登陆），SHOW_COMMENT_LIST：只能查看评论，不能发布评论，DISMISS_COMMENT：不显示评论
                .littleComment(CommentConfig.CommentType.SHOW_COMMENT_ALL)
                //设置下拉刷新是否可用，默认值：true
                .littleRefreshEnable(true)
                //设置小视频ui元素距离底部的偏移量，默认值：0
                .setLittleTitleBottom(0)
                //设置小视频底部热点距离底部的偏移量，默认值：0
                .setLittleHotBarBottom(0)

                //------小视频快手样式下的配置
                //设置快手样式下的页面样式，默认STYLE_GRID，等宽，STYLE_STAGGER：非等宽瀑布流
                .setKsStyle(KSStyle.STYLE_GRID)
        ;
        //注册feed信息流滑动到顶部和底部的状态回调
        YLUIConfig.getInstance().registerFeedScrollCallBack(new FeedScrollListener() {
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
        }).registerShareCallBack(new ShareCallback() {
            @Override
            public void onShare(Context context, MediaInfo info) {

            }
        }).registerLikeCallBack(new LikeCallback() {
            @Override
            public boolean onLike(String videoID, boolean isLike) {
                Log.e("onLike", isLike ? "点赞：" + videoID : "取消点赞：" + videoID);
                return false;
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
        }).registerFollowCallBack(new FollowCallback() {
            @Override
            public boolean onFollowClick(String videoID) {
                Log.e("Follow", "点击关注,返回值代表是否阻止本次行为，若返回true，表示拦截本次行为");
                return false;
            }
        }).registerLoginCallback(new LoginCallback() {
            @Override
            public void onNeedLogin() {
                Log.e("onLogin", "需要登陆");
                Intent intent = new Intent(App.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onLoginSuccess() {
                Log.e("onLogin", "登陆成功");

            }

            @Override
            public void onLoginError() {
                Log.e("onLogin", "登陆失败");
            }

            @Override
            public void onLogout() {
                Log.e("onLogin", "注销登陆");
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
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
