package com.yilan.sdk.sdkdemo.view.guide;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * 引导图工具类
 */
public class TipViewHelper {
    private Context mContext;
    private PopupWindow popupWindow;
    private TipFrameView tipFrameView;
    private LinkedHashMap<String, View> mViewMap;//需要引导的View集
    private SharedPreferences sp;
    private String uniformId;//用户唯一标识
    private final String CRM_IMAGE_GUIDE = "crm_home_image_guide";
    private final String KEY_FIRST_USE = "key_first_use";
    private TipLightView.TipLightType mLightType;
    private int errorCount;
    private boolean canShowTipView = true;

    public TipViewHelper(Context context, String uniformId) {
        this.mContext = context;
        this.uniformId = uniformId;
        init();
    }

    private void init() {
        mViewMap = new LinkedHashMap<>();
        popupWindow = new PopupWindow();
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        popupWindow.setClippingEnabled(false);
        tipFrameView = new TipFrameView(mContext);
        popupWindow.setContentView(tipFrameView);
        tipFrameView.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TipGuideKey type = (TipGuideKey) v.getTag();
                //将当前View设置为已展示
                setNotFirstUse(type);
                if (tipFrameView.hasNext()) {
                    tipFrameView.showNext();
                } else {
                    popupWindow.dismiss();
                }
            }
        });
    }

    /**
     * 添加需要引导的View
     *
     * @param v       需要添加引导的View
     * @param content 引导View展示的内容
     * @param type    引导View的Key
     */
    public TipViewHelper addTipView(View v, String content, TipGuideKey type) {
        if (isFirstUse(type)) {
            v.setTag(type);
            if (tipFrameView != null && popupWindow != null && popupWindow.isShowing()) {
                tipFrameView.setView(v, content).upDataTagContent();
            } else {
                mViewMap.put(content, v);
            }
        }
        return this;
    }

    /**
     * 清空引导View
     */
    public TipViewHelper removeAll() {
        mViewMap.clear();
        return this;
    }

    /**
     * 是否展示弹框
     */
    public boolean isShowPopu() {
        if (popupWindow != null) {
            return popupWindow.isShowing();
        } else {
            return false;
        }
    }

    /**
     * 取消弹框
     */
    public void dismiss() {
        canShowTipView = false;
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    /**
     * 展示引导图
     */
    public void showTip() {
        showTip("", 0, 0, 0);
    }

    public void showTip(String dashedColor, float dashedWidth, float dashedHeight,
                        float dashedDistances) {
        canShowTipView = true;
        if (mViewMap != null && mViewMap.size() > 0) {
            tipFrameView.setView(mViewMap);
            if (popupWindow != null && !popupWindow.isShowing()) {
                if (dashedHeight != 0 && dashedWidth != 0 && dashedDistances != 0) {
                    tipFrameView
                            .setDashedData(dashedColor, dashedWidth, dashedHeight, dashedDistances);
                }
                tipFrameView.setLightType(mLightType).startDraw();
                if (!((Activity) mContext).isFinishing() && !isDestroyed()) {
                    try {
                        checkViewWH();
                        errorCount = 0;
                    } catch (Exception e) {
                        errorCount++;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (errorCount < 4) {
                                    showTip(dashedColor, dashedWidth, dashedHeight,
                                            dashedDistances);
                                }
                            }
                        }, 500);
                    }
                }
            }
        }
    }

    private void checkViewWH() {
        new Handler()
                .postDelayed(() -> {
                    boolean canShow = true;
                    Collection<View> allView = mViewMap.values();
                    for (View v : allView) {
                        if (v.getHeight() == 0 || v.getWidth() == 0) {
                            canShow = false;
                            break;
                        }
                    }
                    if (canShow) {
                        if (canShowTipView
                                && !((Activity) mContext).isFinishing()
                                && !isDestroyed()) {
                            popupWindow
                                    .showAtLocation(
                                            ((Activity) mContext).getWindow().getDecorView(),
                                            Gravity.CENTER, 0,
                                            0);
                        }
                    } else {
                        checkViewWH();
                    }
                }, 500);
    }

    public TipViewHelper setLightType(TipLightView.TipLightType lightType) {
        mLightType = lightType;
        return this;
    }

    boolean isDestroyed() {
        Activity activity = (Activity) mContext;
        if (activity == null) {
            return true;
        }
        return false;
    }

    /**
     * 获取SP
     */
    private SharedPreferences getSp() {
        if (sp == null) {
            sp = mContext.getSharedPreferences(CRM_IMAGE_GUIDE, Context.MODE_PRIVATE);
        }
        return sp;
    }

    /**
     * 判断是否是首次展示
     */
    private boolean isFirstUse(TipGuideKey type) {
        Set<String> set = getSp().getStringSet(KEY_FIRST_USE, null);
        String value = getValue(uniformId, type);
        return set == null || set.isEmpty() || !set.contains(value);
    }

    /**
     * 设置为已展示
     */
    private void setNotFirstUse(TipGuideKey type) {
        Set<String> temp = new HashSet<>();
        temp = getSp().getStringSet(KEY_FIRST_USE, temp);
        Set<String> set = new HashSet<>(temp);
        set.add(getValue(uniformId, type));
        getSp().edit().putStringSet(KEY_FIRST_USE, set).apply();
    }

    /**
     * 获取Value
     */
    private String getValue(String uniformId, TipGuideKey type) {
        return uniformId + (type == null ? "" : type.key);
    }
}
