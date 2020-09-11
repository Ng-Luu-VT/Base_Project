package com.teachenglish.ielts.data.error

import java.io.IOException

class ApiException(url: String, message: String?, status: Int) :
    IOException() {
    var error: ApiError
    var url: String
    override val message: String
        get() = error.message!!

    init {
        error = ApiError()
        error.message = message
        error.status = status
        this.url = url
    }
}