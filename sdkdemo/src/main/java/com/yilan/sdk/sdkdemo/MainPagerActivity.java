package com.yilan.sdk.sdkdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.yilan.sdk.sdkdemo.view.TitleLayout;
import com.yilan.sdk.ui.category.ChannelFragment;
import com.yilan.sdk.ui.configs.YLUIConfig;
import com.yilan.sdk.ui.little.YLLittleVideoFragment;
import com.yilan.sdk.ui.web.WebFragment;
import com.yilan.sdk.uibase.ui.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainPagerActivity extends AppCompatActivity implements WebFragment.OnVideoChangeListener {

    private TitleLayout mTitleLayout;
    private FragmentManager manager;
    private ShortFragment littleVideoFragment;
    private MyFragment myFragment;
    private ChannelFragment channelFrag;
    List<Fragment> fragments = new ArrayList<>();

    CustomViewPager viewPager;
    MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_main);
        mTitleLayout = findViewById(R.id.layout_common_title);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setCanScroll(false);
        final BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        manager = getSupportFragmentManager();
        navigation.post(new Runnable() {
            @Override
            public void run() {
                navigation.setSelectedItemId(R.id.navigation_home);
            }
        });
        if (Build.VERSION.SDK_INT >= 23) {
            checkAndRequestPermission();
        }
        initFragments();
        viewPager.setOffscreenPageLimit(4);
        adapter = new MyPagerAdapter(manager, fragments);
        viewPager.setAdapter(adapter);
        YLLittleVideoFragment.preloadVideo();
    }

    private void initFragments() {
        channelFrag = new ChannelFragment();
        littleVideoFragment = new ShortFragment();
        myFragment = new MyFragment();
        fragments.add(channelFrag);
        fragments.add(littleVideoFragment);
        fragments.add(myFragment);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0, true);
                    mTitleLayout.setOptionsMenu(MainPagerActivity.this, R.menu.ui_menu);
                    return true;
                case R.id.navigation_little:
                    viewPager.setCurrentItem(1, true);
                    mTitleLayout.setActionInvisible();
                    return true;
                case R.id.navigation_my:
                    viewPager.setCurrentItem(2, true);
                    mTitleLayout.setOptionsMenu(MainPagerActivity.this, R.menu.short_menu);
                    mTitleLayout.onOptionsItemSelectedListener(littleVideoFragment);
                    return true;
            }
            return false;
        }
    };

    private void hideAll() {
        List<Fragment> fragmentList = fragments;
        for (Fragment fragment : fragmentList) {
            fragment.setUserVisibleHint(false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onState(boolean isFullScreen) {
        showStatusBar(!isFullScreen);
    }

    private void showStatusBar(boolean show) {
        if (show) {
            WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attrs = getWindow().getAttributes();
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

        if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

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

    static class MyPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragments;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        YLUIConfig.getInstance().unRegisterShareCallBack();
    }
}
