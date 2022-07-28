package com.aslansari.bitcointicker.ui.util

import android.content.Context
import android.content.res.Resources
import com.aslansari.bitcointicker.R

object DisplayColorUtil {

    fun getGainColor(): Int = R.color.gain

    fun getGainTextColor(resources: Resources): Int = resources.getColor(R.color.md_theme_light_onSurface)

    fun getLossColor(context: Context): Int = if (DarkModeUtil.isDarkMode(context)) {
        R.color.md_theme_dark_error
    } else {
        R.color.md_theme_light_error
    }

    fun getLossTextColor(context: Context, resources: Resources): Int = if (DarkModeUtil.isDarkMode(context)) {
        resources.getColor(R.color.md_theme_dark_onError)
    } else {
        resources.getColor(R.color.md_theme_light_onError)
    }

}