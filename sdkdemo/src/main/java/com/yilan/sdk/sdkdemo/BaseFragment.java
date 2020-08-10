package com.yilan.sdk.sdkdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.yilan.sdk.ui.littlevideo.LittleVideoFragment;
import com.yilan.sdk.ui.web.WebFragment;

import java.util.List;

public class BaseFragment extends Fragment {
    protected FragmentManager manager;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        onHidden(hidden);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        manager = getChildFragmentManager();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        setUserHint(isVisibleToUser);
    }

    private void setUserHint(boolean isVisibleToUser) {
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f instanceof WebFragment) {
                    WebFragment webFragment = (WebFragment) f;
                    webFragment.setUserVisibleHint(isVisibleToUser);
                }
                if (f instanceof LittleVideoFragment) {
                    LittleVideoFragment littleVideoFragment = (LittleVideoFragment) f;
                    littleVideoFragment.setUserVisibleHint(isVisibleToUser);
                }
            }
        }
    }

    private void onHidden(boolean hidden) {
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f instanceof WebFragment) {
                    WebFragment webFragment = (WebFragment) f;
                    webFragment.onHiddenChanged(hidden);
                }
                if (f instanceof LittleVideoFragment) {
                    LittleVideoFragment littleVideoFragment = (LittleVideoFragment) f;
                    littleVideoFragment.onHiddenChanged(hidden);
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    private void resume() {
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f instanceof WebFragment) {
                    WebFragment webFragment = (WebFragment) f;
                    webFragment.onResume();
                }
                if (f instanceof LittleVideoFragment) {
                    LittleVideoFragment littleVideoFragment = (LittleVideoFragment) f;
                    littleVideoFragment.onResume();
                }
            }
        }
    }

    private void pause() {
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f instanceof WebFragment) {
                    WebFragment webFragment = (WebFragment) f;
                    webFragment.onPause();
                }
                if (f instanceof LittleVideoFragment) {
                    LittleVideoFragment littleVideoFragment = (LittleVideoFragment) f;
                    littleVideoFragment.onPause();
                }
            }
        }
    }
}
