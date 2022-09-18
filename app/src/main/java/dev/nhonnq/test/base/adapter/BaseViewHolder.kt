package dev.nhonnq.test.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<V>(var viewRoot: View, var data: V) :
    RecyclerView.ViewHolder(viewRoot) {

    fun bindData() {
        bindEvent()
    }

    fun bind(data: V, position: Int) {}

    fun bindEvent() {}

}