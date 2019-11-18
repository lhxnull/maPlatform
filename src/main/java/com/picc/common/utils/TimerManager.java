package com.picc.common.utils;


public class TimerManager {
    //服务是否通,默认通
    public volatile static  boolean bping = true;
    //是否开启开启功能开关
    public volatile static boolean flag = true;
    public volatile static String  ipAddress = "http://api.map.baidu.com";

    private volatile static TimerManager timerManager;

    public static TimerManager newInstance() {
        if (timerManager == null) {
            synchronized (TimerManager.class) {
                if (timerManager == null) {
                    timerManager = new TimerManager();
                }
            }
        }
        return timerManager;
    }

}
