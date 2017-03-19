/*
 * Copyright (C) 2014 Baidu Inc. All rights reserved.
 */
package com.baidu.test.aop;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 
 * @author chenyangkun
 * @since 2014-3-20
 */
public class FragmentTest extends Fragment {

    @Override
    public void onAttach(Activity activity) {
        android.util.Log.e("CYK", "onAttach = " + this);
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        android.util.Log.e("CYK", "onCreate = " + this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int position = bundle.getInt("position");
        android.util.Log.e("CYK", "onCreateView = " + this);
        TextView text = new TextView(getActivity());
        text.setText("This is view : " + position);
        return text;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        android.util.Log.e("CYK", "onActivityCreated = " + this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        android.util.Log.e("CYK", "onStart = " + this);
        super.onStart();
    }

    @Override
    public void onResume() {
        android.util.Log.e("CYK", "onResume = " + this);
        super.onResume();
    }

    @Override
    public void onPause() {
        android.util.Log.e("CYK", "onPause = " + this);
        super.onPause();
    }

    @Override
    public void onStop() {
        android.util.Log.e("CYK", "onStop = " + this);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        android.util.Log.e("CYK", "onDestroyView = " + this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        android.util.Log.e("CYK", "onDestroy = " + this);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        android.util.Log.e("CYK", "onDetach = " + this);
        super.onDetach();
    }

}
