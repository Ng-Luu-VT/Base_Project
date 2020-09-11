package com.teachenglish.techenglish.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.teachenglish.app.R
import com.teachenglish.techenglish.base.BaseAdapter
import kotlinx.android.synthetic.main.recyclerview.view.*

class GERecyclerVIew(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
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
        View.inflate(context, R.layout.recyclerview, this)
        swlRefresh.apply {
            setOnRefreshListener {
                isRefreshing = false
                onRefreshListener?.onRefresh()
            }
        }

    }

    fun hideRefresh() {
        swlRefresh.isRefreshing = false
    }

    fun initLoadMore(listener: LoadMoreListener?) {
        if (listener == null) return
        rvData.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var mLayoutManager: LinearLayoutManager =
                ((rvData.layoutManager as LinearLayoutManager?)!!)
            var lastVisibleItems: Int = 0
            var totalItemCount: Int = 0

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                totalItemCount = mLayoutManager.itemCount
                lastVisibleItems = mLayoutManager.findLastVisibleItemPosition()
                if (totalItemCount <= lastVisibleItems + mThresholdLoadMore) {
                    if (!isLoading && page < maxPage) {
                        page++
                        mAdapter.addItem(null)
                        isLoading = true
                        listener.onLoadMore()
                    }
                }

            }
        })
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

    fun noData(boolean: Boolean) {
        vNodata.visibility = if (boolean) View.VISIBLE else View.GONE
        rvData.visibility = if (boolean) View.GONE else View.VISIBLE
    }

    fun noData() {
        vNodata.visibility = if (mAdapter.isSize()) View.VISIBLE else View.GONE
        rvData.visibility = if (mAdapter.isSize()) View.GONE else View.VISIBLE
    }


    fun handleDataResponse(maxPage: Int) {
        if (isLoading && mAdapter.data.count() > 0 && mAdapter.data[mAdapter.data.count() - 1] == null) {
            mAdapter.data.removeAt(mAdapter.data.count() - 1)
            isLoading = false
            mAdapter.notifyItemRemoved(mAdapter.data.count())
        }
        this.maxPage = maxPage
    }

    fun recyclerView(): RecyclerView {
        return rvData
    }

    fun disableAnimation() {
        (recyclerView().itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

    }

    fun setRefreshListener(listener: SwipeRefreshLayout.OnRefreshListener) {
        this.onRefreshListener = listener
    }
    fun setEnableRefresh(enable:Boolean){
        swlRefresh.isEnabled=enable
    }

    interface LoadMoreListener {
        fun onLoadMore()
    }

}