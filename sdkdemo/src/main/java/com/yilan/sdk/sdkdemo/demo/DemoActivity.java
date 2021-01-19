package com.yilan.sdk.sdkdemo.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.ui.category.ChannelFragment;
import com.yilan.sdk.ui.littlevideo.KSLittleVideoFragment;

public class DemoActivity extends AppCompatActivity {

    private static final int TYPE_FEED = 0;
    private static final int TYPE_KS = 1;
    private static final int TYPE_SINGLE = 2;
    private int type = -1;
    private int subType = -1;
    private FragmentManager manager;

    public static void startFeed(Context context) {
        Intent intent = new Intent(context, DemoActivity.class);
        intent.putExtra("type", TYPE_FEED);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void startKs(Context context) {
        Intent intent = new Intent(context, DemoActivity.class);
        intent.putExtra("type", TYPE_KS);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void startSingle(Context context,int subType) {
        Intent intent = new Intent(context, DemoActivity.class);
        intent.putExtra("type", TYPE_SINGLE);
        intent.putExtra("subType", subType);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        if (getIntent() != null) {
            type = getIntent().getIntExtra("type", -1);
            subType = getIntent().getIntExtra("subType", -1);
        }
        manager = getSupportFragmentManager();
        Fragment fragment = getFragmentByType();
        if (fragment != null) {
            manager.beginTransaction().replace(R.id.content_layout, fragment).commitAllowingStateLoss();
        }
    }

    private Fragment getFragmentByType() {
        if (type == TYPE_KS) {
            return KSLittleVideoFragment.newInstance();
        } else if (type == TYPE_FEED) {
            return new ChannelFragment();
        } else if (type == TYPE_SINGLE) {
            return SubFragment.newInstance(subType);
        }
        return null;
    }
}