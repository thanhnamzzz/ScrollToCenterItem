package lib.virgo.lib_scrollcenteritem.util

import androidx.recyclerview.widget.RecyclerView
import lib.virgo.lib_scrollcenteritem.callback.ScrollListener
import lib.virgo.lib_scrollcenteritem.callback.ScrollStateChangeListener

/**
 * Rebuilt from [DroidSky/DiscreteScrollView](https://github.com/DroidSky/DiscreteScrollView)
 */
class ScrollListenerAdapter<T : RecyclerView.ViewHolder>(
    private val adaptee: ScrollListener<T>,
) : ScrollStateChangeListener<T> {

    override fun onScrollStart(currentItemHolder: T, adapterPosition: Int) {}

    override fun onScrollEnd(currentItemHolder: T, adapterPosition: Int) {}

    override fun onScroll(scrollPosition: Float, currentHolder: T, newCurrent: T) {
        adaptee.onScroll(scrollPosition, currentHolder, newCurrent)
    }
}
