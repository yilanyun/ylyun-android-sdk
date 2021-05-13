package com.yilan.sdk.sdkdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.sdk.ui.littlevideo.KSLittleVideoFragment;
import com.yilan.sdk.uibase.ui.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class ShortFragment2 extends BaseFragment  {

    List<Fragment> fragments = new ArrayList<>();

    CustomViewPager viewPager;
    MyPagerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_short2, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        manager = getChildFragmentManager();
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setCanScroll(true);
        initFragment();
        viewPager.setOffscreenPageLimit(3);
        adapter = new MyPagerAdapter(manager, fragments);
        viewPager.setAdapter(adapter);
    }

    private void initFragment() {
        Fragment fragment = new ShortFragment();
        Fragment ks = KSLittleVideoFragment.newInstance();
        fragments.add(fragment);
        fragments.add(ks);
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
    public void onDestroy() {
        super.onDestroy();
    }
}
