/*
 * Copyright (C) 2019 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.yilan.sdk.sdkdemo.view.guide;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.yilan.sdk.common.util.FSScreen;

/**
 * 自定义线条View
 */
public class TipLineView extends View {

    private Context mContext;
    private Paint mPaint;
    private View mView;//需要引导的View
    private LinearGradient mLinearGradient;//设置渐变
    private int mX;//传进来View的横坐标
    private int mY;//传进来View的纵坐标
    private int mHeight;//传进来的View的高度
    private int adjustHeight;//微调高度
    private int mWidth;//传进来的View的宽度
    private float lineHeight;//线条高度
    private float nowLineHeight;//当前线条高度
    private int[] colorIds;//线条颜色
    private float speed;//绘制速度(数值越小，绘制越慢)
    private boolean running;//是否正在运行
    private boolean auToRun;//是否自动运行
    private boolean hasAnim;//是否加载动画
    private TipCircularDrawListener mTipCircularDrawListener;

    public TipLineView(Context context) {
        super(context);
        init(context);
    }

    public TipLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TipLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL);//实心
    }

    /**
     * 设置需要引导的View的位置及宽高
     *
     * @param x 引导View横坐标
     * @param y 引导View纵坐标
     * @param w 引导View宽
     * @param h 引导View高
     */
    public TipLineView setTipViewOption(int x, int y, int w, int h) {
        this.mX = x;
        this.mY = y;
        this.mWidth = w;
        this.mHeight = h;
        return this;
    }

    /**
     * 设置微调高度
     */
    public TipLineView setAdjustHeight(int adjustHeight) {
        this.adjustHeight = adjustHeight;
        return this;
    }

    /**
     * 设置需要引导的View
     *
     * @param tipView
     */
    public TipLineView setTipView(View tipView) {
        this.mView = tipView;
        return this;
    }

    /**
     * 设置线条高度
     *
     * @param lineHeight
     */
    public TipLineView setLineHeight(float lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    /**
     * 设置颜色
     */
    public TipLineView setColors(int... colorIds) {
        this.colorIds = colorIds;
        return this;
    }

    /**
     * 设置颜色
     */
    public TipLineView setColors(String... colors) {
        this.colorIds = new int[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colorIds[i] = Color.parseColor(colors[i]);
        }
        return this;
    }

    /**
     * 设置是否自动运行
     */
    public TipLineView setAuToRun(boolean auToRun) {
        this.auToRun = auToRun;
        return this;
    }

    /**
     * 设置是否加载动画
     */
    public TipLineView setHasAnim(boolean hasAnim) {
        this.hasAnim = hasAnim;
        return this;
    }

    /**
     * 设置绘制速度
     */
    public TipLineView setSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    /**
     * 启动动画效果
     */
    public void start() {
        if (running) {
            return;
        }
        if (mTipCircularDrawListener != null) {
            mTipCircularDrawListener.drawStart();
        }
        running = true;
        invalidate();
    }

    /**
     * 停止动画效果
     */
    public void stop() {
        running = false;
        invalidate();
    }

    /**
     * 暂停动画效果
     */
    public void pause() {
        running = false;
    }

    /**
     * 重启动画效果
     */
    public void resume() {
        running = true;
        invalidate();
    }

    /**
     * 设置绘图监听
     */
    public TipLineView setTipCircularDrawListener(
            TipCircularDrawListener tipCircularDrawListener) {
        mTipCircularDrawListener = tipCircularDrawListener;
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
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mView != null) {
            initViewOption();
        }
        int pX = mX + (mWidth / 2);
        int pY;
        float toPY;

        if (!hasAnim) {
            nowLineHeight = lineHeight;
        }

        //判断需要引导的View是否在屏幕上半部分
        if (mY < FSScreen.getScreenHeight() / 2) {
            //在屏幕上半部分
            pY = mY + mHeight - adjustHeight;
            toPY = pY + nowLineHeight;
        } else {
            //在屏幕下半部分
            pY = mY + adjustHeight;
            toPY = pY - nowLineHeight;
        }

        if (mLinearGradient == null) {
            //创建渐变色
            mLinearGradient =
                    new LinearGradient(pX, pY, pX, toPY, colorIds, null, Shader.TileMode.MIRROR);
            mPaint.setShader(mLinearGradient);
        }

        if (hasAnim) {
            canvas.drawLine(pX, pY, pX, toPY, mPaint);
            nowLineHeight += speed;
            boolean isGoOn = true;
            if (nowLineHeight > lineHeight) {
                nowLineHeight = 0;
                if (mTipCircularDrawListener != null) {
                    isGoOn = mTipCircularDrawListener.drawEnd();
                } else {
                    isGoOn = hasAnim;
                }
            }
            if (running && isGoOn) {
                invalidate();
            } else {
                running = false;
                if (mTipCircularDrawListener != null) {
                    mTipCircularDrawListener.AnimEnd();
                }
            }
        } else {
            canvas.drawLine(pX, pY, pX, toPY, mPaint);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (auToRun) {
            start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        stop();
        super.onDetachedFromWindow();
    }
}
