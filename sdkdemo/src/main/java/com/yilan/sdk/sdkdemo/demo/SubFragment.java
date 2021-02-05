package com.yilan.sdk.sdkdemo.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.yilan.sdk.common.util.FSScreen;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.ui.stream.StreamManager;
import com.yilan.sdk.ui.stream.StreamOption;
import com.yilan.sdk.ui.stream.feed.FeedStream;
import com.yilan.sdk.ui.stream.little.LittleStream;
import com.yilan.sdk.ui.stream.model.DataObtainMode;
import com.yilan.sdk.ui.stream.model.RequestMode;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private static final String SUB_TYPE = "sub_type";
    public static final int LITTLE_CARD = 0;
    public static final int LITTLE_SINGLE = 1;
    public static final int FEED_SINGLE = 2;

    private int subType;
    FeedStream feedStream;
    LittleStream littleStream;
    LittleStream cardStream;
    private ViewGroup container;
    private AppCompatSeekBar seekBar;

    public static SubFragment newInstance(int subType) {
        SubFragment fragment = new SubFragment();
        Bundle args = new Bundle();
        args.putInt(SUB_TYPE, subType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subType = getArguments().getInt(SUB_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sub, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        container = view.findViewById(R.id.content_layout);
        seekBar = view.findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(this);
        if (subType == LITTLE_CARD) {
            showLittleCard(view);
        } else if (subType == LITTLE_SINGLE) {
            showLittleSingle(view);
        } else if (subType == FEED_SINGLE) {
            showFeedSingle(view);
        }
    }


    private void showLittleCard(View view) {
        if (cardStream == null) {
            cardStream = StreamManager.create().getCardStream();
        }
        StreamOption.Builder builder = new StreamOption.Builder()
                .videoNum(4)
                .requestMode(RequestMode.ALWAYS_NEW)
                .dataObtainMode(DataObtainMode.LOOP);
        cardStream.option(builder.build()).load(view.getContext()).into(container);
    }

    private void showLittleSingle(View view) {
        if (littleStream == null) {
            littleStream = StreamManager.create().getLittleStream();
            int screenWidth = FSScreen.getScreenWidth();
            ViewGroup.LayoutParams params = container.getLayoutParams();
            params.width = screenWidth / 2;
            container.setLayoutParams(params);
        }
        StreamOption.Builder builder = new StreamOption.Builder()
                .videoNum(4)
                .requestMode(RequestMode.ALWAYS_NEW)
                .dataObtainMode(DataObtainMode.RANDOM);
        littleStream.option(builder.build()).load(view.getContext()).into(container);
    }

    private void showFeedSingle(View view) {
        if (feedStream == null) {
            feedStream = StreamManager.create().getFeedStream();
        }
        StreamOption.Builder builder = new StreamOption.Builder()
                .videoNum(4)
                .requestMode(RequestMode.ALWAYS_NEW)
                .dataObtainMode(DataObtainMode.LOOP);
        feedStream.option(builder.build()).load(view.getContext()).into(container);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int screenWidth = FSScreen.getScreenWidth();
        ViewGroup.LayoutParams params = container.getLayoutParams();
        params.width = screenWidth * seekBar.getProgress() / 100;
        container.setLayoutParams(params);
    }
}