package com.teachenglish.techenglish.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teachenglish.app.R

class DividerRecyclerView : RecyclerView {
    public var dividerItemDecoration: DividerItemDecorator? = null
    private var iRecyclerViewLoadMoreListener: IRecyclerViewLoadMoreListener? = null
//    private val iRecyclerViewScrollListener: IRecyclerViewScrollListener? = null
    private var isLoadMore = true
    private var isLoading = false
    private var totalItemCount = 0

    private var totalDy = 0f

    fun getTotalDy(): Float {
        return totalDy

    }

    fun setTotalDy(totalDy: Float) {
        this.totalDy = totalDy
    }

    constructor(context: Context) : super(context){
        init()

    }



    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs){
        init()
    }

    fun initLoadMore(iRecyclerViewLoadMoreListener: IRecyclerViewLoadMoreListener) {
        this.iRecyclerViewLoadMoreListener = iRecyclerViewLoadMoreListener
        addOnScrollListener(object : OnScrollListener() {
            var mLayoutManager = layoutManager as LinearLayoutManager?
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalDy += dy.toFloat() //Math.abs(dy);
                totalItemCount = mLayoutManager!!.itemCount
                val lastVisibleItem = mLayoutManager!!.findLastVisibleItemPosition()
                val firstVisibleItem = mLayoutManager!!.findFirstVisibleItemPosition()
                if (firstVisibleItem == 0) totalDy = 0f
                if (!isLoading && totalItemCount <= lastVisibleItem + 4 && isLoadMore) {
                    isLoading = true
                    iRecyclerViewLoadMoreListener.onLoadMore()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    Log.d(javaClass.simpleName, "end")
                } else if (!recyclerView.canScrollVertically(-1)) {
                    Log.d(javaClass.simpleName, "top")
                } else {
                }
            }
        })
    }

    fun isLoading(): Boolean {
        return isLoading
    }

    fun setLoading(loading: Boolean) {
        isLoading = loading
    }

    fun isLoadMore(): Boolean {
        return isLoadMore
    }

    fun setLoadMore(loadMore: Boolean) {
//        if (adapter is BaseRecyclerViewAdapter) {
//            (adapter as BaseRecyclerViewAdapter?).setLoadMore(loadMore)
//            try {
//                adapter!!.notifyDataSetChanged()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//        isLoadMore = loadMore
    }

    private fun init() {
        dividerItemDecoration = DividerItemDecorator(
            ContextCompat.getDrawable(
                context,
                R.drawable.divider_reyclerview
            )
        )
        addItemDecoration(dividerItemDecoration!!)
    }

    fun getTotalItemCount(): Int {
        return totalItemCount
    }

    interface IRecyclerViewLoadMoreListener {
        fun onLoadMore()
    }

}