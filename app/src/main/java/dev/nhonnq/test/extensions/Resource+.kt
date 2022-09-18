package dev.nhonnq.test.extensions

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.content.res.ResourcesCompat
import dev.nhonnq.test.MVVMApplication

@ColorInt
fun Int.getColor(): Int {
    return ResourcesCompat.getColor(MVVMApplication.instance.resources, this, null)
}

fun Int.getString(): String{
    return MVVMApplication.instance.getString(this)
}

fun Int.getDrawable(): Drawable? {
    return ResourcesCompat.getDrawable(MVVMApplication.instance.resources, this, null)
}
