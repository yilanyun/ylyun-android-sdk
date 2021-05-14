package com.yilan.sdk.sdkdemo.util;

import android.content.Context;
import android.view.View;

import com.yilan.sdk.common.net.YLCommonRequest;
import com.yilan.sdk.data.entity.MediaInfo;
import com.yilan.sdk.data.entity.MediaList;
import com.yilan.sdk.data.net.Path;
import com.yilan.sdk.data.net.Urls;
import com.yilan.sdk.data.net.YLCallBack;
import com.yilan.sdk.data.net.request.IYLDataRequest;
import com.yilan.sdk.ui.album.LittleAlbumActivity;

import java.util.HashMap;

public class Utils {
    public static String getSourceName(int source) {
        String sourceName = "";
        if (source == 202) {
            sourceName = "一览";
        } else if (source == 23) {
            sourceName = "快手";
        } else if (source == 20 || source == 4) {
            sourceName = "穿山甲";
        } else if (source == 21 || source == 22) {
            sourceName = "广点通";
        }
        return sourceName;
    }

    /**
     * 获取view的高度和宽度，未渲染前
     *
     * @return [0] width [1]height
     */
    public static int[] getViewSizeUNSPECIFIED(View view) {
        int[] array = new int[2];
        final int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        array[0] = view.getMeasuredWidth();
        array[1] = view.getMeasuredHeight();
        return array;
    }

    public static void startAlbumActivityById(final Context context, String albumId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("album_id", albumId);
        params.put("order_num", "0");
        params.put("load_type", "1");
        YLCommonRequest.request.requestGet(Urls.getCommonUrl(Path.VIDEO_UGC_ALBUM), params, new YLCallBack<MediaList>() {
            @Override
            public void onSuccess(MediaList entity) {
                if (entity != null && entity.getData() != null && !entity.getData().isEmpty()) {
                    MediaInfo mediaInfo = entity.getData().get(0);
                    LittleAlbumActivity.start(context, mediaInfo);
                }
            }

            @Override
            public void onError(int httpCode, String ylCode, String msg) {
            }
        });
    }

    public static void getMediaById(final Context context,String videoId) {
        IYLDataRequest.REQUEST.getDetailFeed(videoId, "", new YLCallBack<MediaList>() {
            @Override
            public void onSuccess(MediaList data) {
                if (data != null && data.getData() != null && !data.getData().isEmpty()) {
                    MediaInfo info = data.getData().get(0);
                }
            }

            @Override
            public void onError(int httpCode, String ylCode, String msg) {

            }
        });
    }
}
