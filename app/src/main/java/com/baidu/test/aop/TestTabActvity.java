package com.baidu.test.aop;


import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.baidu.test.aop.annotation.ActivityAnnotation;
import com.baidu.test.aop.annotation.FileAnnotation;

@FileAnnotation("TestTabActvity")
public class TestTabActvity extends TabActivity {
    /** tab的indicator文本 */
    private TabHost mTabHost;

    @Override
    @ActivityAnnotation("onCreate")
    protected void onCreate(Bundle savedInstanceState) {
//        long t0 = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_tab_activity);
        mTabHost = (TabHost) getTabHost();
        Intent intent1 = new Intent(this, AnimationExampleActivity.class);
        TabSpec tabSpec = mTabHost.newTabSpec(AnimationExampleActivity.class.toString()).setIndicator("tab1")
                .setContent(intent1);
        mTabHost.addTab(tabSpec);
        Intent intent2 = new Intent(this, AnimationTranslateActivity.class);
        TabSpec tabSpec2 = mTabHost.newTabSpec(AnimationTranslateActivity.class.toString()).setIndicator("tab2")
                .setContent(intent2);
        mTabHost.addTab(tabSpec2);
//        long t2 = System.currentTimeMillis();
//        long t = t2-t0;
//        Log.i("ActivityTime",this.toString()+", oncreate " + t);
    }
    
    void dispatchActivityResult(String who, int requestCode, int resultCode,
            Intent data) {
        if (who != null) {
            Activity act = getLocalActivityManager().getActivity(who);
            
            Log.v("czq", "TestTabActvity,Dispatching result: who=" + who + ", reqCode=" + requestCode + ", resCode=" + resultCode
                    + ", data=" + data);
            
//            if (act != null) {
//                act.onActivityResult(requestCode, resultCode, data);
//                return;
//            }
        }
//        super.dispatchActivityResult(who, requestCode, resultCode, data);
    }
}
