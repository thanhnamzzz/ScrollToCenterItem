package lib.virgo.lib_scrollcenteritem.callback

import androidx.recyclerview.widget.RecyclerView

fun interface ScrollListener<T : RecyclerView.ViewHolder> {
    fun onScroll(scrollPosition: Float, currentHolder: T, newCurrent: T)
}