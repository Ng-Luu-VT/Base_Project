package com.teachenglish.techenglish.extension

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.provider.Settings
import android.widget.Toast

//fun Context.getIP() : String {
//    val wm =
//        applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//    return Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
//}

fun Context.getUUID(): String? {
    return Settings.Secure.getString(
        applicationContext.contentResolver,
        Settings.Secure.ANDROID_ID
    )
}

fun Context.getVersionApp(): String? {
    var version = ""
    try {
        val pInfo: PackageInfo =
            packageManager
                .getPackageInfo(packageName, 0)
        version = pInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return version
}

fun Context.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}