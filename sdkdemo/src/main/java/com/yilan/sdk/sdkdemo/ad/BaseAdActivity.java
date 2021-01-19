package com.yilan.sdk.sdkdemo.ad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yilan.sdk.common.ui.dialog.LoadingDialog;

public class BaseAdActivity extends AppCompatActivity {
    protected LoadingDialog dialog;
    protected boolean succeed = false;
    protected AppCompatActivity context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        dialog = new LoadingDialog(this);
    }
}
