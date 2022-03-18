package com.yilan.sdk.sdkdemo.demo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.yilan.sdk.reprotlib.body.player.IPlayerReporter;
import com.yilan.sdk.reprotlib.body.player.PlayData;
import com.yilan.sdk.reprotlib.body.player.UGCReporter;
import com.yilan.sdk.sdkdemo.R;

import java.io.IOException;

public class PlayerReportActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    private Button play;
    private Button pause;
    private Button stop;
    private MediaPlayer player;
    private IPlayerReporter reporter;
    private PlayData data;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_report);
        surfaceView = findViewById(R.id.surface_view);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        stop = findViewById(R.id.stop);
        player = new MediaPlayer();
        player.setScreenOnWhilePlaying(true);
        reporter = new UGCReporter();
        handler = new Handler();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                player.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (player.isPlaying()) {
                        player.stop();
                    }
                    /**
                     * 点击播放时
                     */
                    data = PlayData.createData("videoid", "https://vv.qianpailive.com/d207/20210111/419a60f944467accf981d3983d2d3705");
                    reporter.onPlay(data);
                    player.setLooping(true);
                    player.setDataSource(data.getPlayUrl());
                    player.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                reporter.onStop(data);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.isPreparing()) return;
                if (player.isPlaying()) {
                    player.pause();
                    reporter.onPause(data);
                    stopTask();
                } else {
                    player.start();
                    reporter.onResume(data);
                    startTask();
                }
            }
        });

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                lastTime = 0;
                isStuck = false;
                data.duration = mp.getDuration();
                data.pos = 0;
                /**
                 * 开始播放
                 */
                reporter.onStart(data);
                player.start();
                startTask();
            }
        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                /**
                 * 播放完毕
                 */
                reporter.onComplete(data);
            }
        });
        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (data.isPreparing()) {
                    /**
                     * 如果当前状态是正在加载，则调用onPrepareError
                     */
                    reporter.onPrepareError(data, "what:" + what + "  extra:" + extra);
                } else {
                    /**
                     * 调用onError
                     */
                    reporter.onError(data, "what:" + what + "  extra:" + extra);
                    stopTask();
                }
                return true;
            }
        });
    }

    public long lastTime = 0;
    public boolean isStuck = false;
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            data.pos = player.getCurrentPosition();
            if (data.duration - lastTime < 500 && data.pos < 500) {
                /**
                 * 播放完毕
                 */
                reporter.onComplete(data);
            }
            if (data.pos == lastTime) {
                //卡住了
                isStuck = true;
                /**
                 * 卡顿开始
                 */
                reporter.onBufferStart(data);
            } else if (isStuck) {
                isStuck = false;
                /**
                 * 卡顿结束
                 */
                reporter.onBufferEnd(data);
            }
            lastTime = data.pos;
            handler.postDelayed(task, 500);
        }
    };

    public void startTask() {
        handler.post(task);
    }

    public void stopTask() {
        handler.removeCallbacks(task);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(task);
    }
}