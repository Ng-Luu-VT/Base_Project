package com.teachenglish.techenglish.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.teachenglish.app.R

class GENoDataView(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.nodataview, this)
    }

}