package com.yilan.sdk.sdkdemo.ad.videoList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yilan.sdk.data.entity.MediaInfo;
import com.yilan.sdk.player.ylplayer.PlayerStyle;
import com.yilan.sdk.player.ylplayer.YLPlayerView;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.uibase.ui.adapter.viewholder.ViewHolder;

/**
 * Author And Date: liurongzhi on 2021/1/16.
 * Description: com.yilan.sdk.sdkdemo.ad.feed
 */
class VideoViewHolder extends ViewHolder {
    public YLPlayerView playerView;
    public MediaInfo data;
    public VideoViewHolder(Context context, ViewGroup parent) {
        super(context, LayoutInflater.from(context).inflate(R.layout.holder_video_list, parent, false));
        initView();
    }

    protected void initView() {
        playerView = itemView.findViewById(R.id.player_view);
        playerView.setStyle(PlayerStyle.STYLE_UGC);
        playerView.setLooping(true);
    }

    public void onBindViewHolder(MediaInfo data) {
        this.data = data;
    }
}
