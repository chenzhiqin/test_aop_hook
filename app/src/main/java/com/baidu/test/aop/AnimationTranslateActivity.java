package com.baidu.test.aop;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.baidu.test.aop.annotation.ActivityAnnotation;


/**
 * @author Harlan Song
 * @weibo: weibo.com/markdev
 * 2012-8-27
 */
public class AnimationTranslateActivity extends FragmentActivity {
	ImageView imgv0,imgv1,imgv2,imgv3,imgv4,imgv5;
	Animation alphaAnimation0,alphaAnimation1,alphaAnimation2,alphaAnimation3,alphaAnimation4,alphaAnimation5;

	@Override
	@ActivityAnnotation("onCreate")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.an_translate);
		imgv0=(ImageView) findViewById(R.id.img0);
		alphaAnimation0=AnimationUtils.loadAnimation(this, R.anim.translate0);
		imgv0.startAnimation(alphaAnimation0);
		
		imgv1=(ImageView) findViewById(R.id.img1);
		alphaAnimation1=AnimationUtils.loadAnimation(this, R.anim.translate1);
		imgv1.startAnimation(alphaAnimation1);
		
		imgv2=(ImageView) findViewById(R.id.img2);
		alphaAnimation2=AnimationUtils.loadAnimation(this, R.anim.translate2);
		imgv2.startAnimation(alphaAnimation2);
		
		imgv3=(ImageView) findViewById(R.id.img3);
		alphaAnimation3=AnimationUtils.loadAnimation(this, R.anim.translate3);
		imgv3.startAnimation(alphaAnimation3);
		
		imgv4=(ImageView) findViewById(R.id.img4);
		alphaAnimation4=AnimationUtils.loadAnimation(this, R.anim.translate4);
		imgv4.startAnimation(alphaAnimation4);
		
		imgv5=(ImageView) findViewById(R.id.img5);
		alphaAnimation5=AnimationUtils.loadAnimation(this, R.anim.translate5);
		imgv5.startAnimation(alphaAnimation5);
	}
}
