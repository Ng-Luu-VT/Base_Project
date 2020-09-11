package com.teachenglish.ielts.viewpageradapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    var listFragment: MutableList<Fragment> = mutableListOf()
) :
    FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int = listFragment.size


    override fun createFragment(position: Int): Fragment = listFragment[position]

}