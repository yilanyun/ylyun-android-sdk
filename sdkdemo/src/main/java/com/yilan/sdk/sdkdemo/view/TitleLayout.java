package com.yilan.sdk.sdkdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.yilan.sdk.sdkdemo.R;

public class TitleLayout extends FrameLayout implements View.OnClickListener {

    private int menuResId;
    private ImageView mActionMore;
    private OptionsItemSelectedListener optionsItemSelectedListener;

    public TitleLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public TitleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @SuppressLint("RestrictedApi")
    private void init(Context context) {
        View layout = LayoutInflater.from(context).inflate(R.layout.layout_title, null);
        addView(layout);
        mActionMore = layout.findViewById(R.id.img_action);
        mActionMore.setOnClickListener(this);
    }

    public void onOptionsItemSelectedListener(OptionsItemSelectedListener listener) {
        optionsItemSelectedListener = listener;
    }

    public void setOptionsMenu(FragmentActivity activity, @MenuRes int menuResId) {
        if (activity == null) return;
        this.menuResId = menuResId;
        mActionMore.setVisibility(VISIBLE);
    }

    public void setActionInvisible() {
        mActionMore.setVisibility(GONE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_action) {
            showMenu();
        }
    }

    private void showMenu() {
        PopupMenu popupMenu = new PopupMenu(getContext(), mActionMore);
        popupMenu.inflate(menuResId);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (optionsItemSelectedListener != null) {
                    return optionsItemSelectedListener.onOptionsItemSelected(item);
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public interface OptionsItemSelectedListener {
        boolean onOptionsItemSelected(MenuItem item);
    }

}
