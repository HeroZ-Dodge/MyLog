package com.dodge.mylog

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_test_layout.*

/**
 *  Created by linzheng on 2019/8/15.
 */

class MyFragment : Fragment(), IFragmentMonitor {

    private val monitor: FragmentStateMonitor = FragmentStateMonitor(this)
    private val fragmentName by lazy { arguments?.getString("args_name", "") }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tv_index.text = fragmentName
        btn_switch.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            val subFragment = buildSubFragment(fragmentName + "SubTag")
            transaction.replace(R.id.fl_container, subFragment).commitAllowingStateLoss()
        }
    }

    private fun buildSubFragment(tag: String): Fragment {
        val fragment = MySubFragment()
        val args = Bundle()
        args.putString("args_tag", tag)
        fragment.arguments = args
        return fragment
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