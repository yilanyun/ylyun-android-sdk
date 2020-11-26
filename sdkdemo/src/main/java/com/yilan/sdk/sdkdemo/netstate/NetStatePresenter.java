package com.yilan.sdk.sdkdemo.netstate;

/**
 * Author And Date: liurongzhi on 2020/7/21.
 * Description: com.yilan.sdk.sdkdemo.netstate
 */
public class NetStatePresenter {
    private NetStateActivity activity;
    private NetStateModel model;

    public NetStatePresenter(NetStateActivity activity) {
        this.activity = activity;
        model = new NetStateModel(this);
    }

    protected void initData() {
        if (activity != null) {
            model.checkNet(activity);
        }
    }

    public void update(String content) {
        if (activity != null) {
            activity.contentView.setText(content);
        }
    }

    public void destroy() {
        activity = null;
    }
}
