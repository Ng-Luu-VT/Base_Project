package com.teachenglish.techenglish.extension


import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.teachenglish.app.R
import com.teachenglish.techenglish.utils.EnumStatus
import com.teachenglish.techenglish.view.GEMessageDialogFragment
import java.util.*

fun Context.getGenericDialog(titleResID: Int, message: String): GEMessageDialogFragment {
    return GEMessageDialogFragment.newInstance(message)
}

class MessageDialog(
    private val title: Int = R.string.notification,
    private val message: String? = "",
    private val clickCallBack: (() -> Unit)? = null
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton("OK") { dialog, _ ->
                dialog.dismiss()
                clickCallBack?.invoke()
            }.create()
    }
}

class ConfirmDialog(
    private val title: Int = R.string.notification,
    private val message: String? = "",
    private val clickCallBack: ((Boolean) -> Unit)? = null
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                clickCallBack?.invoke(true)
            }.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                clickCallBack?.invoke(false)
            }.create()
    }
}

/**
 * Show dialog, kiểm tra dialog đã hiện thì không hiện nữa
 */
fun DialogFragment.showIfNonExistent(manager: FragmentManager, tag: String) {
    try {
        if (manager.findFragmentByTag(tag) == null)
            this.show(manager, tag)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun DialogFragment.showNowIfNonExistent(manager: FragmentManager, tag: String) {
    try {
        if (manager.findFragmentByTag(tag) == null)
            this.showNow(manager, tag)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


fun Context.getMessageDialog(
    message: String = "",
    enumStatus: EnumStatus = EnumStatus.ERROR,
    clickCallBack: (() -> Unit)? = null
): DialogFragment {
    return GEMessageDialogFragment.newInstance(message, enumStatus, clickCallBack)
}

fun Fragment.showMessageDialog(
    message: String = "",
    enumStatus: EnumStatus=EnumStatus.ERROR,
    clickCallBack: (() -> Unit)? = null
) {
    context
        ?.getMessageDialog(message, enumStatus, clickCallBack)
        ?.showNowIfNonExistent(childFragmentManager, getClassSimpleTag())
//    requireContext()
//        .getMessageDialog(title, message, clickCallBack)
//        .showIfNonExistent(childFragmentManager, getClassSimpleTag())
}

fun AppCompatActivity.showMessageDialog(
    title: Int = R.string.notification,
    message: String = "",
    enumStatus: EnumStatus = EnumStatus.ERROR,

    clickCallBack: (() -> Unit)? = null
) {
    getMessageDialog(message, enumStatus, clickCallBack)
        .showIfNonExistent(supportFragmentManager, getClassSimpleTag())
}

fun Fragment.showConfirmDialog(
    title: Int = R.string.notification,
    message: String? = "",
    clickCallBack: ((Boolean) -> Unit)? = null
) {
    requireContext()
        .getConfirmDialog(title, message, clickCallBack)
        .showIfNonExistent(childFragmentManager, getClassSimpleTag())
}

fun AppCompatActivity.showConfirmDialog(
    title: Int = R.string.notification,
    message: String? = "",
    clickCallBack: ((Boolean) -> Unit)? = null
) {
    getConfirmDialog(title, message, clickCallBack)
        .showIfNonExistent(this.supportFragmentManager, getClassSimpleTag())
}

fun Context.getConfirmDialog(
    title: Int = R.string.notification,
    message: String? = "",
    clickCallBack: ((Boolean) -> Unit)? = null
): DialogFragment {
    return ConfirmDialog(title, message, clickCallBack)
}

fun showDateDialogPicker(context: Context, textView: TextView) {
    val calendar = Calendar.getInstance()

    val mDatePickerDialog = DatePickerDialog(
        context, { view, year, monthOfYear, dayOfMonth ->
            val newDate = Calendar.getInstance()
            newDate.set(year, monthOfYear, dayOfMonth)
            textView.hint = ""
            textView.text = dateToStringFormat(newDate.time, "dd-MM-YYYY")
        }, calendar.get(
            Calendar.YEAR
        ), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )
    mDatePickerDialog.datePicker.maxDate = System.currentTimeMillis()
    mDatePickerDialog.show()
}