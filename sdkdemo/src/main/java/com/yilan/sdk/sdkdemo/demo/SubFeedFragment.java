package com.yilan.sdk.sdkdemo.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.sdk.sdkdemo.R;

public class SubFeedFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sub_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.littleCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLittleCard(v);
            }
        });
        view.findViewById(R.id.littleSingle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLittleSingle(v);
            }
        });
        view.findViewById(R.id.feedSingle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLittleFeedSingle(v);
            }
        });
    }

    /**
     *
     * @param v
     */
    private void showLittleCard(View v) {
        DemoActivity.startSingle(v.getContext(),SubFragment.LITTLE_CARD);
    }

    /**
     *
     * @param v
     */
    private void showLittleSingle(View v) {
        DemoActivity.startSingle(v.getContext(),SubFragment.LITTLE_SINGLE);
    }

    /**
     *
     * @param v
     */
    private void showLittleFeedSingle(View v) {
        DemoActivity.startSingle(v.getContext(),SubFragment.FEED_SINGLE);
    }

}