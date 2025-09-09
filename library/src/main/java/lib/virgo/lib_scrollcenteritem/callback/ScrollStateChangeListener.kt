package lib.virgo.lib_scrollcenteritem.callback

import androidx.recyclerview.widget.RecyclerView

interface ScrollStateChangeListener<out T : RecyclerView.ViewHolder> {
    fun onScrollStart(currentItemHolder: @UnsafeVariance T, adapterPosition: Int)
    fun onScrollEnd(currentItemHolder: @UnsafeVariance T, adapterPosition: Int)
    fun onScroll(scrollPosition: Float, currentHolder: @UnsafeVariance T, newCurrent: @UnsafeVariance T)
}