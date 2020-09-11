package com.teachenglish.ielts.extension

import com.teachenglish.ielts.base.BaseResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

fun <T>Observable<T>.outOnMainThread (): Observable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T>Observable<Response<BaseResponse<T>>>.responseOutOnMain (): Observable<BaseResponse<T>?> {
    return map{ it.body() }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

//fun <T>Observable<BaseResponse<T>?>.reCallHandleToken(): Observable<BaseResponse<T>?> {
//    return handleToken()
//}

//inline fun <T>Observable<BaseResponse<T>?>.handleToken(): Observable<BaseResponse<T>?> {
//    return map {
//        if (it.code == ResponseCode.TOKEN_EXPIRES.value)
//            throw TokenException("")
//        it
//    }
//        .retryWhen { error ->
//            error.flatMap {
//                if (it is TokenException){
//                    this.reCallHandleToken()
//                } else
//                    Observable.error(it)
//            }
//        }
////        .responseOutOnMain()
//}

fun Disposable.addTo(compositeDisposable: CompositeDisposable?) {
    if(compositeDisposable == null) return
    compositeDisposable.add(this)
}