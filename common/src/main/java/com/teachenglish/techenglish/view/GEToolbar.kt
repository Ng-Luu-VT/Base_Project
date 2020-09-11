package com.teachenglish.techenglish.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.teachenglish.app.R
import kotlinx.android.synthetic.main.base_toolbar_item.view.*

class GEToolbar(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    private var isShowMoreIcon = false
    private var title = ""
    private var onToolbarActionClickListener: OnToolbarActionClickListener? = null

    init {
        init()
        val typedArray =
            context!!.obtainStyledAttributes(attrs, R.styleable.GEToolbar, 0, 0)
        try {

            isShowMoreIcon =
                typedArray.getBoolean(R.styleable.GEToolbar_showMoreItem, isShowMoreIcon)
            title = typedArray.getString(R.styleable.GEToolbar_title)!!
        } catch (e: Exception) {
            Log.e(javaClass.simpleName, "GEToolbar: " + e.message)
        } finally {
            typedArray.recycle()
        }
    }


    private fun init() {
        tag = "Toolbar"

        View.inflate(context, R.layout.base_toolbar_item, this)
        frmBack.setOnClickListener { v ->
            if (onToolbarActionClickListener != null) onToolbarActionClickListener?.onClickBack(v)
        }

        ivMenu.setOnClickListener { v ->
            if (onToolbarActionClickListener != null) onToolbarActionClickListener?.onClickMoreItem(
                v
            )
        }
        setTitle(title)
    }


    fun setTitle(title: String?) {
        tvTitle.text = title
    }

    fun setOnToolbarActionClickListener(onToolbarActionClickListener: OnToolbarActionClickListener?) {
        this.onToolbarActionClickListener = onToolbarActionClickListener
    }


    private fun pxFromDp(dp: Float): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }


    interface OnToolbarActionClickListener {
        fun onClickBack(view: View?)
        fun onClickBaseActionItem(view: View?)
        fun onClickMoreItem(view: View?)
    }
}