package com.example.infocovid.helper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.infocovid.MainActivity

class PagerAdapter (fm: FragmentManager, numberOfTabs: Int): FragmentPagerAdapter(fm, numberOfTabs) {
    var tabCount: Int = numberOfTabs
    val fragments = MainActivity.fragments!!

    override fun getCount(): Int {
        return tabCount
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}