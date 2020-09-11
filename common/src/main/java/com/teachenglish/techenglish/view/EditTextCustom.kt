package com.teachenglish.techenglish.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.teachenglish.app.R

class EditTextCustom(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private var titleHeader: String? = null
    private var placeholder: String? = null
    private var tvTitleHeader: TextView? = null
    private var etInput: EditText? = null

    init {
        init(attrs)
        val typedArray =
            context!!.obtainStyledAttributes(attrs, R.styleable.EditTextCustom, 0, 0)
        try {

            titleHeader = typedArray.getString(R.styleable.EditTextCustom_titleHeader)!!
            placeholder = typedArray.getString(R.styleable.EditTextCustom_placeholder)!!
        } catch (e: Exception) {
            Log.e(javaClass.simpleName, "GEToolbar: " + e.message)
        } finally {
            typedArray.recycle()
        }

    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.view_edit_text_custom, this)

        tvTitleHeader = findViewById(R.id.tvTitleHeader)
        etInput = findViewById(R.id.etInput)
    }

    val text: String?
        get() {
            return etInput?.text.toString()
        }


}