package com.teachenglish.ielts.ui.fragment

import android.util.Log
import android.view.View
import com.teachenglish.ielts.R
import com.teachenglish.ielts.adapater.CategoryAdapter
import com.teachenglish.ielts.base.BaseFragment
import com.teachenglish.ielts.databinding.FragmentCategoryBinding
import com.teachenglish.ielts.viewmodel.CategoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel



class CategoryFragment : BaseFragment<CategoryViewModel, FragmentCategoryBinding>() {

    companion object{
        fun newInstance() = CategoryFragment()
    }

    override val layoutResID: Int = R.layout.fragment_category
    override val viewModel by viewModel<CategoryViewModel>()

    override fun init() {
    }

    override fun initView(view: View) {
        val data = mutableListOf<String>()
        for (i in 0..10) {
            data.add("Document " + i)
            Log.d("initView", "initView: $i")
        }

        val adapter = CategoryAdapter(data)
        adapter.itemClick = { position ->
            addFragment(DocumentFragment.newInstance())
        }
        binding?.rvItems?.apply { setUpAdapter(adapter) }

    }

    override fun getData() {
    }

}