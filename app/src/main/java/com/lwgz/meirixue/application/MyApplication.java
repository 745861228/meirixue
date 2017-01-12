package com.lwgz.meirixue.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * author by LiKe on 2017/1/10.
 */

public class MyApplication extends Application {

    private static Context context;
    private static Handler handler;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        //配置autoLayout
        AutoLayoutConifg.getInstance().useDeviceSize();
        context = getApplicationContext();
        //创建handler
        handler = new Handler();
        //获取主线程号
        mainThreadId = Process.myTid();
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

    public static Handler getHandler() {
        return handler;
    }

    /**
     * 获取主线程
     *
     * @return
     */
    public static Thread getMainThread() {
        return Thread.currentThread();
    }


    /**
     * 获取整个应用的上下文
     */
    public static Context getContext(){
        return context;
    }



}
