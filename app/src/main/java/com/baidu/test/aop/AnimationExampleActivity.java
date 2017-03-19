package com.baidu.test.aop;

import java.io.File;
import java.util.List;


import android.annotation.SuppressLint;
import android.app.AlarmManager;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.test.aop.R;
import com.baidu.test.aop.annotation.ActivityAnnotation;
import com.baidu.test.aop.annotation.FileAnnotation;

/**
 * @author Harlan Song
 * @weibo: weibo.com/markdev
 * 2012-8-27
 */
@FileAnnotation("AnimationExampleActivity")
public class AnimationExampleActivity extends FragmentActivity implements OnClickListener {
    private static final String TAG = "AnimationExampleActivity";

    @Override
    public void onNewIntent(Intent paramIntent) {
        super.onNewIntent(paramIntent);
        Toast.makeText(getApplicationContext(), "home onNewIntent", Toast.LENGTH_SHORT).show();
    }

    private ServiceConnection mConn = null;

    /** Called when the activity is first created. */
    @SuppressLint("LongLogTag")
    @Override
    @ActivityAnnotation("onCreate")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTimeManger.onCreateStart(this);
        setContentView(R.layout.main);
        setUp();

        Log.e(TAG, "---- begin encode");

        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(mReceiver, filter);


        Intent sintent = new Intent(this, MyService.class);
        mConn = new ServiceConnection() {

            public void onServiceDisconnected(ComponentName name) {
                System.out.println("xxxxxxxxxxxx onServiceDisconnected cp :" + name);

            }

            public void onServiceConnected(ComponentName name, IBinder service) {
                System.out.println("xxxxxxxxxxxx onServiceConnected cp :" + name);


            }
        };
        bindService(sintent, mConn, Context.BIND_AUTO_CREATE);
        // 测试javassist
        new Test();
        ActivityTimeManger.onCreateEnd(this);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive :: " + intent.getAction() + ", context = " + context);
        }
    };

    public void onDestroy() {
        super.onDestroy();
    	if(mReceiver != null ) {
    		unregisterReceiver(mReceiver);
    		Log.e(TAG, "---- unregisterReceiver");
    		mReceiver = null;
    	}

        // Intent sintent = new Intent(this, MyService.class);
        // stopService(sintent);

        if (mConn != null) {
            unbindService(mConn);
        }

    };

    private void setUp() {
        findViewById(R.id.btn_alpha).setOnClickListener(this);
        findViewById(R.id.btn_scale).setOnClickListener(this);
        findViewById(R.id.btn_translate).setOnClickListener(this);
        findViewById(R.id.btn_rotate).setOnClickListener(this);
        findViewById(R.id.btn_fragment).setOnClickListener(this);
        findViewById(R.id.btn_mode).setOnClickListener(this);
        findViewById(R.id.btn_dialog_activity).setOnClickListener(this);
        findViewById(R.id.btn_remote_call).setOnClickListener(this);
        findViewById(R.id.btn_notification).setOnClickListener(this);
        findViewById(R.id.btn_taskroot).setOnClickListener(this);
        findViewById(R.id.btn_wifi).setOnClickListener(this);
        findViewById(R.id.btn_wm).setOnClickListener(this);
        findViewById(R.id.btn_datadir).setOnClickListener(this);
        findViewById(R.id.btn_pkgmgr).setOnClickListener(this);
        findViewById(R.id.btn_activitymgr).setOnClickListener(this);
        findViewById(R.id.btn_alarm_send_intent).setOnClickListener(this);
        findViewById(R.id.btn_notification_remote_view).setOnClickListener(this);
        findViewById(R.id.btn_tab_activity).setOnClickListener(this);
        findViewById(R.id.btn_test_receiver).setOnClickListener(this);
        findViewById(R.id.btn_test_popup_window).setOnClickListener(this);
    }

	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_alpha:
            intent = new Intent(AnimationExampleActivity.this, AnimationAlphaActivity.class);
             startActivity(intent);
			break;
		case R.id.btn_scale:
            intent = new Intent(AnimationExampleActivity.this, AnimationScaleActivity.class);
            startActivityForResult(intent, 1);
			break;
		case R.id.btn_translate:
			intent=new Intent(AnimationExampleActivity.this,AnimationTranslateActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_rotate:
			intent=new Intent(AnimationExampleActivity.this,AnimationRotateActivity.class);
			startActivity(intent);
			break;
        case R.id.btn_fragment:
            intent = new Intent(AnimationExampleActivity.this, FragmentTestActivity.class);
            startActivity(intent);
            break;
        case R.id.btn_mode:
            intent = new Intent(AnimationExampleActivity.this, LaunchModeTestActivity.class);
            startActivity(intent);
            break;
        case R.id.btn_dialog_activity:
            intent = new Intent(AnimationExampleActivity.this, TestDialogActivity.class);
            startActivity(intent);
            break;
        case R.id.btn_notification:
            testNotification();
            break;
        case R.id.btn_taskroot:
            boolean isroot = isTaskRoot();
            System.out.println("xxx isroot " + isroot);
            break;
        case R.id.btn_wm:
            testWindowManager();
            break;
        case R.id.btn_wifi:
//            Toast.makeText(getApplicationContext(), "Context是插件的Toast", Toast.LENGTH_SHORT).show();
//            Activity act = AnimationExampleActivity.this.getParent();
//            if (act == null) {
//                act = AnimationExampleActivity.this;
//            }
//            Toast.makeText(act, "Context是宿主的Toast", Toast.LENGTH_SHORT).show();
//        	    getSystemService(PackageManager)
        	    PackageManager pkgManager= getPackageManager();
			int acessLocation = pkgManager.checkPermission("android.permission.ACCESS_FINE_LOCATION", getPackageName());
			Log.i(TAG, "acessLocation:" + acessLocation);
            WifiManager wifiManager=(WifiManager) getSystemService(WIFI_SERVICE);
            wifiManager.setWifiEnabled(true);
            break;
        case R.id.btn_datadir:
            testDataDir();
            break;
        case R.id.btn_pkgmgr:
            testPm();
            break;
        case R.id.btn_activitymgr:
            intent = new Intent();
            intent.setClass(this, ActivityManagerTest.class);
            startActivity(intent);
            break;
        case R.id.btn_alarm_send_intent:
            testAlarmSendIntent();
            break;
        case R.id.btn_notification_remote_view:
            testNotificationRemoteView();
            break;
        case R.id.btn_tab_activity:
            intent = new Intent(this,TestTabActvity.class);
            startActivity(intent);
            break;
        case R.id.btn_test_receiver:
        	testReceiver();
//        	View view = findViewById(R.id.btn_test_receiver);
//        	view.requestFitSystemWindows();
//        	Choreographer.getInstance().postFrameCallback(new FrameCallback() {
//				
//				@Override
//				public void doFrame(long frameTimeNanos) {
//					Log.i(TAG, "doFrame",new Exception("doFrame"));
//				}
//			});
        	break;
        case R.id.btn_test_popup_window:
        	testPopupWindows(v);
        	break;
        default:
			break;
		}

	}


    private void testReceiver() {
		IntentFilter filter = new IntentFilter(getPackageName() + ".test");
		TestReceiver testReceiver = new TestReceiver();
		registerReceiver(testReceiver, filter);
		Intent intent = new Intent(getPackageName() + ".test");
		sendBroadcast(intent);
//		unregisterReceiver(testReceiver);

		Intent intent2= new Intent("com.baidu.host.demo.myreceiver");
		sendBroadcast(intent2);
	}

	private void testWindowManager() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        TextView tx = new TextView(getApplicationContext());
        View myView = getLayoutInflater().inflate(R.layout.test, null);
        tx.setText(R.string.app_name);
        WindowManager.LayoutParams param = new WindowManager.LayoutParams();
        param.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        param.height = WindowManager.LayoutParams.WRAP_CONTENT;
        param.width = WindowManager.LayoutParams.MATCH_PARENT;
        param.x = 0;
        param.y = 0;
        param.packageName = getPackageName();
        wm.addView(myView, param);
    }


    private void testNotification() {


        testNotificationImpl();
    }

    private void testNotificationImpl() {

    }




    private void testDataDir() {
        File file = getExternalCacheDir();
        System.out.println("xxx external cache dir " + file);

        file = getExternalFilesDir("picture");
        System.out.println("xxx external files dir(picture) " + file);


        file = getApplicationContext().getExternalCacheDir();
        System.out.println("xxx external cache dir " + file);

        file = getApplicationContext().getExternalFilesDir("picture");
        System.out.println("xxx external files dir(picture) " + file);


        System.out.println(getApplicationContext().getPackageName());


    }



    private void testPm() {
        String pkgName = getPackageName();
        System.out.println("xxx testPm " + pkgName);

        // getPackageInfo
        PackageManager pm = getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(pkgName, 0);
            System.out.println("xxx getPackageInfo pkg name " + pi.packageName);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        if (pi == null || !pi.packageName.equals(pkgName)) {
            throw new RuntimeException("getPackageInfo failed");
        }


        // getApplicationInfo
        ApplicationInfo ai = null;
        try {
            ai = pm.getApplicationInfo(pkgName, 0);
            System.out.println("xxx getApplicationInfo pkg name " + ai.packageName);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        if (ai == null || !ai.packageName.equals(pkgName)) {
            throw new RuntimeException("getPackageInfo failed");
        }

        // getActivityInfo
        ActivityInfo activityInfo = null;
        try {
            activityInfo = pm.getActivityInfo(new ComponentName(this, AnimationAlphaActivity.class), 0);
            System.out.println("xxx getActivityInfo activityInfo " + activityInfo);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        if (activityInfo == null || !activityInfo.name.equals(AnimationAlphaActivity.class.getCanonicalName())) {
            throw new RuntimeException("getActivityInfo failed");
        }


        // getReceiverInfo
        ActivityInfo receiverInfo = null;
        try {
            receiverInfo = pm.getReceiverInfo(new ComponentName(this, MyReceiver.class), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (receiverInfo == null || !receiverInfo.name.equals(MyReceiver.class.getCanonicalName())) {
            throw new RuntimeException("getReceiverInfo failed");
        }

        // getServiceInfo
        ServiceInfo si = null;
        try {
            si = pm.getServiceInfo(new ComponentName(this, MyService.class), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (si == null || !si.name.equals(MyService.class.getCanonicalName())) {
            throw new RuntimeException("getServiceInfo failed");
        }

        // getProviderInfo
        ProviderInfo providerInfo = null;
        try {
            providerInfo = pm.getProviderInfo(new ComponentName(this, MyProvider.class), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (providerInfo == null || !providerInfo.name.equals(MyProvider.class.getCanonicalName())) {
            throw new RuntimeException("getProviderInfo failed");
        }


        // queryIntentActivities
        Intent intent = new Intent();
        intent.setComponent((new ComponentName(this, AnimationAlphaActivity.class)));
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        if (infos.size() == 0) {
            throw new RuntimeException("queryIntentActivities failed");
        } else {
            for (ResolveInfo ri : infos) {
                System.out.println("xxx queryIntentActivities " + ri);
            }
        }

        Toast.makeText(this, "PackageManager 测试通过", Toast.LENGTH_LONG).show();
    }


    private void testNotificationRemoteView() {
    }


    private void testAlarmSendIntent(){
        Context context = getApplicationContext();
        context.registerReceiver(new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(TAG, "onReceive : action:" + intent.getAction());
            }
        }, new IntentFilter("my.action"));
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent("my.action");
//        intent.setPackage(context.getPackageName());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        try {
            alarmManager.cancel(pendingIntent);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        try {
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000, pendingIntent);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String rs = "";
        if (data != null) {
            rs = data.getStringExtra("rs");
        }
        Log.i(TAG, "onActivityResult", new Exception("onActivityResult"));
        Log.i(TAG, "onActivityResult: requestCode:"+ requestCode +",resultCode " + resultCode +", rs :" + rs);
        Toast.makeText(this, "onActivityResult rs:" + rs, Toast.LENGTH_LONG).show();
    }

    private void testPopupWindows(View v) {
    	View view = LayoutInflater.from(this).inflate(R.layout.test_popup_window,null);
		final PopupWindow pop = new PopupWindow(view, getResources().getDimensionPixelSize(R.dimen.popwindow_width),
				getResources().getDimensionPixelSize(R.dimen.popwindow_height), true);
    	pop.setOutsideTouchable(false);
        pop.setBackgroundDrawable(new ColorDrawable(0));
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (pop.isShowing()) {
                    pop.dismiss();
                }
            }
        });
        int[] xy = new int[2];
        v.getLocationInWindow(xy);
        pop.showAtLocation(v, Gravity.NO_GRAVITY, 0, 0);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

		int popWindowAnchorY = xy[1] + pop.getContentView().getMeasuredHeight() / 2;
		pop.update((wm.getDefaultDisplay().getWidth() - getResources().getDimensionPixelSize(R.dimen.popwindow_offset))
				/ 2, xy[1] - getResources().getDimensionPixelSize(R.dimen.popwindow_offset), -1, -1);


    }


}