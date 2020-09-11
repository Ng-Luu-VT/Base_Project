package com.teachenglish.ielts.ui.fragment

import android.view.View
import com.teachenglish.ielts.R
import com.teachenglish.ielts.base.BaseFragment
import com.teachenglish.ielts.databinding.FragmentTestBinding
import com.teachenglish.ielts.viewmodel.FirstViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckNowFragment : BaseFragment<FirstViewModel, FragmentTestBinding>(){

    companion object{
        fun newInstance() = CheckNowFragment().apply {

        }
    }

    override val layoutResID: Int = R.layout.fragment_check_now
    override val viewModel by viewModel<FirstViewModel>()

    override fun init() {
    }

    override fun initView(view: View) {
    }

    override fun getData() {
    }

}