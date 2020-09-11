package com.teachenglish.techenglish.utils

import android.content.res.Resources
import java.util.regex.Pattern

class AndroidUtilities {

    companion object {
        fun getWidthScreen(): Int {
            return Resources.getSystem().displayMetrics.widthPixels;
        }

        fun getHeightScreen(): Int {
            return Resources.getSystem().displayMetrics.heightPixels;
        }


        val VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

        fun validateEmail(emailStr: String): Boolean {
            val matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr)
            return matcher.find()
        }

        fun hexStringToByteArray(s: String): ByteArray? {
            val len = s.length
            val data = ByteArray(len / 2)
            var i = 0
            while (i < len) {
                data[i / 2] = ((Character.digit(s[i], 16) shl 4)
                        + Character.digit(s[i + 1], 16)).toByte()
                i += 2
            }
            return data
        }
    }
}