package com.yilan.sdk.sdkdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yilan.sdk.common.util.FSDevice;
import com.yilan.sdk.common.util.YLUIUtil;
import com.yilan.sdk.sdkdemo.demo.ConfigFragment;
import com.yilan.sdk.sdkdemo.demo.FeedFragment;
import com.yilan.sdk.sdkdemo.demo.LoginFragment;
import com.yilan.sdk.sdkdemo.demo.OtherFragment;
import com.yilan.sdk.sdkdemo.demo.SubFeedFragment;
import com.yilan.sdk.sdkdemo.view.TitleLayout;
import com.yilan.sdk.sdkdemo.view.guide.TipGuideKey;
import com.yilan.sdk.sdkdemo.view.guide.TipLightView;
import com.yilan.sdk.sdkdemo.view.guide.TipViewHelper;
import com.yilan.sdk.ui.configs.YLUIConfig;
import com.yilan.sdk.ui.web.WebFragment;

import java.util.ArrayList;
import java.util.List;

public class MainDemoActivity extends AppCompatActivity implements WebFragment.OnVideoChangeListener, TitleLayout.OnLogoClickListener, TitleLayout.OnSettingClickedListener {

    private TitleLayout mTitleLayout;
    private FragmentManager manager;
    private FeedFragment feedFragment;
    private SubFeedFragment subFeedFragment;
    private OtherFragment otherFragment;

    private RelativeLayout slideContainer;

    private Fragment leftFragment;
    private Fragment rightFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_demo);
        mTitleLayout = findViewById(R.id.layout_common_title);
        mTitleLayout.setOnLogoClickListener(this);
        mTitleLayout.setOnSettingClickedListener(this);
        slideContainer = findViewById(R.id.slide_container);
        slideContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackGroundClick(v);
            }
        });
        TextView version = findViewById(R.id.version);
        version.setText(BuildConfig.VERSION_NAME);
        TextView udid = findViewById(R.id.udid);
        udid.setText(FSDevice.Dev.getDeviceID(this));
        manager = getSupportFragmentManager();
        if (Build.VERSION.SDK_INT >= 23) {
            checkAndRequestPermission();
        }
        feedFragment = new FeedFragment();
        manager.beginTransaction().replace(R.id.content_feed, feedFragment).commitAllowingStateLoss();
        subFeedFragment = new SubFeedFragment();
        manager.beginTransaction().replace(R.id.content_sub_feed, subFeedFragment).commitAllowingStateLoss();
        otherFragment = new OtherFragment();
        manager.beginTransaction().replace(R.id.content_other, otherFragment).commitAllowingStateLoss();
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                showTipsView();
            }
        },200);
    }

    private void showTipsView() {
        View logo = mTitleLayout.findViewById(R.id.logo);
        View img_action = mTitleLayout.findViewById(R.id.img_action);
        TipViewHelper helper = new TipViewHelper(this,"aaa");
        helper.addTipView(logo,"点击这里登陆", TipGuideKey.KEY_LOGIN);
        helper.addTipView(img_action,"点击这里进行个性化设置", TipGuideKey.KEY_MENU_SETTING);
        helper.setLightType(TipLightView.TipLightType.Rectangle);
        helper.showTip();
    }

    private void onBackGroundClick(View v) {
        if (leftFragment != null) {
            YLUIUtil.FragmentOperate.with(manager).hide(leftFragment);
        }
        if (rightFragment != null) {
            YLUIUtil.FragmentOperate.with(manager).hide(rightFragment);
        }
        slideContainer.setVisibility(View.GONE);
    }

    @Override
    public void onLogoClicked(View v) {
        slideContainer.setVisibility(View.VISIBLE);
        if (leftFragment == null) {
            leftFragment = LoginFragment.newInstance();
            YLUIUtil.FragmentOperate.with(manager).add(R.id.left_container, leftFragment);
        } else {
            YLUIUtil.FragmentOperate.with(manager).show(leftFragment);
        }
    }

    @Override
    public boolean onSettingClicked() {
        slideContainer.setVisibility(View.VISIBLE);
        if (rightFragment == null) {
            rightFragment = ConfigFragment.newInstance();
            YLUIUtil.FragmentOperate.with(manager).add(R.id.right_container, rightFragment);
        } else {
            YLUIUtil.FragmentOperate.with(manager).show(rightFragment);
        }
        return true;
    }

    @Override
    public void onState(boolean isFullScreen) {
        showStatusBar(!isFullScreen);
    }

    private void showStatusBar(boolean show) {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        if (show) {
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(attrs);
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * ----------非常重要----------
     * <p>
     * Android6.0以上的权限适配简单示例：
     * <p>
     * 如果targetSDKVersion >= 23，那么必须要申请到所需要的权限，再调用广点通SDK，否则广点通SDK不会工作。
     * <p>
     * Demo代码里是一个基本的权限申请示例，请开发者根据自己的场景合理地编写这部分代码来实现权限申请。
     * 注意：下面的`checkSelfPermission`和`requestPermissions`方法都是在Android6.0的SDK中增加的API，如果您的App还没有适配到Android6.0以上，则不需要调用这些方法，直接调用广点通SDK即可。
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        if (!(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
        }

//        if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
//            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }

        if (!(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        // 权限都已经有了，那么直接调用SDK
        if (lackedPermission.size() == 0) {
        } else {
            // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            requestPermissions(requestPermissions, 1024);
        }
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1024 && hasAllPermissionsGranted(grantResults)) {
        } else {
            // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
            Toast.makeText(this, "应用缺少必要的权限！请点击\"权限\"，打开所需要的权限。", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
//            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        YLUIConfig.getInstance().unRegisterShareCallBack();
    }

    @Override
    public void onBackPressed() {
        if (leftFragment != null && leftFragment.isVisible()) {
            YLUIUtil.FragmentOperate.with(manager).hide(leftFragment);
            slideContainer.setVisibility(View.GONE);
            return;
        }
        if (rightFragment != null && rightFragment.isVisible()) {
            YLUIUtil.FragmentOperate.with(manager).hide(rightFragment);
            slideContainer.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }


}
