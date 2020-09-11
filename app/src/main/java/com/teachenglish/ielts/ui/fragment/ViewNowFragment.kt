package com.teachenglish.ielts.ui.fragment

import android.view.View
import com.teachenglish.ielts.R
import com.teachenglish.ielts.base.BaseFragment
import com.teachenglish.ielts.databinding.FragmentViewNowBinding
import com.teachenglish.ielts.viewmodel.FirstViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ViewNowFragment : BaseFragment<FirstViewModel,FragmentViewNowBinding>(){
    companion object{
        fun newInstance() = ViewNowFragment().apply {

        }
    }


    override val layoutResID: Int = R.layout.fragment_view_now
    override val viewModel by viewModel<FirstViewModel>()

    override fun init() {
    }

    override fun initView(view: View) {
    }

    override fun getData() {
    }

}