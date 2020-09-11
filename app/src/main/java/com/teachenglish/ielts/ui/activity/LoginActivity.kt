package com.teachenglish.ielts.ui.activity

import com.teachenglish.ielts.R
import com.teachenglish.ielts.databinding.ActivityBaseBinding
import com.teachenglish.ielts.ui.fragment.LoginFragment
import com.teachenglish.ielts.base.BaseActivity as BaseActivity

class LoginActivity : BaseActivity<ActivityBaseBinding>() {
    override val layoutRes: Int = R.layout.activity_base


    override fun init() {
        addFragment(LoginFragment(), isAddToBackStack = false, isAnim = false)
    }

    override fun initView() {

    }

    override fun bindEvent() {

    }

    override fun getData() {

    }


}