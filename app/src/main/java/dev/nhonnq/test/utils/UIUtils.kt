package dev.nhonnq.test.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import dev.nhonnq.test.R

private var screenWidth: Int = 0
private var screenHeight: Int = 0
private var deviceSerial: String = ""

fun Activity.hideSoftKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.showSoftKeyboard() {
    val inputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Context.getScreenHeight(): Int {
    if (screenHeight == 0) {
        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        screenHeight = size.y
    }
    return screenHeight
}

fun Context.getScreenWidth(): Int {
    if (screenWidth == 0) {
        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        screenWidth = size.x
    }
    return screenWidth
}

@SuppressLint("HardwareIds")
fun Context.getDeviceSerial(): String {
    if (deviceSerial.isNotEmpty()) return deviceSerial
    return Settings.Secure.getString(
        this.contentResolver,
        Settings.Secure.ANDROID_ID
    )
}

fun getDeviceName(): String {
    val manufacturer = Build.MANUFACTURER
    val model = Build.MODEL
    return if (model.startsWith(manufacturer)) {
        capitalize(model)
    } else {
        capitalize(manufacturer) + " " + model
    }
}

private fun capitalize(str: String): String {
    return capitalize(str, null)
}

private fun capitalize(str: String, delimiters: CharArray?): String {
    val delimiterLen = delimiters?.size ?: -1
    if (!TextUtils.isEmpty(str) && delimiterLen != 0) {
        val buffer = str.toCharArray()
        var capitalizeNext = true

        for (i in buffer.indices) {
            val ch = buffer[i]
            if (isDelimiter(ch, delimiters)) {
                capitalizeNext = true
            } else if (capitalizeNext) {
                buffer[i] = Character.toTitleCase(ch)
                capitalizeNext = false
            }
        }

        return String(buffer)
    } else {
        return str
    }
}

private fun isDelimiter(ch: Char, delimiters: CharArray?): Boolean {
    if (delimiters == null) {
        return Character.isWhitespace(ch)
    } else {
        val len = delimiters.size
        for (`i$` in 0 until len) {
            val delimiter = delimiters[`i$`]
            if (ch == delimiter) {
                return true
            }
        }
        return false
    }
}

fun Activity.changeColorStatusBar(color: Int) {
    val window = this.window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.statusBarColor = ContextCompat.getColor(this, color)
}

fun Activity.changeStatusBarWhite(light: Boolean = true) {
    changeColorStatusBar(if (light) R.color.white else R.color.color_primary)
    val window = this.window
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.setSystemBarsAppearance(
            if (light) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    } else {
        val windowInsetController = ViewCompat.getWindowInsetsController(window.decorView)
        windowInsetController?.isAppearanceLightStatusBars = light
    }
}

/**
 * Transparent StatusBar
 */
fun Activity.transparentStatusBar() {
    val window = this.window
    window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    window.statusBarColor = Color.TRANSPARENT
}


