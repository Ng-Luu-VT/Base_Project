package com.teachenglish.ielts.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teachenglish.ielts.data.error.ErrorModel
import io.reactivex.disposables.CompositeDisposable
import com.teachenglish.ielts.model.local.AsyncState

abstract class BaseViewModel : ViewModel() {

    val loadingState: MutableLiveData<AsyncState>
            by lazy { MutableLiveData<AsyncState>() }
    var compositeDisposable = CompositeDisposable()


    fun showLoading() {
        loadingState.value = AsyncState.Started
    }

    fun hideLoading(){
        loadingState.value= AsyncState.Completed
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

    fun getMessageErrorWithLocale(errorModel: ErrorModel?): String? {
//        val sharedReferenceHelper = SharedReferenceHelper(GEApp.context)
////        return if (sharedReferenceHelper.getLocale() == VI) {
////            errorModel.message_vi
////        } else {
////            errorModel.message_en
////        }
        return errorModel?.message


    }
}