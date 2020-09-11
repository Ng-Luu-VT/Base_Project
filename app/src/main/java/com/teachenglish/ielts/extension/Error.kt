package com.teachenglish.ielts.extension

import androidx.lifecycle.MutableLiveData
import com.teachenglish.ielts.R
import com.teachenglish.ielts.base.GEApp
import com.teachenglish.ielts.model.local.AsyncState
import java.net.ConnectException

import java.net.UnknownHostException

fun Throwable.handleError(asyncState: MutableLiveData<AsyncState>) {
    if ((this as? ConnectException) != null) {
        asyncState.value =
            AsyncState.Failed(Throwable(GEApp.context.getString(R.string.network_error)))
    } else if ((this as? UnknownHostException) != null) {
        asyncState.value =
            AsyncState.Failed(Throwable(GEApp.context.getString(R.string.can_not_connect_to_server)))
    }
    //   }| } else if (this as? TokenException != null) {}
//
//    }
    else
        asyncState.value =
            AsyncState.Failed(Throwable(GEApp.context.getString(R.string.message_error)))
}