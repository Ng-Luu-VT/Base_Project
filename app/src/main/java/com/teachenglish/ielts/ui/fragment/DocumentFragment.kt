package com.teachenglish.ielts.ui.fragment

import android.util.Log
import android.view.View
import com.teachenglish.ielts.R
import com.teachenglish.ielts.adapater.DocumentAdapter
import com.teachenglish.ielts.base.BaseFragment
import com.teachenglish.ielts.databinding.FragmentDocumentBinding
import com.teachenglish.ielts.viewmodel.DocumentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DocumentFragment : BaseFragment<DocumentViewModel, FragmentDocumentBinding>() {
    companion object {
        fun newInstance() = DocumentFragment().apply {

        }
    }


    override val layoutResID: Int = R.layout.fragment_document
    override val viewModel by viewModel<DocumentViewModel>()

    override fun init() {

    }

    override fun initView(view: View) {
        val data = mutableListOf<String>()
        for (i in 1..4){
            data.add("Test "+ i)
            Log.d("initView", "initView: $i")
        }

        val adapter = DocumentAdapter(data)
        adapter.itemClick = {position ->
            addFragment(TestFragment.newInstance())
        }
        binding?.rvDocument?.apply { setUpAdapter(adapter) }
    }

    override fun getData() {
    }

}