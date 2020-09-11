package com.teachenglish.techenglish.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.teachenglish.app.R
import com.teachenglish.techenglish.base.BaseAdapter
import com.teachenglish.techenglish.base.BaseSpecialAdapter
import kotlinx.android.synthetic.main.recyclerview_vertical.view.*

class GERecyclerVIewVertical(context: Context?, attrs: AttributeSet?) :
    FrameLayout(context!!, attrs) {
    lateinit var mAdapter: BaseAdapter< *,*>
    lateinit var mSpecialAdapter: BaseSpecialAdapter<*, *>

    private var page = 0
    var maxPage: Int = 0
    private var isLoading: Boolean = false
    private val mThresholdLoadMore = 3

    private var onRefreshListener: SwipeRefreshLayout.OnRefreshListener? = null

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.recyclerview_vertical, this)
    }


    fun setUpAdapter(baseAdapter: BaseAdapter< *,*>) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        this.mAdapter = baseAdapter
        recyclerView.adapter = this.mAdapter
    }
  fun setUpAdapter(baseAdapter: BaseSpecialAdapter<*, *>) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        this.mSpecialAdapter = baseAdapter
        recyclerView.adapter = this.mSpecialAdapter
    }


    fun setUpAdapter(baseAdapter: BaseAdapter< *,*>, layoutManager: LinearLayoutManager) {
        recyclerView.layoutManager = layoutManager
        ViewCompat.setNestedScrollingEnabled(recyclerView, false)

        this.mAdapter = baseAdapter
        recyclerView.adapter = this.mAdapter

    }


    fun handleDataResponse(maxPage: Int) {
        if (isLoading && mAdapter.data.count() > 0 && mAdapter.data[mAdapter.data.count() - 1] == null) {
            mAdapter.data.removeAt(mAdapter.data.count() - 1)
            isLoading = false
            mAdapter.notifyItemRemoved(mAdapter.data.count())
        }
        this.maxPage = maxPage
    }


    fun setRefreshListener(listener: SwipeRefreshLayout.OnRefreshListener) {
        this.onRefreshListener = listener
    }

    fun recyclerView(): RecyclerView{
        return recyclerView
    }
    interface LoadMoreListener {
        fun onLoadMore()
    }

}