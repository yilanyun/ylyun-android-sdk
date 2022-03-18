package com.yilan.sdk.sdkdemo.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.yilan.sdk.common.util.ToastUtil;
import com.yilan.sdk.player.ylplayer.TaskInfo;
import com.yilan.sdk.player.ylplayer.callback.OnSimplePlayerCallBack;
import com.yilan.sdk.player.ylplayer.engine.IYLPlayerEngine;
import com.yilan.sdk.player.ylplayer.engine.YLPlayerFactory;
import com.yilan.sdk.sdkdemo.R;

public class PlayerActivity extends AppCompatActivity {
    IYLPlayerEngine playerEngine;
    FrameLayout playerContainer;
    EditText editText;
    EditText preEditText;
    String url = "https://vv.qianpailive.com/7d80/20210714/dfdb4ee9b51c121c7127187ed4411863?auth_key=1626283328-0-0-9b10fd44889fa42ebab0200477b07554";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        playerContainer = findViewById(R.id.player_container);
        editText = findViewById(R.id.edit_input_url);
        preEditText = findViewById(R.id.edit_input_preurl);
        playerEngine = YLPlayerFactory.createEngine(playerContainer);
        playerEngine.play(new TaskInfo.Builder().videoID("adfadffwe121").url(url).coverID(R.id.img_cover).build(), playerContainer);
        playerEngine.setPlayerCallBack(new OnSimplePlayerCallBack() {
            @Override
            public void onError(String pager, String videoID, String taskID) {
                super.onError(pager, videoID, taskID);
                ToastUtil.show(PlayerActivity.this, "播放失败");
            }
        });
        findViewById(R.id.onPlay).setOnClickListener((v) -> {
            onPlay();
        });
        findViewById(R.id.onPause).setOnClickListener((v) -> {
            onPauseClick();
            playerContainer.requestLayout();
        });
        findViewById(R.id.playWithUrl).setOnClickListener((v) -> {
            playWithUrl();
        });
        findViewById(R.id.preloadUrl).setOnClickListener((v) -> {
            preloadUrl();
        });
        findViewById(R.id.playPreLoadUrl).setOnClickListener((v) -> {
            playPreLoadUrl();
        });
        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerEngine.stop();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (playerEngine != null) {
            playerEngine.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (playerEngine != null) {
            playerEngine.resume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (playerEngine != null) {
            playerEngine.release();
        }
    }

    /**
     * 点击播放
     */
    public void onPlay() {
        if (playerEngine != null) {
            playerEngine.resume();
        }
    }

    /**
     * 点击暂停
     */
    public void onPauseClick() {
        if (playerEngine != null) {
            playerEngine.pause();
        }
    }

    /**
     * 从edit的url
     */
    public void playWithUrl() {
        String url = editText.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            playerEngine.play(new TaskInfo.Builder().videoID("playurl").url(url).coverID(R.id.img_cover).build(), playerContainer);
        }
    }

    public void preloadUrl() {
        String url = preEditText.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            playerEngine.prePlay(new TaskInfo.Builder().videoID("preplayer001").url(url).coverID(R.id.img_cover).build());
        }
    }

    public void playPreLoadUrl() {
        String url = preEditText.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            playerEngine.play(new TaskInfo.Builder().videoID("preplayer001").url(url).coverID(R.id.img_cover).build(), playerContainer);
        }
    }
}