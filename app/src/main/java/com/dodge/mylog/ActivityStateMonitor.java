package com.dodge.mylog;

/**
 * Created by linzheng on 2019/8/15.
 */

public class ActivityStateMonitor {


    private boolean mUserVisible = false;


    protected void onResume() {
        mUserVisible = true;
    }


    protected void onPause() {
        mUserVisible = false;
    }
}
