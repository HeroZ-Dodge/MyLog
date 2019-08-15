package com.dodge.mylog

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view_pager.adapter = MyFragmentAdapter(supportFragmentManager)
    }

    class MyFragmentAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            val fragment = MyFragment()
            val args = Bundle()
            args.putString("args_name", position.toString())
            fragment.arguments = args
            return fragment
        }

        override fun getCount(): Int {
            return 5
        }

    }



}
