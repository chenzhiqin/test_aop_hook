package com.baidu.test.aop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TestReceiver extends BroadcastReceiver{
	private static String TAG = "TestReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "onReceive",new Exception("test Receiver"));
	}

}
