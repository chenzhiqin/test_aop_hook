/*
 * Copyright (C) 2014 Baidu Inc. All rights reserved.
 */
package com.baidu.test.aop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.baidu.test.aop.annotation.ActivityAnnotation;
import com.baidu.test.aop.annotation.FileAnnotation;


/**
 * 
 * @author chenyangkun
 * @since 2014-4-23
 */
@FileAnnotation("LaunchModeTestActivity")
public class LaunchModeTestActivity extends Activity {

    @Override
    public void onNewIntent(Intent paramIntent) {
        super.onNewIntent(paramIntent);

        Toast.makeText(getApplicationContext(), "Test onNewIntent", Toast.LENGTH_SHORT).show();
    }

    @Override
    @ActivityAnnotation("onCreate")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test);
        findViewById(R.id.test2).setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
                startActivity(new Intent(LaunchModeTestActivity.this, LaunchModeTestActivity.class));
            }
        });
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(LaunchModeTestActivity.this, AnimationExampleActivity.class));
            }
        });
        android.util.Log.e("CYK", "window : " + getWindow());
        android.util.Log.e("CYK", "decor : " + getWindow().getDecorView());
        android.util.Log.e("CYK", "CustomTextView : " + CustomTextView.class.hashCode());

        android.util.Log.e("CYK", "findViewById : " + findViewById(R.id.custom).getClass().hashCode());
        CustomTextView view = (CustomTextView) findViewById(R.id.custom);
        view.setText("我替换了自定义控件");

    }

}
