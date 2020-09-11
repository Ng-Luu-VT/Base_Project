package com.teachenglish.ielts.adapater

import com.teachenglish.ielts.R
import com.teachenglish.techenglish.base.BaseAdapter
import com.teachenglish.ielts.databinding.ItemDocumentBinding

class DocumentAdapter(arrData : MutableList<String>) :
    BaseAdapter<String, ItemDocumentBinding>(data = arrData){
    override fun binData(item: String, binding: ItemDocumentBinding, position: Int) {
        binding.tvDocumentTest.text = item
    }

    override val layoutRes: Int = R.layout.item_document

}