package com.teachenglish.ielts.adapater


import com.teachenglish.ielts.R
import com.teachenglish.ielts.databinding.ItemCategoryBinding
import com.teachenglish.techenglish.base.BaseAdapter

class CategoryAdapter(arrData: MutableList<String>) :
    BaseAdapter<String, ItemCategoryBinding>(data = arrData)
{
    override fun binData(item: String, binding: ItemCategoryBinding, position: Int) {
        binding.tvTile.text = item
    }

    override val layoutRes: Int = R.layout.item_category

}