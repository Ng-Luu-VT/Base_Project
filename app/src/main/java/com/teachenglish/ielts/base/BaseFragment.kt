package com.teachenglish.ielts.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.appcompat.view.ContextThemeWrapper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.teachenglish.ielts.R
import com.teachenglish.ielts.extension.addTo
import com.teachenglish.ielts.model.local.AsyncState
import com.teachenglish.techenglish.extension.setupKeybord
import com.teachenglish.techenglish.extension.showLoading
import com.teachenglish.techenglish.extension.showMessageDialog
import com.teachenglish.techenglish.view.GEToolbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject


abstract class BaseFragment<VM : BaseViewModel, BD : ViewDataBinding> : Fragment(),
    GEToolbar.OnToolbarActionClickListener {
    protected abstract val layoutResID: Int
    protected abstract val viewModel: VM
     var binding: BD? = null

    private var isInit = false
    private var eventDisposable: CompositeDisposable = CompositeDisposable()
    val sharedRefHelper: SharedReferenceHelper by inject()

    // flag check pull to refresh
    var isRefresh = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // create ContextThemeWrapper from the original Activity Context with the custom theme
        val contextThemeWrapper: Context =
            ContextThemeWrapper(activity, getThemeID())

        // clone the inflater using the ContextThemeWrapper
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        binding = DataBindingUtil.inflate(localInflater, layoutResID, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackgroundView(view)
        init()
        initView(view)
        view.setupKeybord()
        bindEvent(view)
        getData()
    }

    private fun setBackgroundView(view: View) {
        view.setBackgroundColor(Color.WHITE)

        view.isClickable = true
        view.isFocusable = true
    }

    abstract fun init()
    abstract fun initView(view: View)

    @CallSuper
    open fun bindEvent(view: View) {
        GEApp.eventBus
            .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                onEventReceive(it)
            }.addTo(eventDisposable)

        viewModel.loadingState.observe(viewLifecycleOwner, Observer { loadingState ->
            asyncStateHandle(loadingState)
        })
        view.findViewById<GEToolbar?>(R.id.toolbar)?.setOnToolbarActionClickListener(this)

    }

    open fun getThemeID(): Int {
//        var theme = sharedReferenceHelper.getTheme()
//        return if (theme.equals(Theme.LIGHT.name, true)) {
//            R.style.LightTheme
//        } else {
//            R.style.MintTheme
//        }
        return R.style.AppTheme
    }

    abstract fun getData()

    open fun onEventReceive(it: Map<String, Any>) {}

    fun showLoading(isShow: Boolean) {
        val rootView = view as? ViewGroup
        rootView?.showLoading(isShow)
    }


    fun addFragment(fragment: Fragment, isAddToBackStack: Boolean = true) {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).addFragment(fragment, isAddToBackStack)
        }
    }

    fun replaceFragment(fragment: Fragment, isAddToBackStack: Boolean) {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).replaceFragment(fragment, isAddToBackStack)
        }
    }

    open fun asyncStateHandle(asyncState: AsyncState) {
        when (asyncState) {
            is AsyncState.Started -> asyncStateStarted()
            is AsyncState.Completed -> asyncStateCompleted()
            is AsyncState.Failed -> {
                //handle error here
                asyncStateError(asyncState)
            }
        }
    }

    fun clearAllBackStack() {
        val fm = activity?.supportFragmentManager
        for (i in 0 until fm!!.backStackEntryCount) {
            fm.popBackStack()
        }
    }


    /**
     * Starting call api
     */
    open fun asyncStateStarted() {
        showLoading(true)
    }

    /**
     * Api call successfully
     */
    open fun asyncStateCompleted() {
        showLoading(false)
    }

    /**
     * Exception handle here
     */
    open fun asyncStateError(asyncState: AsyncState.Failed) {
        showLoading(false)
        handleViewError()
        asyncState.error.message?.let {
            showMessageDialog(message = it)
        }
    }

    open fun handleViewError() {

    }

    override fun onClickBack(view: View?) {
        (activity as? BaseActivity<*>)?.onBackPressed()
    }

    override fun onClickBaseActionItem(view: View?) {
    }

    override fun onClickMoreItem(view: View?) {
    }

    override fun onDestroy() {
        binding?.unbind()
        binding = null
        super.onDestroy()
        eventDisposable.clear()
        eventDisposable.dispose()
    }

}

