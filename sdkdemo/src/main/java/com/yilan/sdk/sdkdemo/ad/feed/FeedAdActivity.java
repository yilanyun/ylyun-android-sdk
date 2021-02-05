package com.yilan.sdk.sdkdemo.ad.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.sdkdemo.ad.BaseAdActivity;
import com.yilan.sdk.ylad.YLAdListener;
import com.yilan.sdk.ylad.constant.YLAdConstants;
import com.yilan.sdk.ylad.manager.YLAdManager;

import java.util.ArrayList;

/**
 * Author And Date: liurongzhi on 2021/1/15.
 * Description: com.yilan.sdk.sdkdemo.ad.feed
 */
public class FeedAdActivity extends BaseAdActivity {
    private RecyclerView recyclerView;
    private ArrayList arrayList;
    private YLAdManager adManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_ad);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();

        //创建广告管理器
        adManager = YLAdManager.with(this);
        adManager.setAdListener(new YLAdListener(){
            @Override
            public void onSuccess(String adType, int source, String reqId, String pid) {
                super.onSuccess(adType, source, reqId, pid);
            }

            @Override
            public void onError(String adType, int source, String reqId, int code, String msg, String pid) {
                super.onError(adType, source, reqId, code, msg, pid);
            }
        });

        for (int i = 0; i < 30; i++) {
            arrayList.add(i + "");
        }

        //通过广告管理器，将广告插入到列表数据中
        adManager.insertEngineByName(YLAdConstants.AdName.FEED, arrayList);


        RecyclerView.Adapter adapter = new FeedAdapter(arrayList);
        recyclerView.setAdapter(adapter);

    }


    public class FeedAdapter extends RecyclerView.Adapter {
        private ArrayList arrayList;

        public FeedAdapter(ArrayList arrayList) {
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new FeedAdViewHolder(viewGroup.getContext(), viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder instanceof FeedAdViewHolder) {
                ((FeedAdViewHolder) viewHolder).onBindViewHolder(arrayList.get(i));
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
}
