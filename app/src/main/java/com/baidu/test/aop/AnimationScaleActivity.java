package com.baidu.test.aop;

import android.content.Intent;
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
@FileAnnotation("AnimationScaleActivity")
public class AnimationScaleActivity extends FragmentActivity {

	@Override
	@ActivityAnnotation("onCreate")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.an_scale);
		ImageView imgv=(ImageView) findViewById(R.id.img);
		Animation alphaAnimation=AnimationUtils.loadAnimation(this, R.anim.scale);
		imgv.startAnimation(alphaAnimation);
	    Intent intent = new Intent();
        intent.putExtra("rs", "success");
        setResult(2, intent);
	}
}
