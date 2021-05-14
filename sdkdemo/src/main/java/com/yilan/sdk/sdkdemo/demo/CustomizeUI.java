package com.yilan.sdk.sdkdemo.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.sdk.player.R;
import com.yilan.sdk.player.ylplayer.PlayerState;
import com.yilan.sdk.player.ylplayer.ui.AbsYLPlayerUI;
import com.yilan.sdk.reprotlib.body.player.PlayData;

/**
 * Author And Date: liurongzhi on 2021/5/11.
 * Description: com.yilan.sdk.player.ylplayer.ui
 */
public class CustomizeUI extends AbsYLPlayerUI {
    /**
     * 创建播放器 ui view
     * @param parent
     * @return
     */
    @Override
    protected View OnCreateView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.yl_little_player_ui, null);
    }

    /**
     *
     * @return 返回layout的跟view 的id 必需，否则会影响ui 复用
     */
    @Override
    public int getRootID() {
        return R.id.controller_ugc;
    }

    /**
     * 播放器状态回调
     * @param data
     * @param oldState 上一次状态
     * @param newState 当前新状态
     */
    @Override
    public void onPlayStateChange(PlayData data, PlayerState oldState, PlayerState newState) {
        super.onPlayStateChange(data, oldState, newState);
    }

    /**
     * 播放进度回调
     * @param data  data.pos当前进度  data.duration 总时长
     */
    @Override
    public void onProgress(PlayData data) {
        super.onProgress(data);
    }

    /**
     * 播放器进入全屏状态
     */
    @Override
    public void onFull() {
        super.onFull();
    }

    /**
     * 播放器退出全屏
     */
    @Override
    public void onExitFull() {
        super.onExitFull();
    }

    /**
     * 播放器重置，请在此方法中重置 ui状态
     */
    @Override
    public void resetUI() {
        super.resetUI();
    }

    /**
     * 在此方法中显示正在加载的ui
     */
    @Override
    public void showBuffer() {
        super.showBuffer();
    }


    /**
     * 在此方法中关闭 正在加载的ui
     */
    @Override
    public void hideBuffer() {
        super.hideBuffer();
    }


}
