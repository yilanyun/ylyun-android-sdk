package com.yilan.sdk.sdkdemo.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.ylglide.Glide;
import com.yilan.sdk.data.entity.MediaInfo;
import com.yilan.sdk.data.entity.MediaList;
import com.yilan.sdk.data.net.YLCallBack;
import com.yilan.sdk.data.net.request.IYLDataRequest;
import com.yilan.sdk.player.ylplayer.PlayerState;
import com.yilan.sdk.player.ylplayer.engine.IYLPlayerEngine;
import com.yilan.sdk.player.ylplayer.engine.YLMultiPlayerEngine;
import com.yilan.sdk.player.ylplayer.ui.UGCPlayerUI;
import com.yilan.sdk.sdkdemo.R;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PlayerAdapter adapter;
    private IYLPlayerEngine engine;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ViewGroup container = findViewById(R.id.player_container);
        recyclerView = findViewById(R.id.recycle_view);
        engine = YLMultiPlayerEngine.getEngineByContainer(container).withController(new UGCPlayerUI());
        adapter = new PlayerAdapter(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (engine != null) {
                    if (engine.getPlayerState() == PlayerState.PAUSE) {
                        engine.resume();
                    } else if (engine.getPlayerState() == PlayerState.RESUME || engine.getPlayerState() == PlayerState.START) {
                        engine.pause();
                    }
                }
            }
        });
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        PagerSnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        getData();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    startVideo();
                }
            }
        });
    }

    public void getData() {
        IYLDataRequest.REQUEST.getSubFeed(1, 10, "", new YLCallBack<MediaList>() {
            @Override
            public void onSuccess(MediaList mediaList) {
                if (!mediaList.getData().isEmpty()) {
                    adapter.mediaInfos.addAll(mediaList.getData());
                    adapter.notifyDataSetChanged();
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            startVideo();
                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s, String s1) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (engine != null) {
            engine.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (engine != null) {
            engine.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (engine != null) {
            engine.release();
        }
    }

    private void startVideo() {
        int position = manager.findFirstCompletelyVisibleItemPosition();
        if (position >= 0) {
            PlayerViewHolder holder = (PlayerViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
            engine.play(adapter.mediaInfos.get(position), holder.imageView, R.id.cover);
        }
    }

    public static class PlayerAdapter extends RecyclerView.Adapter<PlayerViewHolder> {
        public ArrayList<MediaInfo> mediaInfos = new ArrayList<>();
        View.OnClickListener onClickListener;

        public PlayerAdapter(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        @NonNull
        @Override
        public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new PlayerViewHolder(viewGroup,onClickListener);
        }

        @Override
        public void onBindViewHolder(@NonNull PlayerViewHolder viewHolder, int i) {
            MediaInfo info = mediaInfos.get(i);
            Glide.with(viewHolder.imageView).load(info.getFImg()).into(viewHolder.imageView);
            viewHolder.title.setText(info.getTitle());
        }

        @Override
        public int getItemCount() {
            return mediaInfos.size();
        }
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title;
        public View.OnClickListener onClickListener;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }

        public PlayerViewHolder(ViewGroup parent,View.OnClickListener onClickListener) {
            this(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent, false));
        }

        public void initView() {
            imageView = itemView.findViewById(R.id.cover);
            title = itemView.findViewById(R.id.title);
            imageView.setOnClickListener(onClickListener);
        }
    }
}