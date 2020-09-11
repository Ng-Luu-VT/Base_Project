package com.teachenglish.techenglish.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.math.MathUtils
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.teachenglish.app.R

@Suppress("UNCHECKED_CAST")
abstract class BaseAdapter<D, DB : ViewDataBinding>(val data: MutableList<D> = mutableListOf()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemClick: ((Int) -> Unit)? = null
    private val TYPE_ITEM_NORMAL: Int = 0
    val TYPE_ITEM_LOAD_MORE: Int = 999

    override fun getItemViewType(position: Int): Int {
        return if (data[position] == null) {
            TYPE_ITEM_LOAD_MORE
        } else
            TYPE_ITEM_NORMAL
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ITEM_NORMAL) {
            BaseRecyclerViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    layoutRes,
                    parent,
                    false
                )
            )
        } else
            LoadMoreViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.rcv_item_loadmore,
                    parent,
                    false
                )
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BaseAdapter<*, *>.BaseRecyclerViewHolder) {
            val item: D = data.getOrNull(position)!!
            val binding: DB = holder.binding as DB
            binData(item, binding, position)
        }
    }


    inner class BaseRecyclerViewHolder(var binding: DB) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClick?.invoke(layoutPosition)
            }
        }
    }


    fun addItem(item: D?) = addIemAt(data.count(), item)

    fun addIemAt(position: Int, item: D?) =
        data.add(position, item!!).also { notifyItemInserted(position) }

    fun addItems(list: MutableList<D>) = addItemsAt(data.count(), list)

    fun addItemsAt(position: Int, list: MutableList<D>) =
        data.addAll(position, list).also { notifyItemRangeInserted(position, list.size) }

    fun removeItem(item: D): Boolean {
        val position = data.indexOf(item)
        if (position >= 0) {
            data.remove(item)
            notifyItemRemoved(position)
        }
        return position >= 0
    }


    fun removeItemAt(position: Int) =
        data.removeAt(position).also { notifyItemRemoved(position) }

    fun removeItems(positionStart: Int, positionEnd: Int) {
        val start = MathUtils.clamp(0, positionStart, data.size - 1)
        val end = MathUtils.clamp(start, positionEnd, data.size)
        data.removeAll(data.subList(start, end))
        notifyItemRangeRemoved(start, end - start)
    }

    fun setItemAt(position: Int, item: D) =
        data.set(position, item).also { notifyItemChanged(position) }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int) = data[position]
    fun isSize() = data.size == 0
    abstract fun binData(item: D, binding: DB, position: Int)
    protected abstract val layoutRes: Int
}