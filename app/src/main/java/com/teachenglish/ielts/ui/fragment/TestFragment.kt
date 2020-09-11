package com.teachenglish.ielts.ui.fragment

import android.view.View
import com.teachenglish.ielts.base.BaseFragment
import com.teachenglish.ielts.databinding.FragmentTestBinding
import com.teachenglish.ielts.viewmodel.FirstViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestFragment : BaseFragment<FirstViewModel, FragmentTestBinding>(){
    companion object{
        fun newInstance() = TestFragment().apply {

        }
    }

    override val layoutResID: Int = com.teachenglish.ielts.R.layout.fragment_test
    override val viewModel by viewModel<FirstViewModel>()

    override fun init() {
    }

    override fun initView(view: View) {
        binding?.tvCheckNow?.setOnClickListener{
            addFragment(CheckNowFragment.newInstance())
        }
        binding?.tvViewNow?.setOnClickListener{
            addFragment(ViewNowFragment.newInstance())
        }
    }

    override fun getData() {
    }
}