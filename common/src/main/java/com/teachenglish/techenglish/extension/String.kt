package com.teachenglish.techenglish.extension

import java.security.MessageDigest
import java.text.Normalizer
import java.util.regex.Pattern

fun String.toSHA1(): String {
    val bytes = toByteArray()
    val md = MessageDigest.getInstance("SHA-1")
    val digest = md.digest(bytes)
    var result = ""
    for (byte in digest) result += "%02x".format(byte)
    return result
}

/**
 * Export link từ chuỗi String
 */
fun String.extractUrls(): MutableList<String>? {
    val containedUrls = mutableListOf<String>()
    val urlRegex =
        "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)"
    val pattern =
        Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE)
    val urlMatcher = pattern.matcher(this)
    while (urlMatcher.find()) {
        containedUrls.add(
            this.substring(
                urlMatcher.start(0),
                urlMatcher.end(0)
            ).split("<").toTypedArray()[0]
        )
    }
    return containedUrls
}

/**
 * Xoá ký tự đặc biệt
 */
fun String?.unAccent(): String? {
    return if (this == null) null else Normalizer
        .normalize(this, Normalizer.Form.NFD)
        .replace("Đ".toRegex(), "D").replace("đ", "d")
        .replace("[^\\p{ASCII}]".toRegex(), "")
}

/**
 * Xoá khoảng trắng
 */
fun String.removeWhiteSpace(): String? {
    return trim()
}


/**
 * Xoá khoảng trắng và ký tự đặc biệt
 */
fun String.removeAllSpaceAndSpecialChars(): String? {
    return unAccent()?.replace("[^a-zA-Z0-9_-]".toRegex(), "")
        ?.replace(" ", "")
}

/**
 * Xoá các ký tự đặc biệt, chỉ chấp nhận giá trị a-z, 0-9 và dấu _
 */
fun String.removeSpecialCharacter(): String? {
    return replace("[^a-zA-Z0-9_-]".toRegex(), "")
}