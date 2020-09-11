package com.teachenglish.techenglish.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.teachenglish.app.R
import java.util.*

class SlideDotView : LinearLayout {

    private val imageViews = ArrayList<ImageView>()

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    @SuppressLint("WrongConstant")
    internal fun init() {
        orientation = LinearLayoutCompat.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
    }

    fun setUpChildView(numberItem: Int) {
        removeAllViews()
        imageViews.clear()
        gravity = Gravity.CENTER
        for (i in 0 until numberItem) {
            val imageView = AppCompatImageView(context)
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.default_indicator
                )
            )
            val imglayoutParams = ViewGroup.MarginLayoutParams(
                resources.getDimensionPixelSize(R.dimen._16dp),
                ViewGroup.MarginLayoutParams.WRAP_CONTENT
            )
            imageView.layoutParams = imglayoutParams
            imageViews.add(imageView)
            addView(imageView, imglayoutParams)
        }
    }

    fun activePosition(position: Int) {
        if (position == RecyclerView.NO_POSITION || position >= imageViews.size) return
        for (i in imageViews.indices) {
            imageViews[i].setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.default_indicator
                )
            )
        }
        imageViews[position].setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.dot_blue
            )
        )
    }

    private fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = view.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(left, top, right, bottom)
            view.requestLayout()
        }
    }
}
