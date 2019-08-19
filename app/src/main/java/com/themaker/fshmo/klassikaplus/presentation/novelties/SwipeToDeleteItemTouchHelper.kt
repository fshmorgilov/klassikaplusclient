package com.themaker.fshmo.klassikaplus.presentation.novelties

import android.graphics.Canvas
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.themaker.fshmo.klassikaplus.data.domain.Item

class SwipeToDeleteItemTouchHelper(
    private val isSwipeEnabled: (Item) -> Boolean = { true },
    private val onSwiped: (Item, Int) -> Unit,
    private val adapter: NoveltyAdapter,
    private val contentViewExtractor: (View?) -> View? = { it },
    private val onStartSwipe: () -> Unit = {},
    private val onFinishSwipe: () -> Unit = {} // cancel swipe and return back to position
) : ItemTouchHelper(
    object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        private var shouldCallStartSwipe = true
        private var shouldCallFinishSwipe = true
        override fun onMove(
            recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun getSwipeDirs(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            callStartSwipe()
            val position = viewHolder.adapterPosition
            val item = adapter.getItem(position)
            return when {
                item != null && isSwipeEnabled(item) -> ItemTouchHelper.LEFT
                else -> 0
            }
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            callFinishSwipe()
            val position = viewHolder.adapterPosition
            val item = adapter.getItem(position)
            item?.let {
                onSwiped(item, position)
            } ?: Log.e(TAG, "adapter.getItem($position) returns null")
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            callFinishSwipe()
            ItemTouchHelper.Callback.getDefaultUIUtil().clearView(contentViewExtractor.invoke(viewHolder.itemView))
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            ItemTouchHelper.Callback.getDefaultUIUtil().onSelected(contentViewExtractor.invoke(viewHolder?.itemView))
        }

        override fun onChildDraw(
            c: Canvas, recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
        ) {
            ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(
                c, recyclerView,
                contentViewExtractor.invoke(viewHolder.itemView), dX, dY, actionState, isCurrentlyActive
            )
        }

        override fun onChildDrawOver(
            c: Canvas, recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder?,
            dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
        ) {
            ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(
                c, recyclerView,
                contentViewExtractor.invoke(viewHolder?.itemView), dX, dY, actionState, isCurrentlyActive
            )
        }

        private fun callStartSwipe() {
            shouldCallFinishSwipe = true
            if (shouldCallStartSwipe) {
                shouldCallStartSwipe = false
                onStartSwipe()
            }
        }

        private fun callFinishSwipe() {
            shouldCallStartSwipe = true
            if (shouldCallFinishSwipe) {
                shouldCallFinishSwipe = false
                onFinishSwipe()
            }
        }
    }
) {
    companion object {
        private val TAG = SwipeToDeleteItemTouchHelper::class.java.simpleName
    }
}