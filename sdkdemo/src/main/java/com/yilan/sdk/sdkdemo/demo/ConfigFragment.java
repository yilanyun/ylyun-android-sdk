package com.yilan.sdk.sdkdemo.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.ui.configs.CommentConfig;
import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.ui.configs.LittleVideoConfig;
import com.yilan.sdk.ui.configs.PlayerConfig;
import com.yilan.sdk.ui.configs.YLUIConfig;

import static com.yilan.sdk.ui.configs.CommentConfig.CommentType.DISMISS_COMMENT;
import static com.yilan.sdk.ui.configs.CommentConfig.CommentType.SHOW_COMMENT_ALL;
import static com.yilan.sdk.ui.configs.CommentConfig.CommentType.SHOW_COMMENT_LIST;

public class ConfigFragment extends Fragment {

    public static ConfigFragment newInstance(String param1, String param2) {
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
        final CommentConfig.CommentType commentType = LittleVideoConfig.getInstance().getCommentType();
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
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (commentType) {
                    case SHOW_COMMENT_ALL:
                        LittleVideoConfig.getInstance().setCommentType(SHOW_COMMENT_ALL);
                        break;
                    case SHOW_COMMENT_LIST:
                        LittleVideoConfig.getInstance().setCommentType(SHOW_COMMENT_LIST);
                        break;
                    case DISMISS_COMMENT:
                        LittleVideoConfig.getInstance().setCommentType(DISMISS_COMMENT);
                        break;
                }
            }
        });

        final Switch like = view.findViewById(R.id.little_like);
        like.setChecked(YLUIConfig.getInstance().islittleLikeShow());
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YLUIConfig.getInstance().littleLikeShow(like.isChecked());
            }
        });

        Switch share = view.findViewById(R.id.little_share);
        share.setChecked(YLUIConfig.getInstance().islittleShareShow());
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YLUIConfig.getInstance().littleShareShow(like.isChecked());
            }
        });
    }

    private void initFeed(View view) {
        final CommentConfig.CommentType commentType = PlayerConfig.getInstance().getCommentType();
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
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (commentType) {
                    case SHOW_COMMENT_ALL:
                        PlayerConfig.getInstance().setCommentType(SHOW_COMMENT_ALL);
                        break;
                    case SHOW_COMMENT_LIST:
                        PlayerConfig.getInstance().setCommentType(SHOW_COMMENT_LIST);
                        break;
                    case DISMISS_COMMENT:
                        PlayerConfig.getInstance().setCommentType(DISMISS_COMMENT);
                        break;
                }
            }
        });

        final Switch like = view.findViewById(R.id.feed_like);
        like.setChecked(YLUIConfig.getInstance().isVideoLikeShow());
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YLUIConfig.getInstance().videoLikeShow(like.isChecked());
            }
        });

        final Switch share = view.findViewById(R.id.feed_share);
        share.setChecked(YLUIConfig.getInstance().isVideoShareShow());
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YLUIConfig.getInstance().videoShareShow(share.isChecked());
            }
        });

    }

    private void initDetail(View view) {
        final Switch head = view.findViewById(R.id.detail_head);
        head.setChecked(FeedConfig.getInstance().getAvatarClickable());
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YLUIConfig.getInstance().feedAvatarClickable(head.isChecked());
            }
        });

        final Switch follow = view.findViewById(R.id.detail_follow);
        follow.setChecked(FeedConfig.getInstance().getFollowShow());
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YLUIConfig.getInstance().followAvailable(follow.isChecked());
            }
        });
    }
}