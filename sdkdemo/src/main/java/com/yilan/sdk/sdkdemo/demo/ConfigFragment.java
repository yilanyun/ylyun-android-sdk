package com.yilan.sdk.sdkdemo.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.yilan.sdk.player.ylplayer.YLPlayerConfig;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.ui.configs.CommentConfig;
import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.ui.configs.LittleStyle;
import com.yilan.sdk.ui.configs.LittleVideoConfig;
import com.yilan.sdk.ui.configs.PlayerConfig;
import com.yilan.sdk.ui.configs.YLUIConfig;

import static com.yilan.sdk.ui.configs.CommentConfig.CommentType.DISMISS_COMMENT;
import static com.yilan.sdk.ui.configs.CommentConfig.CommentType.SHOW_COMMENT_ALL;
import static com.yilan.sdk.ui.configs.CommentConfig.CommentType.SHOW_COMMENT_LIST;

public class ConfigFragment extends Fragment {

    public static ConfigFragment newInstance() {
        return new ConfigFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_config, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLittle(view);
        initFeed(view);
        initDetail(view);
    }

    private void initLittle(View view) {
        CommentConfig.CommentType commentType = LittleVideoConfig.getInstance().getCommentType();
        RadioGroup group = view.findViewById(R.id.little_group);
        switch (commentType) {
            case SHOW_COMMENT_ALL:
                group.check(R.id.little_radio1);
                break;
            case SHOW_COMMENT_LIST:
                group.check(R.id.little_radio2);
                break;
            case DISMISS_COMMENT:
                group.check(R.id.little_radio3);
                break;
        }
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.little_radio1:
                        LittleVideoConfig.getInstance().setCommentType(SHOW_COMMENT_ALL);
                        break;
                    case R.id.little_radio2:
                        LittleVideoConfig.getInstance().setCommentType(SHOW_COMMENT_LIST);
                        break;
                    case R.id.little_radio3:
                        LittleVideoConfig.getInstance().setCommentType(DISMISS_COMMENT);
                        break;
                }
            }
        });
        RadioGroup groupUI = view.findViewById(R.id.little_ui);
        if (LittleVideoConfig.getInstance().getLittleStyle() == LittleStyle.STYLE_RIGHT) {
            groupUI.check(R.id.little_ui_radio1);
        } else {
            groupUI.check(R.id.little_ui_radio2);
        }
        groupUI.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.little_ui_radio1:
                        LittleVideoConfig.getInstance().setLittleStyle(LittleStyle.STYLE_RIGHT);
                        break;
                    case R.id.little_ui_radio2:
                        LittleVideoConfig.getInstance().setLittleStyle(LittleStyle.STYLE_BOTTOM);
                        break;
                }
            }
        });

        TextView littleSize = view.findViewById(R.id.tv_litt_size);
        littleSize.setText(String.format("频道字体大小(%sdp)", LittleVideoConfig.getInstance().getTitleTextSize()));
        SeekBar littleSeekBar = view.findViewById(R.id.seek_little_size);
        littleSeekBar.setProgress(LittleVideoConfig.getInstance().getTitleTextSize());
        littleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    YLUIConfig.getInstance().setLittleTitleTextSize(progress);
                    littleSize.setText(String.format("频道字体大小(%sdp)", progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Switch littleRefresh = view.findViewById(R.id.little_refresh);
        littleRefresh.setChecked(LittleVideoConfig.getInstance().isSwipeRefreshEnable());
        littleRefresh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                YLUIConfig.getInstance().littleRefreshEnable(isChecked);
            }
        });

        Switch relate = view.findViewById(R.id.little_relate);
        relate.setChecked(LittleVideoConfig.getInstance().getRelateShow());
        relate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                YLUIConfig.getInstance().littleShowRelate(isChecked);
            }
        });

        Switch avatar = view.findViewById(R.id.little_avatar);
        avatar.setChecked(LittleVideoConfig.getInstance().showPlayerAvatar());
        avatar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                YLUIConfig.getInstance().littleShowAvatar(isChecked);
            }
        });

        Switch theme = view.findViewById(R.id.feed_theme);
        theme.setChecked(YLUIConfig.getInstance().getTheme() instanceof YLUIConfig.BlackTheme);
        theme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                YLUIConfig.getInstance().setTheme(isChecked ? new YLUIConfig.BlackTheme() : new YLUIConfig.WhiteTheme());
            }
        });

        Switch like = view.findViewById(R.id.little_like);
        like.setChecked(YLUIConfig.getInstance().isLittleLikeShow());
        like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                YLUIConfig.getInstance().littleLikeShow(isChecked);
            }
        });

        Switch share = view.findViewById(R.id.little_share);
        share.setChecked(YLUIConfig.getInstance().isLittleShareShow());
        share.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                YLUIConfig.getInstance().littleShareShow(isChecked);
            }
        });


        Switch loopStyle = view.findViewById(R.id.little_loop_style);
        loopStyle.setChecked(YLPlayerConfig.config().isVideoLoop());
        loopStyle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LittleVideoConfig.getInstance().setVideoLoop(isChecked);
                loopStyle.setText(String.format("小视频播放模式(%s)", isChecked ? "循环" : "播放下一个"));
            }
        });

        TextView textSeek = view.findViewById(R.id.tv_little_seek);
        SeekBar seekBar = view.findViewById(R.id.little_seek_bar);
        int dpTitleBottom = LittleVideoConfig.getInstance().getDpTitleBottom();
        seekBar.setMax(100);
        seekBar.setProgress(dpTitleBottom);
        textSeek.setText(String.format("小视频Title底部边距(%sdp)", dpTitleBottom));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    LittleVideoConfig.getInstance().setDpTitleBottom(progress);
                    textSeek.setText(String.format("小视频Title底部边距(%sdp)", progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        TextView textHotSeek = view.findViewById(R.id.tv_little_hot_seek);
        SeekBar seekBarHot = view.findViewById(R.id.little_hot_seek_bar);
        int dpHotBarBottom = LittleVideoConfig.getInstance().getDpHotBarBottom();
        seekBarHot.setMax(100);
        seekBarHot.setProgress(dpHotBarBottom);
        textHotSeek.setText(String.format("小视频热点底部边距(%sdp)", dpHotBarBottom));
        seekBarHot.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    LittleVideoConfig.getInstance().setDpHotBarBottom(progress);
                    textHotSeek.setText(String.format("小视频热点底部边距(%sdp)", progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        TextView channelSize = view.findViewById(R.id.tv_channel_size);
        channelSize.setText(String.format("频道字体大小(%sdp)", FeedConfig.getInstance().getTitleTextSize()));
        SeekBar channelSeekBar = view.findViewById(R.id.seek_channel_size);
        channelSeekBar.setProgress(FeedConfig.getInstance().getTitleTextSize());
        channelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    YLUIConfig.getInstance().setFeedTitleTextSize(progress);
                    channelSize.setText(String.format("频道字体大小(%sdp)", progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Switch channelSwitch = view.findViewById(R.id.channel_line);
        channelSwitch.setChecked(FeedConfig.getInstance().isUseIndicator());
        channelSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                YLUIConfig.getInstance().setFeedUseIndicator(isChecked);
            }
        });
        Switch followSwitch = view.findViewById(R.id.channel_follow);
        followSwitch.setChecked(FeedConfig.getInstance().getFollowChannelAvailable());
        followSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                YLUIConfig.getInstance().followChannelAvailable(isChecked);
            }
        });
        Switch refreshSwitch = view.findViewById(R.id.channel_refresh);
        followSwitch.setChecked(FeedConfig.getInstance().isSwipeRefreshEnable());
        followSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                YLUIConfig.getInstance().feedSwipeRefreshEnable(isChecked);
            }
        });
    }

    private void initFeed(View view) {
        CommentConfig.CommentType commentType = PlayerConfig.getInstance().getCommentType();
        RadioGroup group = view.findViewById(R.id.feed_group);
        switch (commentType) {
            case SHOW_COMMENT_ALL:
                group.check(R.id.feed_radio1);
                break;
            case SHOW_COMMENT_LIST:
                group.check(R.id.feed_radio2);
                break;
            case DISMISS_COMMENT:
                group.check(R.id.feed_radio3);
                break;
        }
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.feed_radio1:
                        PlayerConfig.getInstance().setCommentType(SHOW_COMMENT_ALL);
                        break;
                    case R.id.feed_radio2:
                        PlayerConfig.getInstance().setCommentType(SHOW_COMMENT_LIST);
                        break;
                    case R.id.feed_radio3:
                        PlayerConfig.getInstance().setCommentType(DISMISS_COMMENT);
                        break;
                }
            }
        });

        Switch like = view.findViewById(R.id.feed_like);
        like.setChecked(YLUIConfig.getInstance().isVideoLikeShow());
        like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                YLUIConfig.getInstance().videoLikeShow(isChecked);
            }
        });

        Switch share = view.findViewById(R.id.feed_share);
        share.setChecked(YLUIConfig.getInstance().isVideoShareShow());
        share.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                YLUIConfig.getInstance().videoShareShow(isChecked);
            }
        });

        Switch showAvatar = view.findViewById(R.id.feed_show_avatar);
        showAvatar.setChecked(FeedConfig.getInstance().showPlayerAvatar());
        showAvatar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                YLUIConfig.getInstance().feedShowAvatar(isChecked);
            }
        });
        Switch feedAutoPlay = view.findViewById(R.id.feed_auto_play);
        feedAutoPlay.setChecked(FeedConfig.getInstance().getFeedPlayAuto());
        feedAutoPlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                YLUIConfig.getInstance().feedPlayAuto(isChecked);
            }
        });

    }

    private void initDetail(View view) {
        Switch head = view.findViewById(R.id.detail_head);
        head.setChecked(FeedConfig.getInstance().getAvatarClickable());
        head.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                YLUIConfig.getInstance().feedAvatarClickable(isChecked);
            }
        });

        Switch follow = view.findViewById(R.id.detail_follow);
        follow.setChecked(FeedConfig.getInstance().getFollowShow());
        follow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                YLUIConfig.getInstance().followAvailable(isChecked);
            }
        });
    }
}