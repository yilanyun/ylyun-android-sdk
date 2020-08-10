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

import com.yilan.sdk.entity.Channel;
import com.yilan.sdk.ui.category.ChannelFragment;
import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.ui.configs.PlayerConfig;

import java.util.List;

public class UIFragment extends BaseFragment {
    private FragmentManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ui, container, false);
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
        inflater.inflate(R.menu.ui_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.n2n:
                showDefault();
                break;

            case R.id.n2nf:
                hideAll();
                FeedConfig.getInstance()
                        .setPlayerStyle(FeedConfig.STYLE_NATIVE_FEED);
                ChannelFragment fragment2 = new ChannelFragment();
                manager.beginTransaction().add(R.id.ui_content, fragment2).commitAllowingStateLoss();
                channelFragment = fragment2;
                break;

            case R.id.nc:
                hideAll();
                FeedConfig.getInstance()
                        .setPlayerStyle(FeedConfig.STYLE_FEED_PLAY);
                ChannelFragment fragment4 = new ChannelFragment();
                manager.beginTransaction().add(R.id.ui_content, fragment4).commitAllowingStateLoss();
                channelFragment = fragment4;
                break;
            case R.id.refresh_feed:
                if (channelFragment!=null){
                    channelFragment.refresh();
                }
                break;
            case R.id.refresh_open:
                if (channelFragment!=null){
                    channelFragment.swipeEnableChange();
                }
                break;
            default:
                hideAll();
                FeedConfig.getInstance()
                        .setPlayerStyle(FeedConfig.STYLE_WEB);
                ChannelFragment fragment3 = new ChannelFragment();
                manager.beginTransaction().add(R.id.ui_content, fragment3).commitAllowingStateLoss();

                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private ChannelFragment channelFragment;
    private void showDefault() {
        hideAll();

        ChannelFragment fragment = new ChannelFragment();
        manager.beginTransaction().add(R.id.ui_content, fragment).commitAllowingStateLoss();
        channelFragment = fragment;
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        FragmentManager manager = getChildFragmentManager();
        if (manager == null) {
            manager = getFragmentManager();
        }
        List<Fragment> fragmentList = manager.getFragments();
        if (fragmentList == null) return;
        for (Fragment fragment : fragmentList) {
            fragment.onHiddenChanged(hidden);
        }
    }

    public boolean canBack() {
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f instanceof ChannelFragment) {
                    ChannelFragment channelFragment = (ChannelFragment) f;
                    return channelFragment.canBack();
                }
            }
        }
        return true;
    }
}
