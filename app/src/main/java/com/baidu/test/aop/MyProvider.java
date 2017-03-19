/*
 * Copyright (C) 2015 Baidu Inc. All rights reserved.
 */
package com.baidu.test.aop;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

/**
 * 
 * @author caohaitao
 * @since 2015年10月19日
 */
public class MyProvider extends ContentProvider {
    
    public static String AUTH = "com.harlan.animation.MyProvider";

    @Override
    public boolean onCreate() {
        Context context = getContext();
        Log.i("czq", "MyProvider context:"+context);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = new MatrixCursor(projection);
        Object[] result = new Object[projection.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = projection[i] + " " + i;
        }

        cursor.addRow(result);
        
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}
