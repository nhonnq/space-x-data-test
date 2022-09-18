package dev.nhonnq.test.base.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import dev.nhonnq.test.R
import java.util.concurrent.atomic.AtomicBoolean

class EndlessRecyclerAdapter private constructor(
    private var context: Context,
    wrapped: RecyclerView.Adapter<*>,
    private val listener: RequestToLoadMoreListener, @param:LayoutRes private val pendingViewResId: Int,
    keepOnAppending: Boolean
) :
    RecyclerAdapterWrapper(wrapped as RecyclerView.Adapter<RecyclerView.ViewHolder>) {
    private val keepOnAppending: AtomicBoolean = AtomicBoolean(keepOnAppending)
    private val dataPending: AtomicBoolean = AtomicBoolean(false)

    companion object {
        private const val TYPE_PENDING = 999
        private const val TYPE_TRANSACTION = 998
    }

    constructor(
        context: Context,
        wrapped: RecyclerView.Adapter<*>,
        requestToLoadMoreListener: RequestToLoadMoreListener
    ) : this(context, wrapped, requestToLoadMoreListener, R.layout.item_loading, false)

    private fun stopAppending() {
        setKeepOnAppending(false)
    }

    /**
     * Let the adapter know that data is load and ready to view.
     *
     * @param keepOnAppending whether the adapter should request to load more when scrolling to the bottom.
     */
    fun onDataReady(keepOnAppending: Boolean) {
        dataPending.set(false)
        setKeepOnAppending(keepOnAppending)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setKeepOnAppending(newValue: Boolean) {
        keepOnAppending.set(newValue)
        wrappedAdapter.notifyDataSetChanged()
    }

    /**
     *
     */
    fun restartAppending() {
        dataPending.set(false)
        setKeepOnAppending(true)
    }

    private fun getPendingView(viewGroup: ViewGroup): View {
        return LayoutInflater.from(context).inflate(pendingViewResId, viewGroup, false)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (keepOnAppending.get()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        if (position == wrappedAdapter.itemCount) {
            return TYPE_PENDING
        } else if (position > 0 && position == wrappedAdapter.itemCount) {
            return TYPE_TRANSACTION
        }
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == TYPE_PENDING) {
            PendingViewHolder(getPendingView(parent))
        } else super.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (getItemViewType(position) == TYPE_PENDING) {
            if (!dataPending.get()) {
                dataPending.set(true)
                listener.onLoadMoreRequested()
            }
        } else if (getItemViewType(position) == TYPE_TRANSACTION) {
            if (!dataPending.get()) {
                dataPending.set(true)
                listener.onLoadMoreRequested()
            }
            super.onBindViewHolder(holder, position)
        } else {
            super.onBindViewHolder(holder, position)
        }
    }

    interface RequestToLoadMoreListener {
        /**
         * The adapter requests to load more data.
         */
        fun onLoadMoreRequested()
    }

    internal inner class PendingViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        init {
            val itemLoadMore = itemView.findViewById<LinearLayout>(R.id.itemLoadMore)
            val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)
            //Todo: add more actions
        }
    }

}
