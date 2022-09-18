package dev.nhonnq.test.utils

import android.content.Context
import android.graphics.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dev.nhonnq.test.R
import dev.nhonnq.test.extensions.getColor
import dev.nhonnq.test.listeners.ItemSwipeListener

class SwipeTouchHelper(
    private val context: Context,
    private val itemSwipeListener: ItemSwipeListener
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val p = Paint()

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.absoluteAdapterPosition
        if (direction == ItemTouchHelper.LEFT) {
            itemSwipeListener.onSwipeLeft(position)
        }
        /*else if (direction == ItemTouchHelper.RIGHT) {
            itemSwipeListener.onSwipeRight(position)
        }*/
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView
            val height = itemView.bottom.toFloat() - itemView.top.toFloat()
            val width = height / 3

            if (dX < 0) {
                p.color = R.color.orange.getColor()
                val background = RectF(
                    itemView.right.toFloat() + dX,
                    itemView.top.toFloat(),
                    itemView.right.toFloat(),
                    itemView.bottom.toFloat()
                )
                c.drawRect(background, p)
                val icon = BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.ic_delete
                )
                val margin = (dX / 5 - width) / 2
                val iconDest = RectF(
                    itemView.right.toFloat() + margin,
                    itemView.top.toFloat() + width,
                    itemView.right.toFloat() + (margin + width),
                    itemView.bottom.toFloat() - width
                )
                c.drawBitmap(icon, null, iconDest, p)
            }
            /*if (dX > 0) {
                p.color = R.color.orange.getColor()
                val background = RectF(
                    itemView.left.toFloat(),
                    itemView.top.toFloat(),
                    itemView.left.toFloat() + dX,
                    itemView.bottom.toFloat()
                )
                c.drawRect(background, p)
                val icon = BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.ic_ticked
                )
                val margin = (dX / 5 - width) / 2
                val iconDest = RectF(
                    margin + 35f,
                    itemView.top.toFloat() + width,
                    margin + 35f + width,
                    itemView.bottom.toFloat() - width
                )
                c.drawBitmap(icon, null, iconDest, p)
            }*/
        } else {
            c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        }
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            dX / 5,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

}

