package com.yilan.sdk.sdkdemo.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.sdk.sdkdemo.BaseFragment;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.ui.configs.KSStyle;
import com.yilan.sdk.ui.configs.YLUIConfig;
import com.yilan.sdk.ui.little.LittlePageConfig;
import com.yilan.sdk.ui.little.YLLittleVideoActivity;

/**
 *
 */
public class FeedFragment extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_demo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.littleDraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLittleDraw(v);
            }
        });
        view.findViewById(R.id.feedCurrent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFeedCurrent(v);
            }
        });
        view.findViewById(R.id.feedDetail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFeedDetail(v);
            }
        });
        view.findViewById(R.id.ksGrid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKsGrid(v);
            }
        });
        view.findViewById(R.id.ksStaggered).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKsStaggered(v);
            }
        });
    }

    /**
     * 沉浸式信息流
     *
     * @param v
     */
    private void onClickLittleDraw(View v) {
        YLLittleVideoActivity.start(v.getContext(), LittlePageConfig.DefaultConfig());
    }

    /**
     * 抽屉式信息流-当前页播放
     *
     * @param v
     */
    private void onClickFeedCurrent(View v) {
        YLUIConfig.getInstance().feedPlayStyle(FeedConfig.STYLE_FEED_PLAY);
        DemoActivity.startFeed(v.getContext());
    }

    /**
     * 抽屉式信息流-播放页播放
     *
     * @param v
     */
    private void onClickFeedDetail(View v) {
        YLUIConfig.getInstance().feedPlayStyle(FeedConfig.STYLE_NATIVE);
        DemoActivity.startFeed(v.getContext());
    }

    /**
     * 宫格型信息流-整齐型
     *
     * @param v
     */
    private void onClickKsGrid(View v) {
        YLUIConfig.getInstance().setKsStyle(KSStyle.STYLE_GRID);
        DemoActivity.startKs(v.getContext());
    }

    /**
     * 宫格型信息流-错落型
     *
     * @param v
     */
    private void onClickKsStaggered(View v) {
        YLUIConfig.getInstance().setKsStyle(KSStyle.STYLE_STAGGER);
        DemoActivity.startKs(v.getContext());
    }

}