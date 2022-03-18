package com.yilan.sdk.sdkdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.sdk.sdkdemo.view.TitleLayout;
import com.yilan.sdk.ui.hybridfeed.HybridFeedFragment;
import com.yilan.sdk.ui.hybridfeed.HybridMultiFeedFragment;
import com.yilan.sdk.ui.little.channel.YLLittleChannelFragment;
import com.yilan.sdk.ui.littlevideo.KSLittleVideoFragment;
import com.yilan.sdk.ui.web.WebFragment;

import java.util.List;

public class ShortFragment extends BaseFragment implements TitleLayout.OptionsItemSelectedListener {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_short, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showDefault();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.natvie:
                showDefault();
                break;
            case R.id.hybird:
                hideAll();
                fragment = WebFragment.newInstance("https://sv.yilan.tv/Starscream/ylmv/index.html?access_key=ylel2vek386q", "");
                manager.beginTransaction().replace(R.id.short_content, fragment).commitAllowingStateLoss();
                break;
            case R.id.natvie_hybrid:
                hideAll();
                fragment = HybridFeedFragment.newInstance();
                manager.beginTransaction().replace(R.id.short_content, fragment).commitAllowingStateLoss();
                break;
            case R.id.natvie_hybrid_double:
                hideAll();
                fragment = HybridMultiFeedFragment.newInstance();
                manager.beginTransaction().replace(R.id.short_content, fragment).commitAllowingStateLoss();
                break;
            case R.id.natvie_kuaishou:
                hideAll();
                fragment = KSLittleVideoFragment.newInstance();
                manager.beginTransaction().replace(R.id.short_content, fragment).commitAllowingStateLoss();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    Fragment fragment;

    private void showDefault() {
        hideAll();
        fragment = new YLLittleChannelFragment();
        manager.beginTransaction().replace(R.id.short_content, fragment).commitAllowingStateLoss();
    }

    private void showKsDefault() {
        hideAll();
        fragment = KSLittleVideoFragment.newInstance();
        manager.beginTransaction().replace(R.id.short_content, fragment).commitAllowingStateLoss();
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
        if (fragment != null) {
            fragment.onHiddenChanged(hidden);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (fragment != null) {
            fragment.setUserVisibleHint(isVisibleToUser);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
