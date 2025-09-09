package lib.virgo.lib_scrollcenteritem

/**
 * Rebuilt from [DroidSky/DiscreteScrollView](https://github.com/DroidSky/DiscreteScrollView)
 */
//class ScrollToCenterLayoutManager(
//    private val context: Context,
//    private val iScrollStateListener: IScrollStateListener,
//    orientation: OrientationView,
//) : RecyclerView.LayoutManager() {
//    companion object {
//        private const val EXTRA_POSITION = "extra_position"
//        private const val NO_POSITION = -1
//        private const val DEFAULT_TIME_FOR_ITEM_SETTLE = 150
//    }
//
//    private val viewCenterIterator = Point()
//    private val recyclerCenter = Point()
//    private val currentViewCenter = Point()
//
//    private var childHalfWidth = 0
//    private var childHalfHeight = 0
//    private var extraLayoutSpace = 0
//
//    private var scrollToChangeCurrent = 0
//    private var currentScrollState = 0
//
//    private var orientationHelper: Helper = orientation.createHelper()
//
//    private var scrolled = 0
//    private var pendingScroll = 0
//    private var currentPosition = NO_POSITION
//    private var pendingPosition = NO_POSITION
//
//    private var timeForItemSettle = DEFAULT_TIME_FOR_ITEM_SETTLE
//    private var offscreenItems = 0
//
//    private val detachedCache = mutableMapOf<Int, View>()
//
//    private var itemTransformer: ScrollItemTransformer? = null
//
//    override fun isAutoMeasureEnabled(): Boolean = true
//
//    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
//        if (state.itemCount == 0) {
//            removeAndRecycleAllViews(recycler)
//            currentPosition = NO_POSITION
//            pendingPosition = NO_POSITION
//            scrolled = 0
//            pendingScroll = 0
//            return
//        }
//
//        val isFirstOrEmptyLayout = childCount == 0
//        if (isFirstOrEmptyLayout) {
//            initChildDimensions(recycler)
//        }
//
//        updateRecyclerDimensions()
//        detachAndScrapAttachedViews(recycler)
//        fill(recycler)
//        applyItemTransformToChildren()
//
//        if (isFirstOrEmptyLayout) {
//            iScrollStateListener.onCurrentViewFirstLayout()
//        }
//    }
//
//    private fun initChildDimensions(recycler: RecyclerView.Recycler) {
//        val viewToMeasure = recycler.getViewForPosition(0)
//        addView(viewToMeasure)
//        measureChildWithMargins(viewToMeasure, 0, 0)
//
//        val childViewWidth = getDecoratedMeasuredWidth(viewToMeasure)
//        val childViewHeight = getDecoratedMeasuredHeight(viewToMeasure)
//
//        childHalfWidth = childViewWidth / 2
//        childHalfHeight = childViewHeight / 2
//
//        scrollToChangeCurrent =
//            orientationHelper.getDistanceToChangeCurrent(childViewWidth, childViewHeight)
//
//        extraLayoutSpace = scrollToChangeCurrent * offscreenItems
//
//        detachAndScrapView(viewToMeasure, recycler)
//    }
//
//    private fun updateRecyclerDimensions() {
//        recyclerCenter.set(width / 2, height / 2)
//    }
//
//    private fun fill(recycler: RecyclerView.Recycler) {
//        cacheAndDetachAttachedViews()
//        orientationHelper.setCurrentViewCenter(recyclerCenter, scrolled, currentViewCenter)
//
//        val endBound = orientationHelper.getViewEnd(width, height)
//
//        if (isViewVisible(currentViewCenter, endBound)) {
//            layoutView(recycler, currentPosition, currentViewCenter)
//        }
//
//        layoutViews(recycler, Direction.START, endBound)
//        layoutViews(recycler, Direction.END, endBound)
//
//        recycleViewsAndClearCache(recycler)
//    }
//
//    private fun layoutViews(recycler: RecyclerView.Recycler, direction: Direction, endBound: Int) {
//        val positionStep = direction.applyTo(1)
//        viewCenterIterator.set(currentViewCenter.x, currentViewCenter.y)
//
//        var i = currentPosition + positionStep
//        while (isInBounds(i)) {
//            orientationHelper.shiftViewCenter(direction, scrollToChangeCurrent, viewCenterIterator)
//            if (isViewVisible(viewCenterIterator, endBound)) {
//                layoutView(recycler, i, viewCenterIterator)
////            } else {
////                break
//            }
//            i += positionStep
//        }
//    }
//
//    private fun isInBounds(itemPosition: Int): Boolean =
//        itemPosition >= 0 && itemPosition < itemCount
//
//    private fun layoutView(recycler: RecyclerView.Recycler, position: Int, viewCenter: Point) {
//        var v = detachedCache[position]
//        v?.let {
//            attachView(it)
//            detachedCache.remove(position)
//        } ?: run {
//            v = recycler.getViewForPosition(position)
//            addView(v)
//            measureChildWithMargins(v, 0, 0)
//            layoutDecoratedWithMargins(
//                v,
//                viewCenter.x - childHalfWidth, viewCenter.y - childHalfHeight,
//                viewCenter.x + childHalfWidth, viewCenter.y + childHalfHeight
//            )
//        }
//    }
//
//    private fun cacheAndDetachAttachedViews() {
//        detachedCache.clear()
//        for (i in 0 until childCount) {
//            getChildAt(i)?.let {
//                detachedCache.put(getPosition(it), it)
//            }
//        }
////        for (i in childCount - 1 downTo 0) {
////            detachViewAt(i)
////        }
//        for (i in 0 until detachedCache.size) {
//            detachedCache[i]?.let {
//                detachView(it)
//            }
////            detachView(detachedCache[i]!!)
////            detachViewAt(detachedCache[i].)
//        }
//    }
//
//    private fun recycleViewsAndClearCache(recycler: RecyclerView.Recycler) {
//        for (view in detachedCache.values) {
//            recycler.recycleView(view)
//        }
//        detachedCache.clear()
//    }
//
//    override fun onItemsAdded(recyclerView: RecyclerView, positionStart: Int, itemCount: Int) {
//        currentPosition = when {
//            currentPosition == NO_POSITION -> 0
//            currentPosition >= positionStart -> min(currentPosition + itemCount, itemCount - 1)
//            else -> currentPosition
//        }
//    }
//
//    override fun onItemsRemoved(recyclerView: RecyclerView, positionStart: Int, itemCount: Int) {
//        currentPosition = when {
//            itemCount == 0 -> NO_POSITION
//            currentPosition >= positionStart -> max(0, currentPosition - itemCount)
//            else -> currentPosition
//        }
//    }
//
//    override fun onItemsChanged(recyclerView: RecyclerView) {
//        currentPosition = min(max(0, currentPosition), itemCount - 1)
//    }
//
//    override fun scrollHorizontallyBy(
//        dx: Int,
//        recycler: RecyclerView.Recycler,
//        state: RecyclerView.State,
//    ): Int = scrollBy(dx, recycler)
//
//    override fun scrollVerticallyBy(
//        dy: Int,
//        recycler: RecyclerView.Recycler,
//        state: RecyclerView.State,
//    ): Int = scrollBy(dy, recycler)
//
//    private fun scrollBy(amount: Int, recycler: RecyclerView.Recycler): Int {
//        if (childCount == 0) return 0
//
//        val direction = Direction.fromDelta(amount)
//        val leftToScroll = calculateAllowedScrollIn(direction)
//        if (leftToScroll <= 0) return 0
//
//        val delta = direction.applyTo(min(leftToScroll, abs(amount)))
//        scrolled += delta
//        if (pendingScroll != 0) pendingScroll -= delta
//
//        orientationHelper.offsetChildren(-delta, this)
//
//        if (orientationHelper.hasNewBecomeVisible(this)) {
//            fill(recycler)
//        }
//
//        notifyScroll()
//        applyItemTransformToChildren()
//        return delta
//    }
//
//    private fun applyItemTransformToChildren() {
//        itemTransformer?.let { transformer ->
//            for (i in 0 until childCount) {
//                val child = getChildAt(i)!!
//                transformer.transformItem(child, getCenterRelativePositionOf(child))
//            }
//        }
//    }
//
//    override fun scrollToPosition(position: Int) {
//        if (currentPosition == position) return
//        currentPosition = position
//        requestLayout()
//    }
//
//    override fun smoothScrollToPosition(
//        recyclerView: RecyclerView,
//        state: RecyclerView.State,
//        position: Int,
//    ) {
//        if (currentPosition == position) return
//
//        pendingScroll = -scrolled
//        val direction = Direction.fromDelta(position - currentPosition)
//        val distanceToScroll = abs(position - currentPosition) * scrollToChangeCurrent
//        pendingScroll += direction.applyTo(distanceToScroll)
//
//        pendingPosition = position
//        startSmoothPendingScroll()
//    }
//
//    override fun canScrollHorizontally(): Boolean = orientationHelper.canScrollHorizontally()
//    override fun canScrollVertically(): Boolean = orientationHelper.canScrollVertically()
//
//    override fun onScrollStateChanged(state: Int) {
//        if (currentScrollState == RecyclerView.SCROLL_STATE_IDLE && 0 != state) {
//            iScrollStateListener.onScrollStart()
//        }
//        if (state == RecyclerView.SCROLL_STATE_IDLE) {
//            val isScrollEnded = onScrollEnd()
//            if (isScrollEnded) iScrollStateListener.onScrollEnd() else return
//        } else if (state == RecyclerView.SCROLL_STATE_DRAGGING) {
//            onDragStart()
//        }
//        currentScrollState = state
//    }
//
//    private fun onScrollEnd(): Boolean {
//        if (pendingPosition != NO_POSITION) {
//            currentPosition = pendingPosition
//            pendingPosition = NO_POSITION
//            scrolled = 0
//        }
//
//        val scrollDirection = Direction.fromDelta(scrolled)
//        if (abs(scrolled) == scrollToChangeCurrent) {
//            currentPosition += scrollDirection.applyTo(1)
//            scrolled = 0
//        }
//
//        pendingScroll = if (isAnotherItemCloserThanCurrent()) {
//            getHowMuchIsLeftToScroll(scrolled)
//        } else -scrolled
//
//        return if (pendingScroll == 0) true else {
//            startSmoothPendingScroll(); false
//        }
//    }
//
//    private fun onDragStart() {
//        if (abs(scrolled) > scrollToChangeCurrent) {
//            val scrolledPositions = scrolled / scrollToChangeCurrent
//            currentPosition += scrolledPositions
//            scrolled -= scrolledPositions * scrollToChangeCurrent
//        }
//        if (isAnotherItemCloserThanCurrent()) {
//            val direction = Direction.fromDelta(scrolled)
//            currentPosition += direction.applyTo(1)
//            scrolled = -getHowMuchIsLeftToScroll(scrolled)
//        }
//        pendingPosition = NO_POSITION
//        pendingScroll = 0
//    }
//
//    fun onFling(velocityX: Int, velocityY: Int) {
//        val velocity = orientationHelper.getFlingVelocity(velocityX, velocityY)
//        val newPosition = currentPosition + Direction.fromDelta(velocity).applyTo(1)
//        val isInScrollDirection = velocity * scrolled >= 0
//        val canFling = isInScrollDirection && newPosition in 0 until itemCount
//
//        if (canFling) {
//            pendingScroll = getHowMuchIsLeftToScroll(velocity)
//            if (pendingScroll != 0) startSmoothPendingScroll()
//        } else returnToCurrentPosition()
//    }
//
//    fun returnToCurrentPosition() {
//        pendingScroll = -scrolled
//        if (pendingScroll != 0) startSmoothPendingScroll()
//    }
//
//    private fun calculateAllowedScrollIn(direction: Direction): Int {
//        if (pendingScroll != 0) return abs(pendingScroll)
//
//        val allowedScroll: Int
//        val isBoundReached: Boolean
//        val isScrollDirectionAsBefore = direction.applyTo(scrolled) > 0
//
//        when {
//            direction == Direction.START && currentPosition == 0 -> {
//                isBoundReached = scrolled == 0
//                allowedScroll = if (isBoundReached) 0 else abs(scrolled)
//            }
//
//            direction == Direction.END && currentPosition == itemCount - 1 -> {
//                isBoundReached = scrolled == 0
//                allowedScroll = if (isBoundReached) 0 else abs(scrolled)
//            }
//
//            else -> {
//                isBoundReached = false
//                allowedScroll = if (isScrollDirectionAsBefore)
//                    scrollToChangeCurrent - abs(scrolled)
//                else scrollToChangeCurrent + abs(scrolled)
//            }
//        }
//        iScrollStateListener.onIsBoundReachedFlagChange(isBoundReached)
//        return allowedScroll
//    }
//
//    private fun startSmoothPendingScroll() {
//        val scroller = DiscreteLinearSmoothScroller(context).apply {
//            targetPosition = currentPosition
//        }
//        startSmoothScroll(scroller)
//    }
//
//    override fun onAdapterChanged(
//        oldAdapter: RecyclerView.Adapter<*>?,
//        newAdapter: RecyclerView.Adapter<*>?,
//    ) {
//        if (newAdapter != null && newAdapter.itemCount > 0) {
//            pendingPosition = NO_POSITION
//            scrolled = 0
//            pendingScroll = 0
//            currentPosition = 0
//        }
//        removeAllViews()
//    }
//
//    override fun onSaveInstanceState(): Parcelable {
//        val bundle = Bundle()
//        if (pendingPosition != NO_POSITION) currentPosition = pendingPosition
//        bundle.putInt(EXTRA_POSITION, currentPosition)
//        return bundle
//    }
//
//    override fun onRestoreInstanceState(state: Parcelable) {
//        val bundle = state as Bundle
//        currentPosition = bundle.getInt(EXTRA_POSITION)
//    }
//
//    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
//        RecyclerView.LayoutParams(
//            ViewGroup.LayoutParams.WRAP_CONTENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//
//    fun setItemTransformer(transformer: ScrollItemTransformer?) {
//        itemTransformer = transformer
//    }
//
//    fun setTimeForItemSettle(timeForItemSettle: Int) {
//        this.timeForItemSettle = timeForItemSettle
//    }
//
//    fun setOffscreenItems(offscreenItems: Int) {
//        this.offscreenItems = offscreenItems
//        extraLayoutSpace = scrollToChangeCurrent * offscreenItems
//        requestLayout()
//    }
//
//    fun setOrientation(orientation: OrientationView) {
//        orientationHelper = orientation.createHelper()
//        removeAllViews()
//        requestLayout()
//    }
//
//    fun getCurrentPosition(): Int = currentPosition
//
//    override fun onInitializeAccessibilityEvent(event: AccessibilityEvent) {
//        super.onInitializeAccessibilityEvent(event)
//        if (childCount > 0) {
//            event.fromIndex = getPosition(getFirstChild()!!)
//            event.toIndex = getPosition(getLastChild()!!)
//        }
//    }
//
//    private fun getCenterRelativePositionOf(v: View): Float {
//        val distanceFromCenter = orientationHelper.getDistanceFromCenter(
//            recyclerCenter,
//            getDecoratedLeft(v) + childHalfWidth,
//            getDecoratedTop(v) + childHalfHeight
//        )
//        return min(max(-1f, distanceFromCenter / scrollToChangeCurrent), 1f)
//    }
//
//    private fun getHowMuchIsLeftToScroll(dx: Int): Int =
//        Direction.fromDelta(dx).applyTo(scrollToChangeCurrent - abs(scrolled))
//
//    private fun isAnotherItemCloserThanCurrent(): Boolean =
//        abs(scrolled) >= scrollToChangeCurrent * 0.6f
//
//    fun getFirstChild(): View? = getChildAt(0)
//    fun getLastChild(): View? = getChildAt(childCount - 1)
//    fun getExtraLayoutSpace(): Int = extraLayoutSpace
//
//    private fun notifyScroll() {
//        val position = -min(max(-1f, scrolled / scrollToChangeCurrent.toFloat()), 1f)
//        iScrollStateListener.onScroll(position)
//    }
//
//    private fun isViewVisible(viewCenter: Point, endBound: Int): Boolean =
//        orientationHelper.isViewVisible(
//            viewCenter,
//            childHalfWidth,
//            childHalfHeight,
//            endBound,
//            extraLayoutSpace
//        )
//
//    private inner class DiscreteLinearSmoothScroller(context: Context) : LinearSmoothScroller(context) {
//        override fun calculateDxToMakeVisible(view: View, snapPreference: Int): Int =
//            orientationHelper.getPendingDx(-pendingScroll)
//
//        override fun calculateDyToMakeVisible(view: View, snapPreference: Int): Int =
//            orientationHelper.getPendingDy(-pendingScroll)
//
//        override fun calculateTimeForScrolling(dx: Int): Int {
//            val dist = min(abs(dx), scrollToChangeCurrent)
//            return (max(0.01f, dist / scrollToChangeCurrent.toFloat()) * timeForItemSettle).toInt()
//        }
//
//        override fun computeScrollVectorForPosition(targetPosition: Int): PointF =
//            PointF(
//                orientationHelper.getPendingDx(pendingScroll).toFloat(),
//                orientationHelper.getPendingDy(pendingScroll).toFloat()
//            )
//    }
//}