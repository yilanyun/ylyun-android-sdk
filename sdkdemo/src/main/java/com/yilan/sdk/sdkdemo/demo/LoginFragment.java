package com.yilan.sdk.sdkdemo.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yilan.sdk.common.util.ToastUtil;
import com.yilan.sdk.data.entity.user.UserEntity;
import com.yilan.sdk.data.user.YLUser;
import com.yilan.sdk.sdkdemo.R;

public class LoginFragment extends Fragment {

    private YLUser.LoginStateChange loginState;
    private TextView user_name;
    private TextView user_phone;
    private String nick;
    private String phone;
    private String userId;


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginState = new YLUser.LoginStateChange() {
            @Override
            public void onStateChange(boolean isLogin) {
                if (getActivity() != null) {
                    ToastUtil.show(getActivity().getApplicationContext(), isLogin ? "登录成功" : "已退出登录");
                    user_name.setText(isLogin ? userId : "未登录");
                    user_phone.setText(isLogin ? phone : "");
                }
            }
        };
        YLUser.getInstance().addListener(loginState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nick = ((EditText) view.findViewById(R.id.nick)).getText().toString();
        phone = ((EditText) view.findViewById(R.id.phone)).getText().toString();
        userId = ((EditText) view.findViewById(R.id.userid)).getText().toString();
        view.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nick = ((EditText) view.findViewById(R.id.nick)).getText().toString();
                String avatar = ((EditText) view.findViewById(R.id.avatar)).getText().toString();
                phone = ((EditText) view.findViewById(R.id.phone)).getText().toString();
                userId = ((EditText) view.findViewById(R.id.userid)).getText().toString();
                YLUser.getInstance().login(nick, avatar, phone, userId);
            }
        });
        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YLUser.getInstance().logout();
            }
        });
        user_name = view.findViewById(R.id.user_name);
        user_phone = view.findViewById(R.id.user_phone);
        UserEntity userEntity = YLUser.getInstance().getUser();
        if (userEntity == null) {
            user_name.setText("未登录");
            user_phone.setText("");
        } else {
            user_name.setText(userEntity.getUserId());
            user_phone.setText(userEntity.getPhone());
        }
        view.findViewById(R.id.gettoken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEntity userEntity = YLUser.getInstance().getUser();
                if (userEntity == null) {
                    Toast.makeText(getContext(), "user: ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "user: " + userEntity.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}