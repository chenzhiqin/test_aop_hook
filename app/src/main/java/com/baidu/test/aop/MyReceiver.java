package com.baidu.test.aop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Log.e("MyReceiver", "onReceive intent : " + arg1.toURI());
//		Log.i("MyReceiver", "onReceive", new Exception("test Receiver"));
	}
}
