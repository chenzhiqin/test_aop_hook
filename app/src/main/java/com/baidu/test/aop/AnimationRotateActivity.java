package com.baidu.test.aop;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.baidu.test.aop.annotation.ActivityAnnotation;
import com.baidu.test.aop.annotation.FileAnnotation;


/**
 * @author Harlan Song
 * @weibo: weibo.com/markdev
 * 2012-8-27
 */
@FileAnnotation("AnimationRotateActivity")
public class AnimationRotateActivity extends FragmentActivity {

	@Override
	@ActivityAnnotation("onCreate")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.an_rotate);
		ImageView imgv=(ImageView) findViewById(R.id.img);
		Animation alphaAnimation=AnimationUtils.loadAnimation(this, R.anim.rotate);
		imgv.startAnimation(alphaAnimation);
	}
}
