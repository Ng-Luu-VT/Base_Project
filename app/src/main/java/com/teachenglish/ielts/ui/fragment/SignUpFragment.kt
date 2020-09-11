package com.teachenglish.ielts.ui.fragment

import android.view.View
import com.teachenglish.ielts.R
import com.teachenglish.ielts.base.BaseFragment
import com.teachenglish.ielts.databinding.FragmentSignUpBinding
import com.teachenglish.ielts.viewmodel.FirstViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment<FirstViewModel, FragmentSignUpBinding>(){
    companion object{
        fun newInstance() = SignUpFragment()
    }

    override val layoutResID: Int = R.layout.fragment_sign_up

    override val viewModel  by viewModel<FirstViewModel>()

    override fun init() {
    }

    override fun initView(view: View) {

    }

    override fun getData() {

    }

}