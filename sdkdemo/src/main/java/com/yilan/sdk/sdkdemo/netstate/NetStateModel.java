package com.yilan.sdk.sdkdemo.netstate;

import com.yilan.sdk.data.net.Net;
import com.yilan.sdk.data.net.NetCheckCallBack;

/**
 * Author And Date: liurongzhi on 2020/7/21.
 * Description: com.yilan.sdk.sdkdemo.netstate
 */
public class NetStateModel {
    private NetStatePresenter presenter;
    public NetStateModel(NetStatePresenter presenter){
        this.presenter = presenter;
    }
    public void checkNet(NetStateActivity netStateActivity) {
        Net.getInstance().checkNet(netStateActivity.getApplicationContext(), new NetCheckCallBack() {
            @Override
            public void onCallBack(String result) {
                presenter.update(result);
            }
        });

    }
}
