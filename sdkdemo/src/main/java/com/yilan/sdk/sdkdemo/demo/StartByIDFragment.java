package com.yilan.sdk.sdkdemo.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yilan.sdk.common.util.ToastUtil;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.ui.little.YLLittleVideoActivity;
import com.yilan.sdk.ui.video.VideoActivity;

public class StartByIDFragment extends Fragment {

    EditText edit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start_byid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        edit = view.findViewById(R.id.edit);
        view.findViewById(R.id.start_feed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFeedDetail(v);
            }
        });

        view.findViewById(R.id.start_little).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLittle(v);
            }
        });
    }

    private void startLittle(View v) {
        String s = edit.getText().toString();
        if (TextUtils.isEmpty(s)) {
            ToastUtil.show(v.getContext(), "请输入视频ID");
            return;
        }
        YLLittleVideoActivity.start(v.getContext(), s);
    }

    private void startFeedDetail(View v) {
        String s = edit.getText().toString();
        if (TextUtils.isEmpty(s)) {
            ToastUtil.show(v.getContext(), "请输入视频ID");
            return;
        }
        VideoActivity.start(v.getContext(), s);
    }
}
