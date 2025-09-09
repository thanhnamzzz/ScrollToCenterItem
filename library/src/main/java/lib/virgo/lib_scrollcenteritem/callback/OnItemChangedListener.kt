package lib.virgo.lib_scrollcenteritem.callback

import androidx.recyclerview.widget.RecyclerView

fun interface OnItemChangedListener<out T : RecyclerView.ViewHolder> {
    /**
     * This method will also be triggered when view appears on the screen for the first time.
     */
    fun onCurrentItemChanged(viewHolder: @UnsafeVariance T, adapterPosition: Int)
}