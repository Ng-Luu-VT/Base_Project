package com.teachenglish.techenglish.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.teachenglish.app.R
import com.teachenglish.techenglish.utils.EnumStatus
import kotlinx.android.synthetic.main.fragment_error.*


class GEMessageDialogFragment : DialogFragment() {

    companion object {
        @JvmStatic
        fun newInstance(
            messageResId: String,
            status: EnumStatus = EnumStatus.ERROR,
            clickCallback: (() -> Unit)? = null
        ): GEMessageDialogFragment {
            return GEMessageDialogFragment().apply {
                this.message = messageResId
                this.clickCallback = clickCallback
                this.status=status
            }
        }
    }

    var message: String = ""
    var status: EnumStatus = EnumStatus.ERROR
    var clickCallback: (() -> Unit)? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        tvMessage.text = message
        tvClose.setOnClickListener {
            dismiss()
            clickCallback?.invoke()
        }

        if (status == EnumStatus.SUCCESS) {
            ivStatus.setImageResource(R.drawable.ic_check)
            ivStatus.setBackgroundResource(R.drawable.background_corner_green)
        }
    }

    override fun onResume() {
        super.onResume()
        val params = dialog!!.window!!.attributes
        params.width = ConstraintLayout.LayoutParams.MATCH_PARENT
        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        val back = ColorDrawable(Color.TRANSPARENT)
        val dimen = resources.getDimensionPixelOffset(R.dimen._24dp)
        val inset = InsetDrawable(back, dimen)

        dialog!!.window!!.setBackgroundDrawable(inset)
        dialog!!.window!!.attributes = params as android.view.WindowManager.LayoutParams
    }
}