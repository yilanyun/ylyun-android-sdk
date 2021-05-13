/*
 * Copyright (C) 2019 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.yilan.sdk.sdkdemo.view.guide;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.yilan.sdk.common.util.FSScreen;

/**
 * 引导图提示点
 *
 */
public class TipCircularView extends View {

    private Paint mPaint;//画笔
    private Context mContext;
    private float nowRadius;//当前绘制的半径
    private View mView;//需要引导的View
    private int mX;//传进来View的横坐标
    private int mY;//传进来View的纵坐标
    private int mHeight;//传进来的View的高度
    private int mWidth;//传进来的View的宽度
    private int adjustHeight;//微调高度
    private int maxRadius;//圆环最大半径
    private float space;//圆环之间的间隔
    private float startRadius;//起始半径
    private float speed;//绘制速度(数值越小，绘制越慢)
    private boolean running;//是否正在运行
    private boolean auToRun = true;//是否自动运行
    private boolean hasAnim;//是否加载动画
    private int[] colorsId = new int[1];//颜色集
    private TipCircularDrawListener mTipCircularDrawListener;

    public TipCircularView(Context context) {
        super(context);
        init(context);
    }

    public TipCircularView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TipCircularView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 设置需要引导的View的位置及宽高
     *
     * @param x 引导View横坐标
     * @param y 引导View纵坐标
     * @param w 引导View宽
     * @param h 引导View高
     */
    public TipCircularView setTipViewOption(int x, int y, int w, int h) {
        this.mX = x;
        this.mY = y;
        this.mWidth = w;
        this.mHeight = h;
        return this;
    }

    /**
     * 设置需要引导的View
     *
     * @param tipView
     */
    public TipCircularView setTipView(View tipView) {
        this.mView = tipView;
        return this;
    }

    /**
     * 设置绘制需要的参数
     *
     * @param maxRadius 最大半径
     * @param space     圆环间距
     * @param radius    起始半径
     * @param speed     绘制速度
     */
    public TipCircularView setDrawOption(int maxRadius, float space, float radius, float speed) {
        this.maxRadius = maxRadius;
        this.space = space;
        this.startRadius = radius;
        this.speed = speed;
        return this;
    }

    /**
     * 设置颜色集
     */
    public TipCircularView setColors(int... colorsId) {
        this.colorsId = colorsId;
        return this;
    }

    /**
     * 设置颜色集
     */
    public TipCircularView setColors(String... colors) {
        this.colorsId = new int[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colorsId[i] = Color.parseColor(colors[i]);
        }
        return this;
    }

    /**
     * 设置是否自动运行
     */
    public TipCircularView setAuToRun(boolean auToRun) {
        this.auToRun = auToRun;
        return this;
    }

    /**
     * 设置是否加载动画
     */
    public TipCircularView setHasAnim(boolean hasAnim) {
        this.hasAnim = hasAnim;
        return this;
    }

    /**
     * 设置最大半径
     */
    public TipCircularView setMaxRadius(int maxRadius) {
        this.maxRadius = maxRadius;
        return this;
    }

    /**
     * 设置微调高度
     */
    public TipCircularView setAdjustHeight(int adjustHeight) {
        this.adjustHeight = adjustHeight;
        return this;
    }

    /**
     * 设置圆环间距
     */
    public TipCircularView setSpace(float space) {
        this.space = space;
        return this;
    }

    /**
     * 设置起始半径
     */
    public TipCircularView setStartRadius(float radius) {
        this.startRadius = radius;
        return this;
    }

    /**
     * 设置绘制速度
     */
    public TipCircularView setSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    /**
     * 设置绘图监听
     */
    public TipCircularView setTipCircularDrawListener(
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

    private void init(Context context) {
        this.mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);//实心
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
        nowRadius = startRadius;
        running = true;
        invalidate();
    }

    /**
     * 停止动画效果
     */
    public void stop() {
        nowRadius = startRadius;
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (space <= 0) {
            space = getMeasuredWidth() * 1.0f / (colorsId.length * 2);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mView != null) {
            initViewOption();
        }
        int pX = mX + (mWidth / 2);
        int pY;

        //判断需要引导的View是否在屏幕上半部分
        if (mY < FSScreen.getScreenHeight() / 2) {
            //在屏幕上半部分
            pY = mY + mHeight - adjustHeight;
        } else {
            //在屏幕下半部分
            pY = mY + adjustHeight;
        }

        if (speed <= 0) {
            speed = space / 24;//每秒波纹动画帧数固定为24帧
        }

        if (hasAnim) {
            for (int i = 0; i < colorsId.length; i++) {
                mPaint.setColor(colorsId[i]);
                float tempRadius = nowRadius - space * i;
                if (tempRadius > 0) {
                    canvas.drawCircle(pX, pY, tempRadius, mPaint);
                }
            }
            nowRadius += speed;
            boolean isGoOn = true;
            if (nowRadius > maxRadius) {
                nowRadius = startRadius;
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
            nowRadius = maxRadius;
            for (int i = 0; i < colorsId.length; i++) {
                mPaint.setColor(colorsId[i]);
                canvas.drawCircle(pX, pY, nowRadius, mPaint);
                nowRadius -= space;
            }
            if (mTipCircularDrawListener != null) {
                mTipCircularDrawListener.AnimEnd();
            }
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
