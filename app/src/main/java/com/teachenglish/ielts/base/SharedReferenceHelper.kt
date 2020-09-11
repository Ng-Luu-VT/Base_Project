package com.teachenglish.ielts.base

import android.content.Context
import android.content.SharedPreferences


class SharedReferenceHelper(appContext: Context) {

    companion object {
        const val THEME = "Theme"
        const val USER = "USER"
        const val LOCALE = "LOCALE"
    }

    private var sharedPreferences: SharedPreferences =
        appContext.getSharedPreferences("APP_DATA", Context.MODE_PRIVATE)

    fun helloWord() = sharedPreferences.getString("hell", "Hell")
//
//    fun getTheme() = sharedPreferences.getString(THEME, Resources.Theme.LIGHT.name) ?: Theme.LIGHT.name
//
//    fun setTheme(theme: Theme) {
//        sharedPreferences.edit().putString(THEME, theme.name).apply()
//    }
//
//    fun setUserLogin(userModel: UserModel?) {
////        EmployeeApplication.userModel = userModel
//        sharedPreferences.edit().putString(USER, Gson().toJson(userModel)).apply()
//    }
//
//    fun getUserLogin(): UserModel? {
//        val string = sharedPreferences.getString(USER, "")
//        if (string?.isEmpty() == true) return null
//        return Gson().fromJson(string, UserModel::class.java)
//    }

    fun getLocale(): String {
        return sharedPreferences.getString(LOCALE, "")!!
    }

    fun getUser(): String {
        return sharedPreferences.getString(USER, "")!!
    }

    fun clearUser() {
        return sharedPreferences.edit().putString(USER, "").apply()

    }
}