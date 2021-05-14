package com.yilan.sdk.sdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yilan.sdk.sdkdemo.ad.ADTestActivity;
import com.yilan.sdk.sdkdemo.data.DataRequestActivity;
import com.yilan.sdk.sdkdemo.netstate.NetStateActivity;
import com.yilan.sdk.sdkdemo.stream.StreamTestActivity;


public class MyFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        TextView version = view.findViewById(R.id.version);
        version.setText(BuildConfig.VERSION_NAME);
        view.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.config).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfigActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.net_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NetStateActivity.class));
            }
        });
        view.findViewById(R.id.ad_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADTestActivity.start(getContext());
            }
        });
        view.findViewById(R.id.stream_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), StreamTestActivity.class));
            }
        });

        view.findViewById(R.id.data_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DataRequestActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
