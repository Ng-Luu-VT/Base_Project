package com.teachenglish.ielts.ui.activity

import com.teachenglish.ielts.R
import com.teachenglish.ielts.base.BaseActivity
import com.teachenglish.ielts.databinding.ActivityHomeBinding
import com.teachenglish.ielts.ui.fragment.CategoryFragment

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override val layoutRes: Int = R.layout.activity_home


    override fun init() {

    }

    override fun initView() {
        binding?.tvCategory1?.setOnClickListener{
            addFragment(CategoryFragment.newInstance())
        }
    }

    override fun bindEvent() {

    }

    override fun getData() {

    }

}