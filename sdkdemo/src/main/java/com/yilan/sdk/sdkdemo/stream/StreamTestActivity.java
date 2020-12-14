package com.yilan.sdk.sdkdemo.stream;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yilan.sdk.sdkdemo.BuildConfig;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.ui.stream.StreamManager;
import com.yilan.sdk.ui.stream.StreamOption;
import com.yilan.sdk.ui.stream.feed.FeedStream;
import com.yilan.sdk.ui.stream.little.LittleStream;
import com.yilan.sdk.ui.stream.model.DataObtainMode;
import com.yilan.sdk.ui.stream.model.RequestMode;


public class StreamTestActivity extends AppCompatActivity {

    FeedStream feedStream;
    LittleStream littleStream;
    LittleStream cardStream;

    FrameLayout feedContainer;
    FrameLayout littleContainer;
    FrameLayout bannerContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_test);
        TextView sdkVersion = findViewById(R.id.sdk_version);
        sdkVersion.setText(String.format("SDK Version %s  ", BuildConfig.VERSION_NAME));
        feedContainer = findViewById(R.id.feed_container);
        littleContainer = findViewById(R.id.little_container);
        bannerContainer = findViewById(R.id.little_banner_container);
        feedStream = StreamManager.create().getFeedStream();
        littleStream = StreamManager.create().getLittleStream();
        cardStream = StreamManager.create().getCardStream();
    }


    public void testFeed(View view) {
        StreamOption.Builder builder = new StreamOption.Builder()
                .videoNum(4)
                .requestMode(RequestMode.ALWAYS_NEW)
                .dataObtainMode(DataObtainMode.LOOP);
        feedStream.option(builder.build()).load(view.getContext()).into(feedContainer);
    }

    public void testLittle(View view) {
        StreamOption.Builder builder = new StreamOption.Builder()
                .videoNum(4)
                .requestMode(RequestMode.ALWAYS_NEW)
                .dataObtainMode(DataObtainMode.RANDOM);
        littleStream.option(builder.build()).load(view.getContext()).into(littleContainer);
    }

    public void testBanner(View view) {
        StreamOption.Builder builder = new StreamOption.Builder()
                .videoNum(4);
        cardStream.option(builder.build())
                .load(view.getContext())
                .into(bannerContainer);
    }

}