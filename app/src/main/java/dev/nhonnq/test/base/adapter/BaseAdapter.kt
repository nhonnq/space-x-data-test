package dev.nhonnq.test.base.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class BaseAdapter<V, VH : BaseHolder<V>>(protected var inflater: LayoutInflater) :
    RecyclerView.Adapter<VH>() {

    var dataSource = ArrayList<V>()
    var isEnd = false

    constructor(inflater: LayoutInflater, dataSource: ArrayList<V>?) : this(inflater) {
        this.dataSource = ArrayList(dataSource)
    }

    @CallSuper
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindView(dataSource[position], position)
        holder.event()
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).hashCode().toLong()
    }

    fun getItem(position: Int): V {
        return dataSource[position]
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    /**
     * Unity methods
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setDataSource(dataSource: List<V>) {
        try {
            this.dataSource = ArrayList(dataSource)
            notifyDataSetChanged()
        } catch (ignored: IllegalStateException) {
        }
    }

    fun getDataSource(): List<V> {
        return dataSource
    }

    fun appendItem(item: V) {
        dataSource.add(item)
        notifyItemInserted(itemCount - 1)
    }

    fun appendItemPosition(item: V, position: Int) {
        if (position < 0 || position > dataSource.size) {
            return
        }
        dataSource.add(position, item)
        notifyItemInserted(position)
    }

    fun updateItem(position: Int, item: V) {
        if (position < 0 || position >= dataSource.size) {
            return
        }
        dataSource[position] = item
        notifyItemChanged(position)
    }

    fun updateItemObject(position: Int, item: V) {
        if (position < 0 || position >= dataSource.size) {
            return
        }
        dataSource[position] = item
        notifyItemChanged(position, item)
    }

    fun updateItem(position: Int, headerSize: Int, item: V) {
        if (dataSource.size > position) {
            dataSource[position] = item
            notifyItemChanged(position + headerSize)
        }
    }

    fun removeAtPosition(position: Int) {
        if (position < 0 || position >= dataSource.size) {
            return
        }
        dataSource.removeAt(position)
        notifyItemRemoved(position)
    }

    fun appendItems(items: List<V>) {
        val positionStart = dataSource.size
        dataSource.addAll(items)
        notifyItemRangeInserted(positionStart, items.size)
    }

    fun appendItemsAtFirst(items: List<V>) {
        dataSource.addAll(0, items)
        notifyItemRangeInserted(0, items.size)
    }

    fun addItemAtFirst(item: V) {
        dataSource.add(0, item)
        notifyItemInserted(0)
    }

    fun addAtFirstAndRemoveEnd(item: V) {
        dataSource.add(0, item)
        dataSource.removeAt(dataSource.size - 1)
        notifyItemRemoved(dataSource.size - 1)
        notifyItemInserted(0)
    }

}