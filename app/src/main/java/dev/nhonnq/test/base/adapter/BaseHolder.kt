package dev.nhonnq.test.base.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseHolder<V> : RecyclerView.ViewHolder {

    constructor(binding: ViewDataBinding) : super(binding.root)

    constructor(itemView: View) : super(itemView)

    protected open fun bind(data: V, position: Int) {}

    fun bindView(data: V, position: Int) {
        bind(data, position)
    }

    fun event() {}
}