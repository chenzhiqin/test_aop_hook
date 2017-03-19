package com.baidu.test.aop;

import android.app.Activity;

import java.util.HashMap;

/**
 * Actvity 方法执行的统计
 * @author chenzhiqin
 *
 */
public class ActivityTimeManger {
	public static HashMap<String, Long> startTimeMap = new HashMap<>();
	public static HashMap<String, Long> resumeTimeMap = new HashMap<>();
	public static HashMap<String, Long> pauseTimeMap = new HashMap<>();
	public static HashMap<String, Long> stopTimeMap = new HashMap<>();
	public static HashMap<String, Long> destroyTimeMap = new HashMap<>();

	
	public static void onCreateStart(Activity activity) {
		startTimeMap.put(activity.toString(), System.currentTimeMillis());
	}

	public static void onCreateEnd(Activity activity) {
		Long startTime = startTimeMap.get(activity.toString());
		if (startTime == null) {
			return;
		}
		long coastTime = System.currentTimeMillis() - startTime;
		System.out.println(activity.toString() + " onCreate coast Time" + coastTime);
		startTimeMap.remove(activity.toString());
	}
	
	
	
	public static void onResumeStart(Activity activity) {
		resumeTimeMap.put(activity.toString(), System.currentTimeMillis());
	}

	public static void onResumeEnd(Activity activity) {
		Long startTime = resumeTimeMap.get(activity.toString());
		if (startTime == null) {
			return;
		}
		long coastTime = System.currentTimeMillis() - startTime;
		System.out.println(activity.toString() + " onResume coast Time" + coastTime);
		resumeTimeMap.remove(activity.toString());
	}
	
	public static void onPauseStart(Activity activity) {
		pauseTimeMap.put(activity.toString(), System.currentTimeMillis());
	}

	public static void onPauseEnd(Activity activity) {
		Long startTime = pauseTimeMap.get(activity.toString());
		if (startTime == null) {
			return;
		}
		long coastTime = System.currentTimeMillis() - startTime;
		System.out.println(activity.toString() + " onPause coast Time" + coastTime);
		pauseTimeMap.remove(activity.toString());
	}
	
	public static void onStopStart(Activity activity) {
		stopTimeMap.put(activity.toString(), System.currentTimeMillis());
	}

	public static void onStopEnd(Activity activity) {
		Long startTime = stopTimeMap.get(activity.toString());
		if (startTime == null) {
			return;
		}
		long coastTime = System.currentTimeMillis() - startTime;
		System.out.println(activity.toString() + " onStop coast Time" + coastTime);
		stopTimeMap.remove(activity.toString());
	}
	public static void onDestroyStart(Activity activity) {
		destroyTimeMap.put(activity.toString(), System.currentTimeMillis());
	}

	public static void onDestroyEnd(Activity activity) {
		Long startTime = destroyTimeMap.get(activity.toString());
		if (startTime == null) {
			return;
		}
		long coastTime = System.currentTimeMillis() - startTime;
		System.out.println(activity.toString() + " onDestroy coast Time" + coastTime);
		destroyTimeMap.remove(activity.toString());
	}
}
