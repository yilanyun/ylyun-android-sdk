/*
 * Copyright (C) 2017 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.yilan.sdk.sdkdemo.util;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import com.yilan.sdk.common.util.FSScreen;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 透明状态栏效果简易实现
 * Created by lwq on 2017/12/11.
 */

public class ImmerseLayoutUtil {
    private ImmerseLayoutUtil() {
    }


    /**
     * 设置透明状态栏效果
     * 在setContentView之后调用
     *
     * @param titleViewID 标题栏资源id
     */
    public static void setImmerseTitleView(Activity activity, @IdRes int titleViewID) {// view为标题栏
        setImmerseTitleView(activity, titleViewID, true, Color.TRANSPARENT);
    }

    /**
     * 设置透明状态栏效果，透明背景，黑色字体，浅色标题栏情况用
     * 在setContentView之后调用
     *
     * @param titleViewID 标题栏资源id
     */
    public static void setImmerseTitleViewLight(Activity activity, @IdRes int titleViewID) {
        int result = setStatusBarLightIconText(activity, true, true);
        int color = result==0? Color.parseColor("#35000000"): Color.TRANSPARENT;
        setImmerseTitleView(activity, titleViewID, true, color);
    }

    /**
     * 设置半透明状态栏效果
     * 在setContentView之后调用
     *
     * @param titleViewID 标题栏资源id
     */
    public static void setHalfImmerseTitleView(Activity activity, @IdRes int titleViewID) {
        setImmerseTitleView(activity, titleViewID, true, Color.parseColor("#35000000"));
    }

    /**
     * 设置透明状态栏效果
     * 在setContentView之后调用
     *
     * @param titleViewID 标题栏资源id
     * @param adjustSize 是否使用 WindowManager#SOFT_INPUT_ADJUST_RESIZE
     */
    public static void setImmerseTitleView(Activity activity, @IdRes int titleViewID, boolean adjustSize,
                                           @ColorInt int statusBarColor) {
        if (activity == null) {
            return;
        }
        Window window = activity.getWindow();
        if (window == null) {
            return;
        }
        View titleView = activity.findViewById(titleViewID);
        if (titleView == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setActivityFullScreenImmerseStatusBar(activity, statusBarColor);
            int statusBarHeight = FSScreen.getStatusBarHeight();
            titleView.measure(0, 0);
            ViewGroup.LayoutParams lp = titleView.getLayoutParams();
            //titleView上部额外加状态栏高度的padding
            if (lp.height > 0) {
                lp.height += statusBarHeight;
            }
            titleView.setPadding(titleView.getPaddingLeft(),
                    statusBarHeight + titleView.getPaddingTop(),
                    titleView.getPaddingRight(),
                    titleView.getPaddingBottom());
            View contentView = activity.findViewById(android.R.id.content);
            if (contentView != null) {
                boolean isTabHostAct = activity.getIntent()
                        .getBooleanExtra("immersive_is_tab_Host_activity", false);
                if (!isTabHostAct && adjustSize) {
//                    AdjustSizeHelper.assistActivity(contentView);
                }
            }
        }
    }

    public static void setActivityFullScreenImmerseStatusBar(Activity activity, @ColorInt int statusBarColor) {
        setActivityImmerseStatusBar(activity, statusBarColor, true);
    }

    public static void setActivityImmerseStatusBar(Activity activity, @ColorInt int statusBarColor, boolean isFullScreen) {
        Window window;
        if (activity == null || (window = activity.getWindow()) == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int visibility = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                if (isFullScreen) {
                    visibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                }
                window.getDecorView().setSystemUiVisibility(visibility);
                // 部分机型的statusbar会有半透明的黑色背景
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                // 因为EMUI3.1系统与这种沉浸式方案API有点冲突，会没有沉浸式效果。
                // 所以这里加了判断，不是EMUI3.1系统才走一下逻辑
                if (!isEmui3_1()) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
                window.setStatusBarColor(statusBarColor);
            }
        }
    }

    /**设置界面布局全屏，状态栏背景透明*/
    public static void setActivityFullScreenNoStatusBar(Activity activity) {
        setActivityFullScreenImmerseStatusBar(activity, Color.TRANSPARENT);
    }

    private static boolean isEmui3_1() {
        return RomUtils.isEmuiVersion("3.1");
    }

    public static class AdjustSizeHelper {

        private View mChildOfContent;
        private int usableHeightPrevious;
        private ViewGroup.LayoutParams frameLayoutParams;

        private AdjustSizeHelper(View content) {
            if (content != null) {
                mChildOfContent = content;
                mChildOfContent.getViewTreeObserver()
                        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            public void onGlobalLayout() {
                                possiblyResizeChildOfContent();
                            }
                        });
                frameLayoutParams = mChildOfContent.getLayoutParams();
            }
        }

        public static void assistActivity(View content) {
            new AdjustSizeHelper(content);
        }

        private void possiblyResizeChildOfContent() {
            int usableHeightNow = computeUsableHeight();
            if (usableHeightNow != usableHeightPrevious) {
                //如果两次高度不一致
                //将计算的可视高度设置成视图的高度
                frameLayoutParams.height = usableHeightNow;
                mChildOfContent.requestLayout();//请求重新布局
                usableHeightPrevious = usableHeightNow;
            }
        }

        private int computeUsableHeight() {
            //计算视图可视高度
            Rect r = new Rect();
            mChildOfContent.getWindowVisibleDisplayFrame(r);
            return r.bottom;
        }
    }

    /**
     *状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    public static int setStatusBarLightIconText(Activity activity, boolean dark, boolean isFullScreen) {
        int result = 0;
        if (activity == null || activity.getWindow() == null) {
            return result;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(activity, dark, isFullScreen)) {
                result = 1;
            } else if (FlymeSetStatusBarLightMode(activity, dark)) {
                result = 2;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                SetStatusBarLightMode(activity, dark, isFullScreen);
                result = 3;
            }
        }
        return result;
    }


    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     * @param window 需要设置的窗口
     * @param dark 是否把状态栏文字及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    public static boolean FlymeSetStatusBarLightMode(Activity window, boolean dark) {
        return MeizuStatusbarColorUtils.setStatusBarDarkIcon(window, dark);
    }

    /**
     * 需要MIUIV6以上
     * @param activity
     * @param dark 是否把状态栏文字及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    private static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark, boolean isFullScreen) {
        boolean result = false;
        Window window = activity.getWindow();
        try {
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = window.getClass().getMethod("setExtraFlags", int.class, int.class);
            if (dark) {
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
            }
            result = true;

            //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                SetStatusBarLightMode(activity, dark, isFullScreen);
            }
        } catch (Exception e) {

        }
        return result;
    }

    private static void SetStatusBarLightMode(Activity activity, boolean dark, boolean isFullScreen) {
        if (dark) {
            int visibility = isFullScreen ? View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            activity.getWindow().getDecorView().setSystemUiVisibility(visibility);
        } else {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

}
