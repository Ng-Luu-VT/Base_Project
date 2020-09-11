package com.guest_escort.app.extension

import java.text.DecimalFormat

fun Number.toCurrencyString() : String {
    val m = this.toDouble()
    val formatter = DecimalFormat("###,###,###")
    return formatter.format(m) + " đ"
}