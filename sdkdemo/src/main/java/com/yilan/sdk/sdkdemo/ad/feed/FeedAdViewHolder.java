package com.yilan.sdk.sdkdemo.ad.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.uibase.ui.adapter.viewholder.ViewHolder;
import com.yilan.sdk.ylad.engine.IYLAdEngine;

/**
 * Author And Date: liurongzhi on 2021/1/16.
 * Description: com.yilan.sdk.sdkdemo.ad.feed
 */
class FeedAdViewHolder extends ViewHolder {
    private Object lastData;
    private TextView textView;
    private FrameLayout adContainer;
    public FeedAdViewHolder(Context context, ViewGroup parent) {
        super(context, LayoutInflater.from(context).inflate(R.layout.holder_feed_ad, parent, false));
        initView();
    }

    protected void initView() {
        textView = itemView.findViewById(R.id.feed_text);
        adContainer = itemView.findViewById(R.id.ad_container);
    }

    public void onBindViewHolder(Object data) {
        if (lastData instanceof IYLAdEngine && lastData != data) ((IYLAdEngine) lastData).reset();
        if (data instanceof String) {
            textView.setText((String) data);
        } else if (data instanceof IYLAdEngine) {
            //通过IAdEngine，请求广告
            ((IYLAdEngine) data).request(adContainer);
            lastData = data;
        }
    }
}
