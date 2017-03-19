package com.baidu.test.aop;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.baidu.test.aop.annotation.ActivityAnnotation;
import com.baidu.test.aop.annotation.FileAnnotation;


/**
 *  
 * @author Harlan Song
 * @weibo: weibo.com/markdev
 * 2012-8-27
 */
@FileAnnotation("AnimationAlphaActivity")
public class AnimationAlphaActivity extends FragmentActivity {
    
    private static final String TAG = AnimationAlphaActivity.class.getSimpleName();
    
	@Override
	@ActivityAnnotation("onCreate")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.an_alpha);
		ImageView imgv=(ImageView) findViewById(R.id.img);
		Animation alphaAnimation=AnimationUtils.loadAnimation(this, R.anim.alpha);
		imgv.startAnimation(alphaAnimation);
        Object obj = getIntent().getSerializableExtra("serialtest");
        if (obj != null) {
            System.out.println("--- get Class loader:" + obj.getClass().getClassLoader());
        }
        
        Log.i(TAG, "AnimationAlphaActivity onCreate task id " + getTaskId());
       
	}
}
