package com.teachenglish.techenglish.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.math.MathUtils
import androidx.recyclerview.widget.RecyclerView
import com.teachenglish.app.R
import java.util.ArrayList

@Suppress("UNCHECKED_CAST")
abstract class BaseSpecialAdapter<D, VH : BaseViewHolder<D>>(
    val _arrData: MutableList<D?> = ArrayList()
) :
    RecyclerView.Adapter<VH>() {

    val arrData: MutableList<D?>
        get() = _arrData
    lateinit var itemClick: (Int) -> Unit
    private val TYPE_ITEM_NORMAL: Int = 0
    private val TYPE_ITEM_LOAD_MORE: Int = 999

    override fun getItemViewType(position: Int): Int {
        return if (_arrData[position] == null) {
            TYPE_ITEM_LOAD_MORE
        } else
            TYPE_ITEM_NORMAL
    }

    override fun getItemCount(): Int {
        return _arrData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return if (viewType == TYPE_ITEM_NORMAL) {
            val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)

            getViewHolder(viewType, view, parent)
        } else
            LoadMoreViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.rcv_item_loadmore,
                    parent,
                    false
                )
            ) as VH
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (holder is LoadMoreViewHolder) {

        } else {
            holder.data = _arrData.getOrNull(position)
            if (position == arrData.size - 1) {
                itemViewLast(holder)
            } else {
                itemViewOther(holder)

            }
        }
    }


    abstract fun itemViewOther(holder: VH)

    abstract fun itemViewLast(holder: VH)


    fun addItem(item: D) = addIemAt(_arrData.count(), item)

    fun addIemAt(position: Int, item: D) =
        _arrData.add(position, item).also { notifyItemInserted(position) }

    fun addItems(list: MutableList<D?>) = addItemsAt(_arrData.count(), list)

    fun addItemsAt(position: Int, list: MutableList<D?>) =
        _arrData.addAll(position, list).also { notifyItemRangeInserted(position, list.size) }

    fun removeItem(item: D): Boolean {
        val position = _arrData.indexOf(item)
        if (position >= 0) {
            _arrData.remove(item)
            notifyItemRemoved(position)
        }
        return position >= 0
    }

    fun removeItemAt(position: Int) =
        _arrData.removeAt(position).also { notifyItemRemoved(position) }

    fun removeItems(positionStart: Int, positionEnd: Int) {
        val start = MathUtils.clamp(0, positionStart, _arrData.size - 1)
        val end = MathUtils.clamp(start, positionEnd, _arrData.size)
        _arrData.removeAll(_arrData.subList(start, end))
        notifyItemRangeRemoved(start, end - start)
    }

    fun setItemAt(position: Int, item: D) =
        _arrData.set(position, item).also { notifyItemChanged(position) }

    fun clear() {
        _arrData.clear()
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int) = arrData[position]

    abstract fun getViewHolder(viewType: Int, view: View, parent: ViewGroup): VH
    protected abstract val layoutRes: Int
}