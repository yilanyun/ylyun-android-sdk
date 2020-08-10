package com.yilan.sdk.sdkdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.sdk.common.BaseApp;
import com.yilan.sdk.common.util.FSDevice;
import com.yilan.sdk.common.util.FSDigest;
import com.yilan.sdk.common.util.FSUdid;
import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.ui.web.WebFragment;

import java.util.List;

public class HyBirdFragment extends Fragment {
    private FragmentManager manager;
    private final String BASEURL = "https://sv.1lan.tv/Starscream/feed/index.html?nobar=0&pageid=2&access_key=" + FSDevice.Client.getAccessKey()
            + "&udid=" + FSUdid.getInstance().get()
            + "&imei=" + FSDevice.Dev.getDeviceID(BaseApp.get())
            + "&imeimd5=" + FSDigest.md5(FSDevice.Dev.getDeviceID(BaseApp.get()))
            + "&mac=" + FSDevice.Wifi.getMacAddress(BaseApp.get())
            + "&adid=" + FSDevice.OS.getAndroidID(BaseApp.get())
            + "&brand=" + FSDevice.OS.getBrand()
            + "&model" + FSDevice.OS.getModel()
            + "&nt=" + getConnectType()
            + "&telecom=" + getOperation()
            + "&os_ver=" + FSDevice.OS.getVersion();


    private final String CURRENT_PLAY = "&curplay=1";
    private final String VIDEO_FEED_PLAY = "&videofeed=1";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hybird, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        manager = getChildFragmentManager();
        showDefault();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.hybird_menu, menu);

    }


    private static int getOperation() {
        FSDevice.Network.Operator operation = FSDevice.Network.getOperation(BaseApp.get());
        switch (operation) {
            case CHINA_MOBILE:
                return 70120;
            case CHINA_TELECOM:
                return 70121;
            case CHINA_UNICOM:
                return 70123;
            case UNKNOWN:
                return 0;
        }
        return 0;
    }

    //1—WIFI。2—5G以上。3—2G。4—3G。5—4G
    private static int getConnectType() {
        FSDevice.Network.Type wifiType = FSDevice.Network.getDetailType(BaseApp.get());
        int connectType;
        switch (wifiType) {
            case WIFI:
                connectType = 1;
                break;
            case MOBILE2G:
                connectType = 3;
                break;
            case MOBILE3G:
                connectType = 4;
                break;
            case MOBILE4G:
                connectType = 5;
                break;
            default:
                connectType = 0;
                break;
        }
        return connectType;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.feed_curplay:
                showDefault();
                break;

            case R.id.feed_normal_player:
                hideAll();
                FeedConfig.getInstance()
                        .setPlayerStyle(2);
                WebFragment fragment2 = WebFragment.newInstance(BASEURL, "");
                manager.beginTransaction().add(R.id.hybird_content, fragment2).commitAllowingStateLoss();

                break;
            case R.id.feed_player:
                hideAll();
                FeedConfig.getInstance()
                        .setPlayerStyle(0);
                WebFragment fragment3 = WebFragment.newInstance(BASEURL + VIDEO_FEED_PLAY, "");
                manager.beginTransaction().add(R.id.hybird_content, fragment3).commitAllowingStateLoss();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDefault() {
        hideAll();

        WebFragment fragment = WebFragment.newInstance(BASEURL + CURRENT_PLAY, "");
        manager.beginTransaction().add(R.id.hybird_content, fragment).commitAllowingStateLoss();
    }

    private void hideAll() {
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                manager.beginTransaction().remove(f).commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f instanceof WebFragment) {
                    WebFragment webFragment = (WebFragment) f;
                    webFragment.onPause();
                }
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f instanceof WebFragment) {
                    WebFragment webFragment = (WebFragment) f;
                    webFragment.onResume();
                }
            }

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onPause();
        } else {
            onResume();
        }
    }
}
