package com.teachenglish.ielts.ui.activity

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.teachenglish.ielts.base.BaseActivity
import com.teachenglish.ielts.databinding.ActivityMainBinding
import com.teachenglish.ielts.model.local.UserModel
import com.teachenglish.ielts.viewpageradapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.teachenglish.ielts.R


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutRes: Int = R.layout.activity_main


    override fun init() {
//        addFragment(
//            FirstFragment.newInstance("ahuahuh"),
//            isAddToBackStack = true,
//            isAnim = false
//        )
    }

    override fun initView() {
    }


    override fun bindEvent() {

    }

    override fun getData() {

    }

}