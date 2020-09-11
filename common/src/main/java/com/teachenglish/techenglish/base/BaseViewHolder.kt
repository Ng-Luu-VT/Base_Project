package com.teachenglish.techenglish.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder<D>(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer,
    View.OnAttachStateChangeListener {
    private var needDataChangedDispatch = false
    var data: D? = null
        set(value) {
            field = value
            if (itemView.isAttachedToWindow) onDataChanged()
            else needDataChangedDispatch = true
        }

    init {
        @Suppress("LeakingThis")
        itemView.addOnAttachStateChangeListener(this)
    }

    override fun onViewAttachedToWindow(v: View?) {
        if (needDataChangedDispatch) onDataChanged()
    }

    override fun onViewDetachedFromWindow(v: View?) {
    }

    abstract fun onDataChanged()
}