package com.yilan.sdk.sdkdemo.data;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yilan.sdk.common.Result;
import com.yilan.sdk.common.ui.mvp.YLBaseActivity;
import com.yilan.sdk.data.entity.CpListEntity;
import com.yilan.sdk.data.entity.MediaList;
import com.yilan.sdk.data.net.YLCallBack;
import com.yilan.sdk.data.net.request.IYLDataRequest;
import com.yilan.sdk.player.utils.PlayerUtil;
import com.yilan.sdk.reprotlib.ReporterEngine;
import com.yilan.sdk.reprotlib.body.UserEvent;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.ui.search.YlSearchActivity;
import com.yilan.sdk.ui.search.result.VideoType;
import com.yilan.sdk.ui.web.WebActivity;

/**
 * Author And Date: liurongzhi on 2021/5/8.
 * Description: com.yilan.sdk.sdkdemo.data
 */
public class DataRequestActivity extends YLBaseActivity<DataRequestPresenter> {
    private EditText editText;
    @Override
    public View onCreateContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_data_request,null);
    }

    @Override
    public void initView(View viewRoot) {
        findViewById(R.id.cp_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IYLDataRequest.REQUEST.getVideoUpdateByIDs("W1M2OAK1wjdJ", VideoType.TYPE_PGC, 0, 1, new YLCallBack<MediaList>() {
                    @Override
                    public void onSuccess(MediaList data) {
                        Toast.makeText(DataRequestActivity.this,"成功获取数据",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(int httpCode, String ylCode, String msg) {
                        Toast.makeText(DataRequestActivity.this,"获取数据失败："+msg,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        findViewById(R.id.cp_hot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IYLDataRequest.REQUEST.getHotCps(5, VideoType.TYPE_PGC, new YLCallBack<CpListEntity>() {
                    @Override
                    public void onSuccess(CpListEntity data) {
                        Toast.makeText(DataRequestActivity.this,"成功获取数据",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(int httpCode, String ylCode, String msg) {
                        Toast.makeText(DataRequestActivity.this,"获取数据失败："+msg,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.clear_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerUtil.clearCache(new Result<Boolean>() {
                    @Override
                    public void callBack(Boolean aBoolean) {
                        Toast.makeText(DataRequestActivity.this,"清除成功",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.search_video_pgc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YlSearchActivity.start(DataRequestActivity.this,VideoType.TYPE_PGC);
            }
        });
        editText = findViewById(R.id.url_edit);
        findViewById(R.id.test_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.start(DataRequestActivity.this,editText.getText().toString().trim(),"链接测试");
            }
        });
        findViewById(R.id.report_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReporterEngine.instance().reportUserEvent(UserEvent.BLACK_REPORT, "W1M2OAK1wjdJ", "xw32dsa2q", 5);
            }
        });
    }
}
