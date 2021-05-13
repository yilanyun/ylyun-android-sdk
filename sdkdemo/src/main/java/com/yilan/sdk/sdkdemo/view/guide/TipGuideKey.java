package com.yilan.sdk.sdkdemo.view.guide;

/**
 * 引导View的key，根据此key判断是否展示
 * */
public enum TipGuideKey {

    KEY_MENU_SETTING("KEY_MENU_SETTING"),//设置
    KEY_LOGIN("KEY_LOGIN"),//登录
    KEY_LITTLE_DP_BOTTOM("KEY_LITTLE_DP_BOTTOM"),//设置
    KEY_LITTLE_HOT_BOTTOM("KEY_LITTLE_HOT_BOTTOM"),//登录
    KEY_LITTLE_RIGHT_VIEW("KEY_LITTLE_RIGHT_VIEW"),//设置

    KEY_FEED_TAB("KEY_FEED_TAB"),//feed
    KEY_FEED_IMG_HEAD("KEY_FEED_IMG_HEAD"),//feed
    KEY_FEED_SOCIAL_GROUP("KEY_FEED_SOCIAL_GROUP"),//feed

    DEFAULT("");

    public String key;

    TipGuideKey(String key) {
        this.key = key;
    }
}
