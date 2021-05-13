/*
 * Copyright (C) 2019 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.yilan.sdk.sdkdemo.view.guide;


import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.yilan.sdk.common.util.FSScreen;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义组合View
 */
public class TipFrameView extends FrameLayout {
    private Context mContext;
    private String mContent;//当前展示的提示文字
    private View mView;//当前需要提示的View
    private TipLineView tipLineView;//线条
    private TipCircularView tipCircularView;//提示点
    private TipContentView tipContentView;//提示文字容器
    private TipLightView tipLightView;//高亮范围
    private TipContentView tipTagView;//提示语
    private float screenWidth;
    private float screenHeight;
    private OnClickListener mOnClickListener;
    private TipLightView.TipLightType mLightType = TipLightView.TipLightType.Circle;
    private int dashedColor = Color.parseColor("#FFFFFF");//虚线颜色
    private float dashedWidth;//虚线宽度
    private float dashedHeight;//虚线高度
    private float dashedDistances;//虚线间隔
    private Map<String, View> mViewMap = new LinkedHashMap<>();//需要引导的View集

    public TipFrameView(Context context) {
        super(context);
        initView(context);
    }

    public TipFrameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TipFrameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 设置监听
     */
    public TipFrameView setClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
        return this;
    }

    /**
     * 传入需要引导的View
     *
     * @param view    需要引导的View
     * @param content 引导文字
     */
    public TipFrameView setView(View view, String content) {
        if (mViewMap == null) {
            mViewMap = new LinkedHashMap<>();
        }
        if (!TextUtils.equals(mContent, content)) {
            mViewMap.put(content, view);
        }
        return this;
    }

    /**
     * 传入需要引导的View
     *
     * @param mViewMap 需要引导的View
     */
    public TipFrameView setView(LinkedHashMap<String, View> mViewMap) {
        if (this.mViewMap == null) {
            this.mViewMap = new LinkedHashMap<>();
        }
        if (!TextUtils.isEmpty(mContent)) {
            mViewMap.remove(mContent);
        }
        this.mViewMap.putAll(mViewMap);
        return this;
    }

    private void initView(Context context) {
        this.mContext = context;
        tipLineView = new TipLineView(mContext);
        tipCircularView = new TipCircularView(mContext);
        tipLightView = new TipLightView(mContext);
        tipContentView = new TipContentView(mContext);
        tipTagView = new TipContentView(mContext);
        screenHeight = FSScreen.getScreenHeight();
        screenWidth = FSScreen.getScreenWidth();

        tipTagView.setFillColor("#f59425")
                .setTColor("#FFFFFF")
                .setTSize(14f)
                .setRoundRadius(FSScreen.dip2px(40f))
                .setTPadding((int) FSScreen.dip2px(30f), (int) FSScreen.dip2px(12f), (int) FSScreen.dip2px(30f), (int) FSScreen.dip2px(12f));
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(mView);
                }
            }
        });
    }

    /**
     * 开始绘制布局
     */
    public TipFrameView startDraw() {
        removeAllViews();
        Iterator<String> keySet = mViewMap.keySet().iterator();
        if (keySet.hasNext()) {
            mContent = keySet.next();
            mView = mViewMap.get(mContent);
            createChildView();
        }
        return this;
    }

    public TipFrameView setLightType(TipLightView.TipLightType lightType) {
        if (lightType != null) {
            mLightType = lightType;
        }
        return this;
    }

    /**
     * 设置虚线属性
     *
     * @param dashedColor;//虚线颜色
     * @param dashedWidth;//虚线宽度
     * @param dashedHeight;//虚线高度
     * @param dashedDistances;//虚线间隔
     */
    public TipFrameView setDashedData(int dashedColor, float dashedWidth, float dashedHeight, float dashedDistances) {
        this.dashedColor = dashedColor;
        this.dashedWidth = dashedWidth;
        this.dashedHeight = dashedHeight;
        this.dashedDistances = dashedDistances;
        return this;
    }

    /**
     * 设置虚线属性
     *
     * @param dashedColor;//虚线颜色
     * @param dashedWidth;//虚线宽度
     * @param dashedHeight;//虚线高度
     * @param dashedDistances;//虚线间隔
     */
    public TipFrameView setDashedData(String dashedColor, float dashedWidth, float dashedHeight, float
            dashedDistances) {
        setDashedData(Color.parseColor(dashedColor), dashedWidth, dashedHeight, dashedDistances);
        return this;
    }

    /**
     * 更新提示文字
     */
    public TipFrameView upDataTagContent() {
        if (mViewMap != null) {
            if (TextUtils.isEmpty(mContent)) {
                tipTagView.setTContent(mViewMap.size() <= 1 ? "轻触关闭" : "点击继续");
            } else {
                tipTagView.setTContent(mViewMap.size() <= 0 ? "轻触关闭" : "点击继续");
            }
        }
        return this;
    }

    /**
     * 绘制子View
     */
    private void createChildView() {
        tipLineView.setTipView(mView)
                .setColors("#AFC8E3", "#449BF3")
                .setAuToRun(false)
                .setAdjustHeight((int) FSScreen.dip2px(5f))
                .setLineHeight(FSScreen.dip2px(34f));
        tipCircularView.setTipView(mView)
                .setAuToRun(false)
                .setHasAnim(true)
                .setAdjustHeight((int) FSScreen.dip2px(5f))
                .setColors("#AFC8E3", "#449BF3")
                .setMaxRadius(16).setSpace(8);
        tipLightView.setTipView(mView)
                .setBlur(mLightType == TipLightView.TipLightType.Rectangle ? 0.1f : FSScreen.dip2px(5f))
                .setRoundRadius(FSScreen.dip2px(5f))
                .setLightType(mLightType);
        if (dashedHeight != 0 && dashedWidth != 0 && dashedDistances != 0) {
            tipLightView.setDashedData(dashedColor, dashedWidth, dashedHeight, dashedDistances);
        }
        tipContentView.setTipView(mView)
                .setDistance(FSScreen.dip2px(29f))
                .setFillColor("#319BF5")
                .setTColor("#FFFFFF")
                .setTSize(14f)
                .setRoundRadius(FSScreen.dip2px(5f))
                .setTPadding((int) FSScreen.dip2px(10f), (int) FSScreen.dip2px(8f), (int) FSScreen.dip2px(10f), (int) FSScreen.dip2px(8f))
                .setTContent(mContent);
        tipTagView.setTContent(mViewMap.size() <= 1 ? "轻触关闭" : "点击继续");
        tipTagView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mView != null) {
                    tipTagView.setX(screenWidth / 2 - tipTagView.getWidth() / 2);
                    if (mView.getY() < screenHeight / 2) {
                        tipTagView.setY(screenHeight - screenHeight / 6);
                    } else {
                        tipTagView.setY(screenHeight / 6);
                    }
                }
            }
        });
        addView(tipLightView,
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(tipLineView,
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(tipCircularView,
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(tipContentView,
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(tipTagView,
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mViewMap.remove(mContent);
        tipCircularView.start();
    }

    public boolean hasNext() {
        return !mViewMap.isEmpty();
    }

    /**
     * 启动下一个
     */
    public void showNext() {
        if (!mViewMap.isEmpty()) {
            removeAllViews();
            startDraw();
        }
    }
}
