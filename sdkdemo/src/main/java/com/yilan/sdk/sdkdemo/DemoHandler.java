package com.yilan.sdk.sdkdemo;

public class DemoHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("------DemoHandler捕获到异常");
        e.printStackTrace();
    }
}
