package com.yilan.sdk.sdkdemo.ad.videoList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yilan.sdk.data.entity.IAdEngine;
import com.yilan.sdk.data.entity.MediaInfo;
import com.yilan.sdk.data.entity.Play;
import com.yilan.sdk.player.ylplayer.YLPlayerView;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.sdkdemo.ad.BaseAdActivity;
import com.yilan.sdk.ylad.YLAdListener;
import com.yilan.sdk.ylad.constant.YLAdConstants;
import com.yilan.sdk.ylad.manager.YLAdManager;
import com.yl.metadata.ALVideoServer;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.Adapter;
import static android.support.v7.widget.RecyclerView.OnScrollListener;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Author And Date: liurongzhi on 2021/1/15.
 * Description: com.yilan.sdk.sdkdemo.ad.feed
 */
public class VideoListAdActivity extends BaseAdActivity {
    private RecyclerView recyclerView;
    private ArrayList arrayList;
    private YLAdManager adManager;
    private YLPlayerView playerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_ad);
        recyclerView = findViewById(R.id.recycle_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        PagerSnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(recyclerView);
        //创建广告管理器
        adManager = YLAdManager.with(this);
        adManager.setAdListener(new YLAdListener() {
            @Override
            public void onSuccess(String adType, int source, String reqId, String pid) {
                super.onSuccess(adType, source, reqId, pid);
            }

            @Override
            public void onError(String adType, int source, String reqId, int code, String msg, String pid) {
                super.onError(adType, source, reqId, code, msg, pid);
            }

        });

        for (int i = 0; i < 10; i++) {
            MediaInfo info = new MediaInfo();
            info.setVideo_id("abc" + i);
            info.setComment_num(i * 15);
            info.setLike_num(i * 25);
            info.play = new Play();
            if (i % 3 == 0) {
                info.play.setUri("http://vv.qianpailive.com/d207/20210111/419a60f944467accf981d3983d2d3705");
            } else if (i % 3 == 1) {
                info.play.setUri("http://vv.qianpailive.com/39d6/20210112/510e5a6457f05028e5ff1660e485ab13");
            } else {
                info.play.setUri("http://vv.qianpailive.com/c0ec/20210105/0b1f5dadc983d4e7bd1e3e8ccc45ff68");
            }
            arrayList.add(info);
        }

        //通过广告管理器，将广告插入到列表数据中
        adManager.insertEngineByName(YLAdConstants.AdName.FEED_VERTICAL, arrayList);

        Adapter adapter = new VideoListAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == SCROLL_STATE_IDLE) {
                    play();
                }
            }
        });

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                play();
            }
        });

    }

    private void play() {
        int position = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        if (position == -1) return;
        ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(position);
        if (holder instanceof VideoViewHolder && ((VideoViewHolder) holder).data != null) {
            if (playerView == ((VideoViewHolder) holder).playerView) return;
            if (playerView != null) {
                playerView.stop();
            }
            playerView = ((VideoViewHolder) holder).playerView;
            playerView.setDataSource(ALVideoServer.instance().getProxyUrl(((VideoViewHolder) holder).data.play.getUri(), ((VideoViewHolder) holder).data.getVideo_id()));
            playerView.prepareAndPlay();
        } else {
            if (playerView != null) {
                playerView.stop();
            }
            playerView = null;
        }
    }

    public static class VideoListAdapter extends Adapter {
        private ArrayList arrayList;

        public VideoListAdapter(ArrayList arrayList) {
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
            if (type == 1) {
                return new VideoViewHolder(viewGroup.getContext(), viewGroup);
            } else {
                return new VideoAdViewHolder(viewGroup.getContext(), viewGroup);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            if (viewHolder instanceof VideoViewHolder) {
                ((VideoViewHolder) viewHolder).onBindViewHolder((MediaInfo) arrayList.get(i));
            } else {
                ((VideoAdViewHolder) viewHolder).onBindViewHolder((IAdEngine) arrayList.get(i));
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (arrayList.get(position) instanceof MediaInfo) {
                return 1;
            } else {
                return 2;
            }
        }

        @Override
        public int getItemCount() {
            if (arrayList != null) {
                return arrayList.size();
            }
            return 0;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (playerView != null) {
            playerView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playerView != null) {
            playerView.release();
        }
    }
}
