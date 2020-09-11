package com.teachenglish.techenglish.extension

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import com.teachenglish.app.R
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.ThreeBounce
import java.text.DecimalFormat


fun ViewGroup.showLoading(isShow: Boolean) {
//    val rootView: ViewGroup =
//        getWindow().getDecorView().findViewById(R.id.content)
    if (findViewWithTag<View?>("Loading") == null) {
        val relativeLayout = RelativeLayout(context)
        relativeLayout.gravity = Gravity.CENTER
        relativeLayout.setOnClickListener { v: View? -> }
        relativeLayout.tag = "Loading"
        relativeLayout.setBackgroundColor(Color.parseColor("#50000000"))
        val progressBar = ProgressBar(context)
        val sprite: Sprite = ThreeBounce()
        sprite.color=ContextCompat.getColor(context, R.color.activeColor)
        progressBar.indeterminateDrawable = sprite
        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.addRule(Gravity.CENTER)
        relativeLayout.addView(progressBar, layoutParams)
        (this as ViewGroup).addView(
            relativeLayout,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }

    findViewWithTag<View>("Loading").visibility = if (isShow) View.VISIBLE else View.GONE
}

fun View?.showKeyboard () {
    if (this == null) {
        return
    }
    if (requestFocus()) {
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, 0)
    }
}

fun View?.hideKeyboard () {
    if (this == null) {
        return
    }
    val imm =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (!imm.isActive) {
        return
    }
    imm.hideSoftInputFromWindow(windowToken, 0)
    clearFocus()
}


@SuppressLint("SetTextI18n")
fun TextView.formatCurrency(long: Long){
    val formatter = DecimalFormat("###,###,###")
    this.text= "${formatter.format(long)} đ"
}

fun TextView.underLine(text:String){
    val content = SpannableString(text)
    content.setSpan(UnderlineSpan(), 0, content.length, 0)
    this.text = content
}
fun View.setupKeybord() {
    if (this !is EditText) {
        this.setOnTouchListener { v: View?, event: MotionEvent? ->
            this.hideKeyboard()
            false
        }
    }
    if (this is ViewGroup) {
        for (i in 0 until childCount) {
            val innerView = getChildAt(i)
            innerView.setupKeybord()
        }
    }
}
