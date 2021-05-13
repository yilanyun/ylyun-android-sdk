/*
 * Copyright (C) 2019 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.yilan.sdk.sdkdemo.view.guide;

/**
 * 绘制自定义View监听
 */
public interface TipCircularDrawListener {
    void drawStart();
    boolean drawEnd();//绘制结束，根据返回值判断是否继续执行动画
    void AnimEnd();//动画结束
}
