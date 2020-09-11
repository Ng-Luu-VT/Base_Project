package com.teachenglish.ielts.data.error

import com.google.gson.annotations.SerializedName

class ApiError : Error() {
    @SerializedName("message")
    override var message: String? = null
    @SerializedName("status")
    var status = 0
}