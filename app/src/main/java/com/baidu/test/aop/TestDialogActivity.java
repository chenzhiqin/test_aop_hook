/*
 * Copyright (C) 2014 Baidu Inc. All rights reserved.
 */
package com.baidu.test.aop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import com.baidu.test.aop.annotation.ActivityAnnotation;
import com.baidu.test.aop.annotation.FileAnnotation;


/**
 * 
 * @author chenyangkun
 * @since 2014-5-16
 */
@FileAnnotation("TestDialogActivity")
public class TestDialogActivity extends Activity {

    @Override
    @ActivityAnnotation("onCreate")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // View view = new View(this);
        // view.setBackgroundColor(0xfeffffff);
        // setContentView(view);
        
                new AlertDialog.Builder(TestDialogActivity.this).setTitle("你好").setMessage("欢迎你！")
                        .setPositiveButton("确定", new OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        }).setNegativeButton("取消", new OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        }).create().show();

    }

}
