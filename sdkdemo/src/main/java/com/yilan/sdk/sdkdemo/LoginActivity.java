package com.yilan.sdk.sdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yilan.sdk.data.entity.user.UserEntity;
import com.yilan.sdk.data.user.YLUser;
import com.yilan.sdk.ui.web.WebActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nick = ((EditText) findViewById(R.id.nick)).getText().toString();
                String avatar = ((EditText) findViewById(R.id.avatar)).getText().toString();
                String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
                String userId = ((EditText) findViewById(R.id.userid)).getText().toString();

                YLUser.getInstance().login(nick, avatar, phone, userId);
            }
        });
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YLUser.getInstance().logout();
            }
        });
        findViewById(R.id.gettoken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEntity userEntity = YLUser.getInstance().getUser();
                if (userEntity == null) {
                    Toast.makeText(LoginActivity.this, "user: ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "user: " + userEntity.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        findViewById(R.id.openweb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://openpre.yladm.com/ylm/index.html?id=PKjWX6Nez5L6&pl=1";
                String web = ((EditText) findViewById(R.id.web)).getText().toString();
                if (!TextUtils.isEmpty(web)) {
                    url = web;
                }
                WebActivity.start(LoginActivity.this, url, "");
            }
        });
    }

}
