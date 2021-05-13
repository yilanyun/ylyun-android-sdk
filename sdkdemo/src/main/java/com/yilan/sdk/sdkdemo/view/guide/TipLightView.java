/*
 * Copyright (C) 2019 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.yilan.sdk.sdkdemo.view.guide;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义高亮View
 */
public class TipLightView extends View {

    private Paint mPaint;
    private View mView;//需要引导的View
    private int mX;//传进来View的横坐标
    private int mY;//传进来View的纵坐标
    private int mHeight;//传进来的View的高度
    private int mWidth;//传进来的View的宽度
    private PorterDuffXfermode porterDuffXfermode;//高亮混合图层
    private BlurMaskFilter blurMaskFilter;//阴影过滤器
    private float radius;//阴影半径
    private float roundRadius;//圆角半径
    private int blurColor = Color.parseColor("#FFFFFF");//阴影颜色
    private int dashedColor = Color.parseColor("#FFFFFF");//虚线颜色
    private float dashedWidth;//虚线宽度
    private float dashedHeight;//虚线高度
    private float dashedDistances;//虚线间隔
    private float mLRPadding;//距离高亮范围的左右边距
    private float mTBPadding;//距离高亮范围的上下边距
    private int backGroundColor;//背景颜色(默认为半透明)
    private TipLightType lightType = TipLightType.Rectangle;//高亮类型（圆的，方的，椭圆的）

    public TipLightView(Context context) {
        super(context);
        init();
    }

    public TipLightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TipLightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        backGroundColor = Color.parseColor("#80000000");//Color.argb(0xCC, 0, 0, 0);
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);//XOR
    }

    /**
     * 设置虚线属性
     *
     * @param dashedColor;//虚线颜色
     * @param dashedWidth;//虚线宽度
     * @param dashedHeight;//虚线高度
     * @param dashedDistances;//虚线间隔
     */
    public TipLightView setDashedData(String dashedColor, float dashedWidth, float dashedHeight, float
            dashedDistances) {
        setDashedData(Color.parseColor(dashedColor), dashedWidth, dashedHeight, dashedDistances);
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
    public TipLightView setDashedData(int dashedColor, float dashedWidth, float dashedHeight, float dashedDistances) {
        this.dashedColor = dashedColor;
        this.dashedWidth = dashedWidth;
        this.dashedHeight = dashedHeight;
        this.dashedDistances = dashedDistances;
        return this;
    }

    /**
     * 设置背景色
     *
     * @param backGroundColorID
     */
    public TipLightView setBackGroundColor(int backGroundColorID) {
        this.backGroundColor = backGroundColorID;
        return this;
    }

    /**
     * 设置距离高亮范围的左右边距
     *
     * @param lRPadding
     */
    public TipLightView setLRPadding(int lRPadding) {
        this.mLRPadding = lRPadding;
        return this;
    }

    /**
     * 设置距离高亮范围的上下边距
     *
     * @param tBPadding
     */
    public TipLightView setTBPadding(int tBPadding) {
        this.mTBPadding = tBPadding;
        return this;
    }

    /**
     * 设置阴影色
     *
     * @param blurColor
     */
    public TipLightView setBlurColor(String blurColor) {
        setBlurColor(Color.parseColor(blurColor));
        return this;
    }

    /**
     * 设置阴影色
     *
     * @param blurColor
     */
    public TipLightView setBlurColor(int blurColor) {
        this.blurColor = blurColor;
        return this;
    }

    /**
     * 设置背景色
     *
     * @param backGroundColor
     */
    public TipLightView setBackGroundColor(String backGroundColor) {
        setBackGroundColor(Color.parseColor(backGroundColor));
        return this;
    }

    /**
     * 设置高亮类型
     *
     * @param lightType Rectangle 方形; Circle 圆形; Oval 椭圆形
     */
    public TipLightView setLightType(TipLightType lightType) {
        this.lightType = lightType;
        return this;
    }

    /**
     * 设置阴影边框
     *
     * @param radius 阴影半径
     */
    public TipLightView setBlur(float radius) {
        this.radius = radius;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        blurMaskFilter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.OUTER);
        return this;
    }

    /**
     * 设置圆角
     *
     * @param roundRadius 圆角半径
     */
    public TipLightView setRoundRadius(float roundRadius) {
        this.roundRadius = roundRadius;
        return this;
    }

    /**
     * 设置需要引导的View的位置及宽高
     *
     * @param x 引导View横坐标
     * @param y 引导View纵坐标
     * @param w 引导View宽
     * @param h 引导View高
     */
    public TipLightView setTipViewOption(int x, int y, int w, int h) {
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
    public TipLightView setTipView(View tipView) {
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
    }

    private void initPaint(){
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);//实心
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mView != null) {
            initViewOption();
        }
        initPaint();
        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawColor(backGroundColor);
        drawView(canvas);

        mPaint.setXfermode(porterDuffXfermode);
        drawView(canvas);
        mPaint.setXfermode(null);
        canvas.restoreToCount(saved);

        //绘制虚线边
        if (dashedColor != 0 && dashedWidth != 0 && dashedHeight != 0 && dashedDistances != 0) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(dashedHeight);
            mPaint.setColor(dashedColor);
            //绘制长度为4的实线后再绘制长度为4的空白区域，0位间隔
            mPaint.setPathEffect(new DashPathEffect(new float[] {dashedWidth, dashedDistances}, 0));
            drawView(canvas);
        } else if (radius > 0) {
            //绘制阴影
            mPaint.setMaskFilter(blurMaskFilter);
            mPaint.setColor(blurColor);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            drawView(canvas);
            mPaint.setMaskFilter(null);
        }
    }

    /**
     * 绘制
     */
    private void drawView(Canvas srcCanvas) {
        switch (lightType) {
            case Rectangle:
                float dashR = radius + dashedHeight;
                srcCanvas.drawRoundRect(
                        new RectF(mX - mLRPadding + dashR,
                                mY - mTBPadding + dashR,
                                mX + mWidth + mLRPadding - dashR,
                                mY + mHeight + mTBPadding - dashR),
                        roundRadius, roundRadius, mPaint);
                break;
            case Circle:
                float dash = (radius * 2 + dashedHeight * 2);
                srcCanvas.drawCircle(mX + mWidth / 2, mY + mHeight / 2,
                        (mWidth > mHeight ? mWidth + mLRPadding - dash
                                 : mHeight + mTBPadding - dash) / 2,
                        mPaint);
                break;
            case Oval:
                srcCanvas.drawOval(new RectF(mX - mLRPadding, mY - mTBPadding, mX + mWidth + mLRPadding,
                        mY + mHeight + mTBPadding), mPaint);
                break;
            default:
                break;
        }
    }

    public enum TipLightType {
        /**
         * 方形
         */
        Rectangle,
        /**
         * 圆形
         */
        Circle,
        /**
         * 椭圆形
         */
        Oval
    }
}
