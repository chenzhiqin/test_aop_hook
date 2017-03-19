/*
 * Copyright (C) 2011 Baidu Inc. All rights reserved.
 */
package com.baidu.test.aop;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.baidu.test.aop.dexposed.DexposedManager;
import com.taobao.android.dexposed.DexposedBridge;

/**
 * 
 * @author caohaitao
 * @since 2014年11月27日
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DexposedBridge.canDexposed(this);
        DexposedManager.getDexposedManager().start();
   
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DexposedManager.getDexposedManager().stop();
    }
}
