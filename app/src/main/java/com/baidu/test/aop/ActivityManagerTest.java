/*
 * Copyright (C) 2015 Baidu Inc. All rights reserved.
 */
package com.baidu.test.aop;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.baidu.test.aop.annotation.ActivityAnnotation;
import com.baidu.test.aop.annotation.FileAnnotation;

/**
 * 
 * @author caohaitao
 * @since 2015年11月9日
 */
@FileAnnotation("ActivityManagerTest")
public class ActivityManagerTest extends ListActivity {
    
    private enum Id {
        Broadcast, //registerReceiver , broadcastIntent
        PendingIntent, //getIntentSender
        Start_Stop_Service, //startService stopService
        Bind_Unbind_Service, // bindService , unbindService
        Provider, // getContentProvider
        CreateShortcut, // broadcastIntent
    }

    @ActivityAnnotation("onCreate")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use an existing ListAdapter that will map an array
        // of strings to TextViews
        setListAdapter(new SimpleAdapter(this, getData(),
                android.R.layout.simple_list_item_1, new String[] { "title" },
                new int[] { android.R.id.text1 }));
    }
    
    protected List<Map<String, Object>> getData() {
        List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
        
        Id[] ids = Id.values();
        
        for (Id id : ids) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("title", id.name());
            item.put("id", id);
            
            data.add(item);
        }
        
        return data;
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> item = (Map<String, Object>) l.getItemAtPosition(position);
        Id itemid = (Id) item.get("id");
        
        switch(itemid) {
        case Broadcast:
            testBroadcast();
            break;
        case PendingIntent:
            testGetIntentSender();
            break;
        case Start_Stop_Service:
            testService();
            break;
        case Bind_Unbind_Service:
            testBindService();
            break;
        case Provider:
            testProvider();
            break;
            
        case CreateShortcut:
            createShortCut();
            break;
        }
    }
    
    private void toast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
    
    private void testBroadcast() {
        String action = "com.baidu.gpt.test.action.broadcast";
        
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(action);
        
        BroadcastReceiver receiver = new BroadcastReceiver() {
            
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("xxxx boradcast received " + intent);
                toast("xxxx boradcast received " + intent);
                
                unregisterReceiver(this);
            }
        };
        
        registerReceiver(receiver, intentFilter);
        
        //
        
        Intent intent = new Intent(action);
        sendBroadcast(intent);
    }
    
    private void testGetIntentSender() {
        Intent intent = new Intent();
        intent.setClass(this, AnimationAlphaActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 100, intent, 0);
        
        try {
            pi.send();
        } catch (CanceledException e) {
            e.printStackTrace();
        }
    }
    
    boolean start = true;
    private void testService() {
        // 测试前需要吧 bindservice 去掉。要不然 ondestroy 不会被回调。
        
        Intent intent = new Intent();
        intent.setClass(this, MyService.class);
        
        if (start) {
            ComponentName cn = getApplicationContext().startService(intent);
            if (cn == null) {
                toast("service not started");
            } else {
                toast("service started");
            }
        } else {
            boolean result = getApplicationContext().stopService(intent);
            if (result) {
                toast("service stoped");
            } else {
                toast("service stop failed !!!");
            }
        }
        
        start = !start;
    }
    
    private ServiceConnection mConn = null;
    private void testBindService() {
//        Intent sintent = new Intent(this, MyService.class);
//        if (mConn == null) {
//            mConn = new ServiceConnection() {
//
//                public void onServiceDisconnected(ComponentName name) {
//                    System.out.println("xxxxxxxxxxxx onServiceDisconnected cp :" + name);
//
//                }
//
//                public void onServiceConnected(ComponentName name, IBinder service) {
//                    System.out.println("xxxxxxxxxxxx onServiceConnected cp :" + name);
//
//                    IMyAidl inter = IMyAidl.Stub.asInterface(service);
//                    try {
//                        inter.test();
//                    } catch (RemoteException e) {
//                        android.util.Log.e("CYK", "RemoteException ==== " + e.getMessage());
//                    }
//
//                }
//            };
//            boolean result = bindService(sintent, mConn, Context.BIND_AUTO_CREATE);
//            if (result) {
//                toast("bind service success");
//            } else {
//                toast("bind service failed!!!");
//            }
//        } else {
//            unbindService(mConn);
//            mConn = null;
//        }
    }
    
    private void testProvider() {
        PackageManager pm = getPackageManager();
        
        try {
            String packageName = pm.getPackageInfo(getPackageName(), 0).packageName;
            System.out.println("xxx " + packageName);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        
        
        ContentResolver cr = getContentResolver();
        
        Uri uri = Uri.parse("content://" + MyProvider.AUTH);
        String[] projection = {"name", "age"};
        
        Cursor c = cr.query(uri, projection, null, null, null);
        
        boolean success = false;
        while (c.moveToNext()) {
            int cc = c.getColumnCount();
            for (int i = 0; i < cc; i++) {
                System.out.print(c.getString(i) + " ");
            }
            
            System.out.println("----------");
            success = true;
        }
        
        c.close();
        
        if (success) {
            toast("get provider success");
        } else {
            toast("get provider failed");
        }
    }
    
    public void createShortCut(){     
        //创建快捷方式的Intent                     
        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");                     
        //不允许重复创建                     
        shortcutintent.putExtra("duplicate", false);                     
        //需要现实的名称                     
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));     
        //快捷图片                    
        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.ic_launcher);     
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);     
        //点击快捷图片，运行的程序主入口                     
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext() , AnimationAlphaActivity.class));                     
        //发送广播。OK                     
        sendBroadcast(shortcutintent);     
    }  
}
