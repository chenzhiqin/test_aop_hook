/*
 * Copyright (C) 2014 Baidu Inc. All rights reserved.
 */
package com.baidu.test.aop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 
 * @author chenyangkun
 * @since 2014-3-20
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int arg0) {
        Fragment fragment = new FragmentTest();
        Bundle bd = new Bundle();
        bd.putInt("position", arg0);
        fragment.setArguments(bd);
        android.util.Log.e("CYK", "getItem = " + arg0 + ", " + fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return 10;
    }

}
