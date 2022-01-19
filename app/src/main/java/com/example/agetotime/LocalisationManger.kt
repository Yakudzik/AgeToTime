package com.example.agetotime

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import org.intellij.lang.annotations.Language
import java.util.*

object LocalisationManger {
    fun setLocalisation(context: Context): Context {
        return if (Lapp.instance!!.getLanguage() != null)
            updateResources(context, Lapp.instance!!.getLanguage()!!)
        else
            context
    }

    fun setNewLocalisation(context: Context, language: String): Context {
        Lapp.instance!!.setLanguage(language)
        return updateResources(context, language)
    }

    private fun updateResources(context: Context, language: String): Context {
        var localeContext: Context = context
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res: Resources = context.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        localeContext = context.createConfigurationContext(config)
        return localeContext
    }
}