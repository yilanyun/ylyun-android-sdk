package com.yilan.sdk.sdkdemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import com.yilan.sdk.data.YLInit;
import com.yilan.sdk.sdkdemo.ad.ADTestActivity;
import com.yilan.sdk.sdkdemo.netstate.NetStateActivity;
import com.yilan.sdk.sdkdemo.stream.StreamTestActivity;
import com.yilan.sdk.ui.YLUIInit;
import com.yilan.sdk.ui.configs.CommentConfig;
import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.ui.configs.LittleVideoConfig;
import com.yilan.sdk.ui.configs.PlayerConfig;
import com.yilan.sdk.ui.configs.YLUIConfig;

import java.util.HashMap;

public class ConfigActivity extends AppCompatActivity implements
        RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

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

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfigActivity.this, NetStateActivity.class));
            }
        });

        findViewById(R.id.user_tag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = "0x112uuwqwe";
                HashMap<String, Object> map = new HashMap<>();
                map.put("gender", 1);
                map.put("age", "23,26");
                YLUIConfig.getInstance().setUserTag(userId, map);
            }
        });

        findViewById(R.id.ad_entrance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfigActivity.this, ADTestActivity.class));
            }
        });
    }

    private void initViews() {
        CommentConfig.CommentType commentConfig = PlayerConfig.getInstance().getCommentType();
        switch (commentConfig) {
            case SHOW_COMMENT_ALL:
                commentRadioGroup.check(R.id.show_comment);
                break;
            case SHOW_COMMENT_LIST:
                commentRadioGroup.check(R.id.show_comment_list);
                break;
            case DISMISS_COMMENT:
                commentRadioGroup.check(R.id.hide_comment);
                break;
        }
        switch (LittleVideoConfig.getInstance().getCommentType()) {
            case SHOW_COMMENT_ALL:
                liitleCommentRadioGroup.check(R.id.show_comment_little);
                break;
            case SHOW_COMMENT_LIST:
                liitleCommentRadioGroup.check(R.id.show_comment_list_little);
                break;
            case DISMISS_COMMENT:
                liitleCommentRadioGroup.check(R.id.hide_comment_little);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.show_comment:
                PlayerConfig.getInstance().setCommentType(CommentConfig.CommentType.SHOW_COMMENT_ALL);
                break;
            case R.id.show_comment_list:
                PlayerConfig.getInstance().setCommentType(CommentConfig.CommentType.SHOW_COMMENT_LIST);
                break;
            case R.id.hide_comment:
                PlayerConfig.getInstance().setCommentType(CommentConfig.CommentType.DISMISS_COMMENT);
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


    @SuppressLint("NonConstantResourceId")
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void testStream(View view) {
        startActivity(new Intent(ConfigActivity.this, StreamTestActivity.class));
    }

}
