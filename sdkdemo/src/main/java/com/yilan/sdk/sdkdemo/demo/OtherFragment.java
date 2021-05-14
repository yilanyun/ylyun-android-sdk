package com.yilan.sdk.sdkdemo.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.sdkdemo.ad.ADTestActivity;

public class OtherFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_other, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.text_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLittleCard(v);
            }
        });
        view.findViewById(R.id.text_player).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPlayerActivity();
            }
        });

    }

    /**
     *
     * @param v
     */
    private void showLittleCard(View v) {
        ADTestActivity.start(getContext());
    }

    private void showPlayerActivity(){
        startActivity(new Intent(this.getActivity(), PlayerActivity.class));
    }


}