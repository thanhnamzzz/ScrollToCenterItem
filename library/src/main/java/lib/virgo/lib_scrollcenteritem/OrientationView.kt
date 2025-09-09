package lib.virgo.lib_scrollcenteritem

import android.graphics.Point
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import lib.virgo.lib_scrollcenteritem.callback.Helper

/**
 * Rebuilt from [DroidSky/DiscreteScrollView](https://github.com/DroidSky/DiscreteScrollView)
 */
enum class OrientationView {

    HORIZONTAL {
        override fun createHelper(): Helper = HorizontalHelper()
    },

    VERTICAL {
        override fun createHelper(): Helper = VerticalHelper()
    };

    abstract fun createHelper(): Helper

    protected class HorizontalHelper : Helper {
        override fun getViewEnd(recyclerWidth: Int, recyclerHeight: Int): Int = recyclerWidth

        override fun getDistanceToChangeCurrent(childWidth: Int, childHeight: Int): Int = childWidth

        override fun setCurrentViewCenter(recyclerCenter: Point, scrolled: Int, outPoint: Point) {
            val newX = recyclerCenter.x - scrolled
            outPoint.set(newX, recyclerCenter.y)
        }

        override fun shiftViewCenter(direction: Direction, shiftAmount: Int, outCenter: Point) {
            val newX = outCenter.x + direction.applyTo(shiftAmount)
            outCenter.set(newX, outCenter.y)
        }

        override fun isViewVisible(
            center: Point,
            halfWidth: Int,
            halfHeight: Int,
            endBound: Int,
            extraSpace: Int,
        ): Boolean {
            val viewLeft = center.x - halfWidth
            val viewRight = center.x + halfWidth
            return viewLeft < (endBound + extraSpace) && viewRight > -extraSpace
        }

        override fun hasNewBecomeVisible(lm: ScrollToCenterLayoutManager): Boolean {
            val firstChild: View = lm.firstChild ?: return false
            val lastChild: View = lm.lastChild ?: return false
            val leftBound = -lm.extraLayoutSpace
            val rightBound = lm.width + lm.extraLayoutSpace
            val isNewVisibleFromLeft =
                lm.getDecoratedLeft(firstChild) > leftBound && lm.getPosition(firstChild) > 0
            val isNewVisibleFromRight =
                lm.getDecoratedRight(lastChild) < rightBound && lm.getPosition(lastChild) < lm.itemCount - 1
            return isNewVisibleFromLeft || isNewVisibleFromRight
        }

        override fun offsetChildren(amount: Int, lm: RecyclerView.LayoutManager) {
            lm.offsetChildrenHorizontal(amount)
        }

        override fun getDistanceFromCenter(
            center: Point,
            viewCenterX: Int,
            viewCenterY: Int,
        ): Float = (viewCenterX - center.x).toFloat()

        override fun getFlingVelocity(velocityX: Int, velocityY: Int): Int = velocityX

        override fun canScrollHorizontally(): Boolean = true

        override fun canScrollVertically(): Boolean = false

        override fun getPendingDx(pendingScroll: Int): Int = pendingScroll

        override fun getPendingDy(pendingScroll: Int): Int = 0
    }

    protected class VerticalHelper : Helper {
        override fun getViewEnd(recyclerWidth: Int, recyclerHeight: Int): Int = recyclerHeight

        override fun getDistanceToChangeCurrent(childWidth: Int, childHeight: Int): Int =
            childHeight

        override fun setCurrentViewCenter(recyclerCenter: Point, scrolled: Int, outPoint: Point) {
            val newY = recyclerCenter.y - scrolled
            outPoint.set(recyclerCenter.x, newY)
        }

        override fun shiftViewCenter(direction: Direction, shiftAmount: Int, outCenter: Point) {
            val newY = outCenter.y + direction.applyTo(shiftAmount)
            outCenter.set(outCenter.x, newY)
        }

        override fun offsetChildren(amount: Int, lm: RecyclerView.LayoutManager) {
            lm.offsetChildrenVertical(amount)
        }

        override fun getDistanceFromCenter(
            center: Point,
            viewCenterX: Int,
            viewCenterY: Int,
        ): Float = (viewCenterY - center.y).toFloat()

        override fun isViewVisible(
            center: Point,
            halfWidth: Int,
            halfHeight: Int,
            endBound: Int,
            extraSpace: Int,
        ): Boolean {
            val viewTop = center.y - halfHeight
            val viewBottom = center.y + halfHeight
            return viewTop < (endBound + extraSpace) && viewBottom > -extraSpace
        }

        override fun hasNewBecomeVisible(lm: ScrollToCenterLayoutManager): Boolean {
            val firstChild: View = lm.firstChild ?: return false
            val lastChild: View = lm.lastChild ?: return false
            val topBound = -lm.extraLayoutSpace
            val bottomBound = lm.height + lm.extraLayoutSpace
            val isNewVisibleFromTop =
                lm.getDecoratedTop(firstChild) > topBound && lm.getPosition(firstChild) > 0
            val isNewVisibleFromBottom =
                lm.getDecoratedBottom(lastChild) < bottomBound && lm.getPosition(lastChild) < lm.itemCount - 1
            return isNewVisibleFromTop || isNewVisibleFromBottom
        }

        override fun getFlingVelocity(velocityX: Int, velocityY: Int): Int = velocityY

        override fun canScrollHorizontally(): Boolean = false

        override fun canScrollVertically(): Boolean = true

        override fun getPendingDx(pendingScroll: Int): Int = 0

        override fun getPendingDy(pendingScroll: Int): Int = pendingScroll
    }
}