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
import kotlinx.android.synthetic.main.recyclerview.view.*

class GERecyclerVIewHori(context: Context, attrs: AttributeSet?) :
    FrameLayout(context, attrs) {
    lateinit var mAdapter: BaseAdapter<*, *>

    private var page = 0
    var maxPage: Int = 0
    private var isLoading: Boolean = false
    private val mThresholdLoadMore = 3

    private var onRefreshListener: SwipeRefreshLayout.OnRefreshListener? = null

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.recyclerview_hori, this)
    }


    fun setUpAdapter(baseAdapter: BaseAdapter<*, *>) {
        rvData.layoutManager = LinearLayoutManager(context)
        this.mAdapter = baseAdapter
        rvData.adapter = this.mAdapter
    }


    fun setUpAdapter(baseAdapter: BaseAdapter<*, *>, layoutManager: LinearLayoutManager) {
        rvData.layoutManager = layoutManager
        ViewCompat.setNestedScrollingEnabled(rvData, false)

        this.mAdapter = baseAdapter
        rvData.adapter = this.mAdapter

    }
    fun recyclerView(): RecyclerView{
        return rvData
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

    interface LoadMoreListener {
        fun onLoadMore()
    }

}