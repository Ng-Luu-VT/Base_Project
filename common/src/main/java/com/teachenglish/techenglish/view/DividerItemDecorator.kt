package com.teachenglish.techenglish.view

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class DividerItemDecorator : ItemDecoration {
    private var mDivider: Drawable? = null

    constructor(divider: Drawable?): super() {
        mDivider = divider
    }

    override fun onDrawOver(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (mDivider == null) return
        val dividerLeft = parent.paddingLeft
        val dividerRight = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0..childCount - 2) {
            val child = parent.getChildAt(i)
            val params =
                child.layoutParams as RecyclerView.LayoutParams
            val dividerTop = child.bottom + params.bottomMargin
            val dividerBottom: Int = dividerTop + mDivider!!.intrinsicHeight
            mDivider?.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            mDivider?.draw(c)
        }
    }
}