package com.yilan.sdk.sdkdemo.ad.videoList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yilan.sdk.data.entity.IAdEngine;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.uibase.ui.adapter.viewholder.ViewHolder;

/**
 * Author And Date: liurongzhi on 2021/1/16.
 * Description: com.yilan.sdk.sdkdemo.ad.feed
 */
class VideoAdViewHolder extends ViewHolder {
    private ViewGroup adContainer;

    public VideoAdViewHolder(Context context, ViewGroup parent) {
        super(context, LayoutInflater.from(context).inflate(R.layout.holder_video_list_ad, parent, false));
        initView();
    }

    protected void initView() {
        adContainer = (ViewGroup) itemView;
    }

    public void onBindViewHolder(IAdEngine data) {
        data.reset();
        data.request(adContainer);
    }
}
