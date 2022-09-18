package dev.nhonnq.test.extensions

import android.view.View

infix fun View.onClick(onClicked: (View) -> Unit){
    setOnClickListener {
        onClicked(this)
    }
}

fun <T> List<T>.toArrayList() : ArrayList<T>{
    val list = arrayListOf<T>()
    list.addAll(this)
    return list
}