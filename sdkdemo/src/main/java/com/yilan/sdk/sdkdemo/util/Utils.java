package com.yilan.sdk.sdkdemo.util;

public class Utils {
    public static String getSourceName(int source) {
        String sourceName = "";
        if (source == 202) {
            sourceName = "一览";
        } else if (source == 23) {
            sourceName = "快手";
        } else if (source == 20 || source == 4) {
            sourceName = "穿山甲";
        } else if (source == 21 || source == 22) {
            sourceName = "广点通";
        }
        return sourceName;
    }
}
