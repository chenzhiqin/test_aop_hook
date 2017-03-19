package com.baidu.test.aop.dexposed;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Choreographer;

import com.taobao.android.dexposed.DexposedBridge;
import com.taobao.android.dexposed.XC_MethodHook;

import java.lang.reflect.Member;
import java.util.HashMap;


/**
 * Dexposed 单例
 * Created by chenzhiqin on 17/2/26.
 */

public class DexposedManager {
    static {
        // load xposed lib for hook.
        try {
            if (android.os.Build.VERSION.SDK_INT == 22){
                System.loadLibrary("dexposed_l51");
            } else if (android.os.Build.VERSION.SDK_INT > 19 && android.os.Build.VERSION.SDK_INT <= 21){
                System.loadLibrary("dexposed_l");
            } else if (android.os.Build.VERSION.SDK_INT > 14){
                System.loadLibrary("dexposed");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    private static final String TAG = "DexposedManager";
    private long mFrameIntervalNanos;
    private long mTolerableSkippedNanos;
    private XC_MethodHook.Unhook onVsync;
    private long jitterNanos;
    private boolean isStart = false;

    private static DexposedManager dexposedManager ;

    private DexposedManager() {
        if(!isEnable())
            return;

        try {
            mFrameIntervalNanos = (Long) Choreographer.class.getDeclaredField("mFrameIntervalNanos")
                    .get(Choreographer.getInstance());
        } catch (Exception e) {
            mFrameIntervalNanos = 1000000000 / 60;
        }

        mTolerableSkippedNanos = 15 * mFrameIntervalNanos;
    }

    public static DexposedManager getDexposedManager() {
        if (dexposedManager == null) {
            dexposedManager = new DexposedManager();
        }
        return dexposedManager;
    }

    private boolean isEnable(){
        if(Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 21) {
            return true;
        } else
            return false;
    }

    public void start() {
        if(!isEnable()) return;

        if(isStart) return;

        hook();
        hookActivity();
        hookDoFrame();

        isStart = true;
    }

    public void stop() {
        if(!isEnable()) return;

        if(!isStart) return;

        unhook();
        unHookActivity();
        unHookDoFrame();
        isStart = false;

    }

    private void hook() {

        Class<?> cls = null;
        try {
            cls = Class.forName("android.view.Choreographer$FrameDisplayEventReceiver");
        } catch (ClassNotFoundException e) {
            Log.w(TAG, "fail to find class FrameDisplayEventReceiver");
            e.printStackTrace();
            return;
        }

        XC_MethodHook mehodHook =  new XC_MethodHook() {
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                long timestampNanos = (Long)param.args[0];
//				int  builtInDisplayId = (Integer)param.args[1];
//				int  frame = (Integer)param.args[2];

                long startNanos = System.nanoTime();
                jitterNanos = startNanos - timestampNanos;
                // we skip the frame that had been violate the mTolerableSkippedNanos when onVsync() arrived.
                if (jitterNanos >= mTolerableSkippedNanos) {
                    final long skippedFrames = jitterNanos / mFrameIntervalNanos;
                    Log.i(TAG, "main thread skip " + skippedFrames + "frames");
                }
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

            }
        } ;

        //Choreographer 在4.1系统才有，并且4.2与4.1在onVsync方法的参数上有区别
        if(Build.VERSION.SDK_INT >= 17) {
            onVsync = DexposedBridge.findAndHookMethod(cls, "onVsync", long.class, int.class, int.class, mehodHook);
        }
        else if(Build.VERSION.SDK_INT == 16){
            onVsync = DexposedBridge.findAndHookMethod(cls, "onVsync", long.class, int.class, mehodHook);
        }
    }

    private void unhook() {

        if(onVsync != null) {
            onVsync.unhook();
            onVsync = null;
        }
    }


    /**
     * Activity 方法的hook
     */
    public static class CoastTimeMethodHook extends XC_MethodHook {
        HashMap<String, Long> startTime = new HashMap<String, Long>();

        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            Member method =  param.method;
            startTime.put(method.toString(), SystemClock.elapsedRealtime());
        }

        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            Member method =  param.method;
            long time0 = startTime.get(method.toString());
            long time = SystemClock.elapsedRealtime() - time0;
            Log.i(TAG, method + ", onCreate time:" + time);
            startTime.remove(method.toString());
        }
    }

    private XC_MethodHook.Unhook actvityUnhook = null;
    public void hookActivity() {
        CoastTimeMethodHook activityMethodHook = new CoastTimeMethodHook();
        actvityUnhook =  DexposedBridge.findAndHookMethod(Activity.class,"onCreate", Bundle.class, activityMethodHook);
    }

    public void unHookActivity() {
        if(actvityUnhook!=null) {
            actvityUnhook.unhook();
        }
    }


    /**
     * doFrame 方法的hook
     */
    public static class FrameMethodHook extends XC_MethodHook {

        private long startTime = 0;
        private int frameNo = -1;

        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            startTime = SystemClock.elapsedRealtime();
            frameNo = (Integer) param.args[1];
        }

        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            long coastTime = SystemClock.elapsedRealtime() - startTime;
            Log.i(TAG, "frameNo:" + frameNo + ", coastTime:" + coastTime);
        }
    }


    private XC_MethodHook.Unhook frameMethodunHook = null;

    public void hookDoFrame() {
        FrameMethodHook frameMethodHook = new FrameMethodHook();
        frameMethodunHook = DexposedBridge.findAndHookMethod(Choreographer.class, "doFrame", long.class, int.class, frameMethodHook);
    }

    public void unHookDoFrame() {
        if (frameMethodunHook != null) {
            frameMethodunHook.unhook();
        }
    }
}
