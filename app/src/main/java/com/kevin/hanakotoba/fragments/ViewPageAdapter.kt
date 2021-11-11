package com.kevin.hanakotoba.fragments

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fa: Fragment, var fragmentList: MutableList<Fragment>)
    : FragmentStateAdapter(  fa ){
    override fun getItemCount(): Int = fragmentList.size
    override fun createFragment(position: Int): Fragment = fragmentList[position]
}