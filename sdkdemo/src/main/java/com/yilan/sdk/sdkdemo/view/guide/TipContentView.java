/*
 * Copyright (C) 2019 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.yilan.sdk.sdkdemo.view.guide;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.yilan.sdk.common.util.FSScreen;

/**
 * 自定义View
 *
 */
public class TipContentView extends android.support.v7.widget.AppCompatTextView {
    private Context mContext;
    private View mView;//需要引导的View
    private float mX;//传进来View的横坐标
    private float mY;//传进来View的纵坐标
    private int mHeight;//传进来的View的高度
    private int mWidth;//传进来的View的宽度
    private int maxWidth;//最大宽度
    private int maxHeight;//最大高度
    private int strokeColor = 0xffffffff;//边框颜色
    private int fillColor = 0xffffffff;//内部填充颜色
    private float strokeWidth;//边框宽度
    private float roundRadius;//圆角半径
    private boolean inTop;//在屏幕上半部分
    private float selfWidth;//控件本身宽度
    private float selfHeight;//控件本身高度
    private int screenPadding = 0X30;//距屏幕左右内距离
    private float distance;//当前控件与引导View的距离

    private float screenWidth;//屏幕宽度
    private float screenHeight;//屏幕高度
    private TipCircularDrawListener mTipCircularDrawListener;

    public TipContentView(Context context) {
        super(context);
        init(context);
    }

    public TipContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TipContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setWillNotDraw(false);
        //设置滑动会拦截其他的监听事件
//        setMovementMethod(ScrollingMovementMethod.getInstance());
//        setVerticalScrollBarEnabled(true);
        screenWidth = FSScreen.getScreenWidth();
        screenHeight = FSScreen.getScreenHeight();
    }

    public void setTipCircularDrawListener(
            TipCircularDrawListener tipCircularDrawListener) {
        mTipCircularDrawListener = tipCircularDrawListener;
    }

    /**
     * 设置边框颜色
     *
     * @param strokeColor
     */
    public TipContentView setStrokeColor(String strokeColor) {
        setStrokeColor(Color.parseColor(strokeColor));
        return this;
    }

    /**
     * 设置边框颜色
     *
     * @param strokeColor
     */
    public TipContentView setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        return this;
    }

    /**
     * 设置填充颜色
     *
     * @param fillColor
     */
    public TipContentView setFillColor(String fillColor) {
        setFillColor(Color.parseColor(fillColor));
        return this;
    }

    /**
     * 设置填充颜色
     *
     * @param fillColor
     */
    public TipContentView setFillColor(int fillColor) {
        this.fillColor = fillColor;
        return this;
    }

    /**
     * 设置边框宽度
     *
     * @param strokeWidth
     */
    public TipContentView setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    /**
     * 设置圆角半径
     *
     * @param roundRadius
     */
    public TipContentView setRoundRadius(float roundRadius) {
        this.roundRadius = roundRadius;
        return this;
    }

    /**
     * 设置背景色
     *
     * @param bgColor
     */
    public TipContentView setBgColor(String bgColor) {
        setBgColor(Color.parseColor(bgColor));
        return this;
    }

    /**
     * 设置背景色
     *
     * @param bgColor
     */
    public TipContentView setBgColor(int bgColor) {
        setBackgroundColor(bgColor);
        return this;
    }

    /**
     * 设置文字
     *
     * @param content
     */
    public TipContentView setTContent(String content) {
        setText(content);
        return this;
    }

    /**
     * 设置文字大小
     *
     * @param txtSize
     */
    public TipContentView setTSize(float txtSize) {
        setTextSize(txtSize);
        return this;
    }

    /**
     * 设置文字颜色
     *
     * @param textColor
     */
    public TipContentView setTColor(String textColor) {
        setTColor(Color.parseColor(textColor));
        return this;
    }

    /**
     * 设置文字颜色
     *
     * @param textColor
     */
    public TipContentView setTColor(int textColor) {
        setTextColor(textColor);
        return this;
    }

    /**
     * 设置文字内边距
     *
     * @param l 左内边距
     * @param t 右内边距
     * @param r 上内边距
     * @param b 下内边距
     */
    public TipContentView setTPadding(int l, int t, int r, int b) {
        setPadding(l, t, r, b);
        return this;
    }

    /**
     * 设置屏幕左右内边距
     *
     * @param padding
     */
    public TipContentView setScreenPadding(int padding) {
        this.screenPadding = padding;
        return this;
    }

    /**
     * 设置当前控件与引导View的距离
     *
     * @param distance
     */
    public TipContentView setDistance(float distance) {
        this.distance = distance;
        return this;
    }

    /**
     * 设置当前控件最大宽度
     *
     * @param maxWidth
     */
    public TipContentView setTMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    /**
     * 置当前控件最大高度
     *
     * @param maxHeight
     */
    public TipContentView setTMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    /**
     * 判断在屏幕的位置
     */
    private void checkLocation() {
        if (mY < screenHeight / 2) {
            this.inTop = true;
        }else{
            this.inTop = false;
        }
        if (mTipCircularDrawListener != null) {
            mTipCircularDrawListener.drawStart();
        }
    }

    /**
     * 设置位置
     *
     * @param x 引导View横坐标
     * @param y 引导View纵坐标
     * @param w 引导View宽
     * @param h 引导View高
     */
    public TipContentView setTipViewOption(float x, float y, int w, int h) {
        this.mX = x;
        this.mY = y;
        this.mWidth = w;
        this.mHeight = h;
        checkLocation();
        return this;
    }

    /**
     * 设置需要引导的View
     *
     * @param tipView
     */
    public TipContentView setTipView(View tipView) {
        this.mView = tipView;
        return this;
    }

    /**
     * 获取需要展示的引导View信息
     */
    private void initViewOption() {
        int[] location = new int[2];
        //获取在整个屏幕内的绝对坐标
        mView.getLocationInWindow(location);
        mX = location[0];
        mY = location[1];
        //        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        //        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        //        //通过Measure计算View宽高
        //        mView.measure(w, h);
        mHeight = mView.getHeight();
        mWidth = mView.getWidth();
        checkLocation();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mView != null) {
            initViewOption();
        }
        if (maxWidth <= 0) {
            maxWidth = ((int) (screenWidth - screenPadding * 2));
        }
        setMaxWidth(maxWidth);

        if (maxHeight <= 0) {
            if (inTop) {
                maxHeight = (int) (screenHeight - mY - mHeight - distance - screenPadding);
            } else {
                maxHeight = (int) (mY - distance - screenPadding);
            }
        }
        if (maxHeight > 0) {
            setMaxHeight(maxHeight);
        }
        selfWidth = getWidth();
        selfHeight = getHeight();
        if (selfWidth != 0  && selfWidth != screenWidth) {
            setX(getSelfX((mX + mWidth / 2) - (selfWidth / 2)));
        }
        if (selfHeight != 0 && selfHeight != screenHeight) {
            setY(getSelfY());
        }
        if (mTipCircularDrawListener != null) {
            mTipCircularDrawListener.drawEnd();
        }
        shapeSolid();
    }

    /**
     * 计算实际应该展示的纵坐标位置
     */
    private float getSelfY() {
        float tempY = mY + mHeight + distance;
        float tempY1 = mY - distance;
        if (tempY + selfHeight > screenHeight && tempY1 - selfHeight < 0) {
            return screenHeight - tempY > tempY1 ? tempY : tempY1;
        } else {
            if (inTop) {
                return tempY;
            } else {
                return tempY1 - selfHeight;
            }
        }
    }

    /**
     * 计算实际应该展示的横坐标位置
     *
     * @param shouldShowX
     */
    private float getSelfX(float shouldShowX) {
        if (shouldShowX < screenPadding) {
            shouldShowX = screenPadding;
        }
        if (shouldShowX + selfWidth > screenWidth - screenPadding) {
            return getSelfX(--shouldShowX);
        } else {
            return shouldShowX;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    /**
     * 设置圆角的背景
     */
    private void shapeSolid() {
        Drawable drawable = getBackground();
        GradientDrawable gd;
        if (drawable instanceof GradientDrawable) {
            gd = (GradientDrawable) drawable;
        } else {
            gd = new GradientDrawable();
        }
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke((int) strokeWidth, strokeColor);
        setBackgroundDrawable(gd);
    }
}
