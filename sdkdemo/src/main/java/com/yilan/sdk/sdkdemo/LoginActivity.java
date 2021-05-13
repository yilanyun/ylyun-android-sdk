package com.yilan.sdk.sdkdemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.yilan.sdk.common.util.YLUIUtil;
import com.yilan.sdk.sdkdemo.demo.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginFragment fragment = LoginFragment.newInstance();
        YLUIUtil.FragmentOperate.with(getSupportFragmentManager())
                .replace(R.id.fragment_container, fragment);
        if (getIntent().getData()!=null) {
            Uri data = getIntent().getData();
            Toast.makeText(this, data.getQueryParameter("goodid"), Toast.LENGTH_SHORT).show();
        }
    }

}
