package com.teachenglish.ielts.base

import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.teachenglish.ielts.R
import com.teachenglish.techenglish.extension.setupKeybord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

abstract class BaseActivity<BD : ViewDataBinding> : AppCompatActivity() {

    protected abstract val layoutRes: Int
    var binding: BD? = null

    val sharedReferenceHelper: SharedReferenceHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutRes)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )
        init()
        window.decorView.setupKeybord()
        initView()
        bindEvent()
        getData()
    }

    private fun View.setPaddingBottom() {
        updateLayoutParams<ViewGroup.MarginLayoutParams> {

            val resources: Resources = context.resources

            var resourceId = 0
            var marginBottom = 0
            if (resourceId > 0) {
                resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
                marginBottom = resources.getDimensionPixelSize(resourceId)
            }
            setMargins(marginStart, marginTop, marginEnd, marginBottom)
        }
    }


    /**
     * Khởi tạo các biến cần thiết cho activity
     */
    abstract fun init()

    /**
     * Khởi tạo view
     */
    abstract fun initView()

    /**
     * Set event listener
     */
    abstract fun bindEvent()

    /**
     * Lấy dữ liệu
     */
    abstract fun getData()


    fun addFragment(fragment: Fragment, isAddToBackStack: Boolean = true) {
        addReplaceFragment(fragment, false, isAddToBackStack, true)
    }

    fun addFragment(fragment: Fragment, isAddToBackStack: Boolean, isAnim: Boolean) {
        addReplaceFragment(fragment, false, isAddToBackStack, isAnim)
    }


    fun replaceFragment(fragment: Fragment, isAddToBackStack: Boolean) {
        addReplaceFragment(fragment, true, isAddToBackStack, true)
    }

    private fun addReplaceFragment(
        fragment: Fragment?,
        isReplace: Boolean,
        isAddToBackStack: Boolean,
        isAnim: Boolean
    ) {
        val fragmentManager = supportFragmentManager
        if (fragment != null) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            if (isAnim) fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            if (isReplace) fragmentTransaction.replace(R.id.frmContainer, fragment) else {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.frmContainer)
                if (currentFragment != null) {
                    fragmentTransaction.hide(currentFragment)
                }
                fragmentTransaction.add(R.id.frmContainer, fragment, fragment.javaClass.simpleName)
            }
            if (isAddToBackStack) {
                fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
            }
            fragmentTransaction.commit()
        }
    }


}