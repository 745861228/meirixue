package com.lwgz.meirixue.utils;

import android.util.Log;

/**
 * Created by LiKe on 17/1/11.
 */
public class LogUtils {
    public static final  boolean isDebug=true;
    public static void i(String TAG, String info){
        if(isDebug){
            Log.i(TAG,info);
        }
    }
    public static void d(String TAG, String info){
        if(isDebug){
            Log.d(TAG,info);
        }
    }
    public static void e(String TAG, String info){
        if(isDebug){
            Log.e(TAG,info);
        }
    }
}
