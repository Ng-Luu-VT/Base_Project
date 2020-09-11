package com.teachenglish.ielts.ui.fragment

import android.content.Intent
import android.view.View
import com.teachenglish.ielts.R
import com.teachenglish.ielts.base.BaseFragment
import com.teachenglish.ielts.databinding.FragmentLoginBinding
import com.teachenglish.ielts.ui.activity.HomeActivity
import com.teachenglish.ielts.viewmodel.FirstViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment :BaseFragment<FirstViewModel,FragmentLoginBinding>(){
    override val layoutResID: Int
        get() = R.layout.fragment_login
    override val viewModel by viewModel<FirstViewModel>()


    override fun init() {
    }

    override fun initView(view: View) {
    }

    override fun bindEvent(view: View) {
        super.bindEvent(view)
        binding?.tvSignUp?.setOnClickListener {
            addFragment(SignUpFragment.newInstance())
        }

        binding?.tvLogin?.setOnClickListener {

            startActivity(Intent(context,HomeActivity::class.java))
            activity?.finish()
        }
    }

    override fun getData() {
    }

}