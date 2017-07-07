package com.wj.xpullze.com.wj.xpullze.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author
 * @version 1.0
 * @date 2017/7/6
 * 
 */

public class ScreenUtils {
    /**
     * 获取屏幕的尺寸大小
     * @param context
     * @return DisplayMetrics
     */
    public static DisplayMetrics getScreenMetric(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    /**
     * 获取屏幕的密度
     * @param context
     * @return
     */
    public static float getDeviceDensity(Context context){
        return getScreenMetric(context).density;
    }
}
