package com.example.agetotime

import android.app.Application
import android.content.Context
import java.util.*

class Lapp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun setLanguage(localeKey: String) {
        val preference = getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
        preference.putString(LOCALE, localeKey)
        preference.apply()
    }

    fun getLanguage(): String? {
        val preference = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return preference.getString(LOCALE, "en")
    }

    companion object {
        var instance: Lapp? = null
        const val PREFS: String = "SHARED_PREFS"
        const val LOCALE: String = "LOCALE"

    }

}