package com.baidu.test.aop;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/*
 * Copyright (C) 2014 Baidu Inc. All rights reserved.
 */

/**
 * 
 * @author chenyangkun
 * @since 2014年6月5日
 */
public class CustomTextView extends TextView {

    /**
     * @param context
     */
    public CustomTextView(Context context) {
        super(context);
        setText("View 插件来自：" + context.getResources().getString(R.string.app_name));
    }

    /**
     * @param context
     * @param attrs
     */
    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

}
