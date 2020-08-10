package com.yilan.sdk.sdkdemo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import com.yilan.sdk.YLInit;
import com.yilan.sdk.common.download.YLDownloadManager;
import com.yilan.sdk.common.util.FSFile;
import com.yilan.sdk.ui.YLUIInit;
import com.yilan.sdk.ui.configs.CommentConfig;
import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.ui.configs.LittleVideoConfig;
import com.yilan.sdk.ui.configs.PlayerConfig;
import com.yilan.sdk.ui.configs.YLUIConfig;
import com.yilan.sdk.ui.download.YLAdDownloadService;
import com.yilan.sdk.ui.web.WebActivity;

public class ConfigActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

    private RadioGroup commentRadioGroup;
    private RadioGroup littleVideoToolRadioGroup;
    private RadioGroup liitleCommentRadioGroup;

    private ToggleButton littleLike;
    private ToggleButton littleShare;
    private ToggleButton videoLike;
    private ToggleButton videoShare;
    private ToggleButton playerAvatar;
    private ToggleButton swipRefreshEnable;
    private ToggleButton follow;
    private ToggleButton share_pa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YLDownloadManager.getInstance().init(this);
        setContentView(R.layout.activity_config);

        commentRadioGroup = findViewById(R.id.config_comment);
        commentRadioGroup.setOnCheckedChangeListener(this);

        littleVideoToolRadioGroup = findViewById(R.id.config_littlevideo);
        littleVideoToolRadioGroup.setOnCheckedChangeListener(this);
        liitleCommentRadioGroup = findViewById(R.id.config_comment_little);
        liitleCommentRadioGroup.setOnCheckedChangeListener(this);

        littleLike = findViewById(R.id.little_like);
        littleLike.setChecked(LittleVideoConfig.getInstance().isLikeShow());
        littleLike.setOnCheckedChangeListener(this);

        littleShare = findViewById(R.id.little_share);
        littleShare.setChecked(LittleVideoConfig.getInstance().isShareShow());
        littleShare.setOnCheckedChangeListener(this);

        videoLike = findViewById(R.id.video_like);
        videoLike.setOnCheckedChangeListener(this);
        videoLike.setChecked(FeedConfig.getInstance().isLikeShow());

        videoShare = findViewById(R.id.video_share);
        videoShare.setOnCheckedChangeListener(this);
        videoShare.setChecked(FeedConfig.getInstance().isShareShow());

        playerAvatar = findViewById(R.id.player_avatar);
        playerAvatar.setOnCheckedChangeListener(this);
        playerAvatar.setChecked(FeedConfig.getInstance().getAvatarClickable());

        swipRefreshEnable = findViewById(R.id.video_refresh);
        swipRefreshEnable.setOnCheckedChangeListener(this);
        swipRefreshEnable.setChecked(FeedConfig.getInstance().isSwipeRefreshEnable());

        follow = findViewById(R.id.guanzhu);
        follow.setOnCheckedChangeListener(this);
        follow.setChecked(FeedConfig.getInstance().getFollowShow());

        share_pa = findViewById(R.id.share_pa);
        share_pa.setOnCheckedChangeListener(this);
        share_pa.setChecked(YLInit.getInstance().isAppendSharePa());

        initViews();

        findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
        int commentConfig = PlayerConfig.getInstance().getCommentType();
        LittleVideoConfig.LittleVideoStyle littleVideoStyle = LittleVideoConfig.getInstance().getLittleVideoStyle();

        switch (commentConfig) {
            case PlayerConfig.SHOW_COMMENT_ALL:
                commentRadioGroup.check(R.id.show_comment);
                break;
            case PlayerConfig.SHOW_COMMENT_LIST:
                commentRadioGroup.check(R.id.show_comment_list);
                break;
            case PlayerConfig.DISMISS_COMMENT:
                commentRadioGroup.check(R.id.hide_comment);
                break;
        }
        int littleCommentConfig = LittleVideoConfig.getInstance().getCommentType().getValue();
        switch (littleCommentConfig) {
            case PlayerConfig.SHOW_COMMENT_ALL:
                liitleCommentRadioGroup.check(R.id.show_comment_little);
                break;
            case PlayerConfig.SHOW_COMMENT_LIST:
                liitleCommentRadioGroup.check(R.id.show_comment_list_little);
                break;
            case PlayerConfig.DISMISS_COMMENT:
                liitleCommentRadioGroup.check(R.id.hide_comment_little);
                break;
        }
        switch (littleVideoStyle) {
            case STYLE_RIGHT:
                littleVideoToolRadioGroup.check(R.id.tool_right);
                break;
            case STYLE_BOTTOM:
                littleVideoToolRadioGroup.check(R.id.tool_bottom);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.show_comment:
                PlayerConfig.getInstance().setCommentType(PlayerConfig.SHOW_COMMENT_ALL);
                break;
            case R.id.show_comment_list:
                PlayerConfig.getInstance().setCommentType(PlayerConfig.SHOW_COMMENT_LIST);
                break;
            case R.id.hide_comment:
                PlayerConfig.getInstance().setCommentType(PlayerConfig.DISMISS_COMMENT);
                break;

            case R.id.tool_bottom:
                LittleVideoConfig.getInstance().setLittleVideoStyle(LittleVideoConfig.LittleVideoStyle.STYLE_BOTTOM);
                break;
            case R.id.tool_right:
                LittleVideoConfig.getInstance().setLittleVideoStyle(LittleVideoConfig.LittleVideoStyle.STYLE_RIGHT);
                break;
            case R.id.show_comment_little:
                YLUIConfig.getInstance().littleComment(CommentConfig.CommentType.SHOW_COMMENT_ALL);
                break;
            case R.id.show_comment_list_little:
                YLUIConfig.getInstance().littleComment(CommentConfig.CommentType.SHOW_COMMENT_LIST);
                break;
            case R.id.hide_comment_little:
                YLUIConfig.getInstance().littleComment(CommentConfig.CommentType.DISMISS_COMMENT);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.little_like:
                YLUIConfig.getInstance().littleLikeShow(isChecked);
                break;

            case R.id.little_share:
                YLUIConfig.getInstance().littleShareShow(isChecked);
                break;

            case R.id.video_like:
                YLUIConfig.getInstance().videoLikeShow(isChecked);
                break;

            case R.id.video_share:
                YLUIConfig.getInstance().videoShareShow(isChecked);
                break;
            case R.id.player_avatar:
                YLUIConfig.getInstance().feedAvatarClickable(isChecked);
                break;
            case R.id.video_refresh:
                YLUIConfig.getInstance().feedSwipeRefreshEnable(isChecked);
                break;
            case R.id.guanzhu:
                YLUIConfig.getInstance().followAvailable(isChecked);
                break;
            case R.id.share_pa:
                YLUIInit.getInstance().appendSharePa(isChecked);
                break;
        }
    }
}
