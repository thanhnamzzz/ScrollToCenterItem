package lib.virgo.lib_scrollcenteritem

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import lib.virgo.lib_scrollcenteritem.callback.OnItemChangedListener
import lib.virgo.lib_scrollcenteritem.callback.ScrollStateChangeListener
import androidx.core.content.withStyledAttributes
import lib.virgo.lib_scrollcenteritem.callback.IScrollStateListener
import lib.virgo.lib_scrollcenteritem.callback.ScrollListener
import lib.virgo.lib_scrollcenteritem.transform.ScrollItemTransformer
import lib.virgo.lib_scrollcenteritem.util.ScrollListenerAdapter

/**
 * Rebuilt from [DroidSky/DiscreteScrollView](https://github.com/DroidSky/DiscreteScrollView)
 */
class ScrollToCenterView @JvmOverloads constructor(
    private val context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RecyclerView(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_ORIENTATION = 0
    }

    private val layoutManager: ScrollToCenterLayoutManager
    private var scrollStateChangeListener: ScrollStateChangeListener<ViewHolder>? = null

    init {
        var orientation = DEFAULT_ORIENTATION

        context.withStyledAttributes(attrs, R.styleable.ScrollToCenterView) {
            orientation = getInt(R.styleable.ScrollToCenterView_stc_orientation, DEFAULT_ORIENTATION)
        }
        val o = if (orientation == 1) OrientationView.VERTICAL else OrientationView.HORIZONTAL
        layoutManager = ScrollToCenterLayoutManager(context, ScrollStateListener(), o)
        super.setLayoutManager(layoutManager)
    }

    override fun setLayoutManager(layout: LayoutManager?) {
        if (layout is ScrollToCenterLayoutManager) {
            super.setLayoutManager(layout)
        } else {
            throw IllegalArgumentException(
                context.getString(R.string.dsv_ex_msg_dont_set_lm)
            )
        }
    }

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        val isFling = super.fling(velocityX, velocityY)
        if (isFling) {
            layoutManager.onFling(velocityX, velocityY)
        } else {
            layoutManager.returnToCurrentPosition()
        }
        return isFling
    }

    fun getViewHolder(position: Int): ViewHolder? {
        val view: View? = layoutManager.findViewByPosition(position)
        return view?.let { getChildViewHolder(it) }
    }

    /**
     * @return adapter position of the current item or -1 if nothing is selected
     */
    fun getCurrentItem(): Int = layoutManager.currentPosition

    fun setItemTransformer(transformer: ScrollItemTransformer?) {
        layoutManager.setItemTransformer(transformer)
    }

    fun setItemTransitionTimeMillis(@IntRange(from = 10) millis: Int) {
        layoutManager.setTimeForItemSettle(millis)
    }

    fun setOrientation(orientation: OrientationView) {
        layoutManager.setOrientation(orientation)
    }

    fun setOffscreenItems(items: Int) {
        layoutManager.setOffscreenItems(items)
    }

    fun setScrollStateChangeListener(listener: ScrollStateChangeListener<ViewHolder>?) {
        scrollStateChangeListener = listener
    }

    fun setScrollListener(scrollListener: ScrollListener<ViewHolder>) {
        setScrollStateChangeListener(ScrollListenerAdapter(scrollListener))
    }
    private var onItemChangedListener: OnItemChangedListener<ViewHolder>? = null
    fun setOnItemChangedListener(listener: OnItemChangedListener<ViewHolder>?) {
        onItemChangedListener = listener
    }

    private inner class ScrollStateListener : IScrollStateListener {

        override fun onIsBoundReachedFlagChange(isBoundReached: Boolean) {
            overScrollMode = if (isBoundReached) OVER_SCROLL_ALWAYS else OVER_SCROLL_NEVER
        }

        override fun onScrollStart() {
            scrollStateChangeListener?.let { listener ->
                val current = layoutManager.currentPosition
                val holder = getViewHolder(current)
                if (holder != null) {
                    listener.onScrollStart(holder, current)
                }
            }
        }

        override fun onScrollEnd() {
            val current = layoutManager.currentPosition
            var holder = getViewHolder(current)

            scrollStateChangeListener?.let { listener ->
                if (holder == null) return
                listener.onScrollEnd(holder, current)
            }

            onItemChangedListener?.let { listener ->
                if (holder == null) {
                    holder = getViewHolder(current)
                }
                if (holder != null) {
                    listener.onCurrentItemChanged(holder, current)
                }
            }
        }

        override fun onScroll(currentViewPosition: Float) {
            scrollStateChangeListener?.let { listener ->
                val current = getCurrentItem()
                val currentHolder = getViewHolder(current)

                val newCurrent = current + if (currentViewPosition < 0) 1 else -1
                val newCurrentHolder = getViewHolder(newCurrent)

                if (currentHolder != null && newCurrentHolder != null) {
                    listener.onScroll(currentViewPosition, currentHolder, newCurrentHolder)
                }
            }
        }

        override fun onCurrentViewFirstLayout() {
            onItemChangedListener?.let { listener ->
                val current = layoutManager.currentPosition
                val holder = getViewHolder(current)
                if (holder != null) {
                    listener.onCurrentItemChanged(holder, current)
                }
            }
        }
    }
}