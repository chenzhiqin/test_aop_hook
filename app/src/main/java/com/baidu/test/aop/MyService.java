/*
 * Copyright (C) 2014 Baidu Inc. All rights reserved.
 */
package com.baidu.test.aop;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * 
 * @author chenyangkun
 * @since 2014年11月28日
 */
public class MyService extends Service {

//    public static class MyBinder extends IMyAidl.Stub {
//
//        @Override
//        public void test() throws RemoteException {
//            android.util.Log.e("CYK", "aidl is called, test");
//        }
//
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return new MyBinder();
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("############################### MyService start!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.err.println("############################### MyService onStartCommand! id=" + startId);
        return super.onStartCommand(intent, flags, startId);
    }
    
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        
        System.out.println("xxx ############################### MyService onDestroy!");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
