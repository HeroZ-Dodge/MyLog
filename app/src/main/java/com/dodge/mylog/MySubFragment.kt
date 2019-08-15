package com.dodge.mylog

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 *  Created by linzheng on 2019/8/15.
 */

class MySubFragment : Fragment(), IFragmentMonitor {

    private val monitor: FragmentStateMonitor = FragmentStateMonitor(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sub_test_layout, container, false)
    }

    override fun onResume() {
        super.onResume()
        monitor.checkFragmentVisible(true)
    }

    override fun onPause() {
        super.onPause()
        monitor.checkFragmentVisible(false)
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        monitor.checkFragmentVisible(!hidden)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        monitor.checkFragmentVisible(isVisibleToUser)
    }


    override fun getMonitor(): FragmentStateMonitor {
        return monitor
    }
}