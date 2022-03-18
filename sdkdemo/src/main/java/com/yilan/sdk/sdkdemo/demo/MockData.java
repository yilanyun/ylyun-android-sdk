package com.yilan.sdk.sdkdemo.demo;


public class MockData {

    static String[] urls = {
            "https://vv.qianpailive.com/7d80/20210714/dfdb4ee9b51c121c7127187ed4411863?auth_key=1626283328-0-0-9b10fd44889fa42ebab0200477b07554",
            "https://vv.qianpailive.com/386e/20210714/f565c8e864722678f4a0e1b64570c7fe?auth_key=1626283328-0-0-8aa72bd4ba852e9f1855352b30694da0",
            "https://vv.qianpailive.com/7452/20210714/d0ddbe5bc1ed7155dc838ac2617e18e9?auth_key=1626283328-0-0-1c0f2094de201fbe1ec41f82baa88fbd",
    };

    public static String getPlayerUrl() {
        return "https://vv.qianpailive.com/7d80/20210714/dfdb4ee9b51c121c7127187ed4411863?auth_key=1626283328-0-0-9b10fd44889fa42ebab0200477b07554";
    }

    public static String getPlayerUrl(int index) {
        return urls[index];
    }

}
