package lib.virgo.lib_scrollcenteritem.transform

import android.view.View
import androidx.annotation.FloatRange

/**
 * Rebuilt from [DroidSky/DiscreteScrollView](https://github.com/DroidSky/DiscreteScrollView)
 */
class ScaleTransformer : ScrollItemTransformer {

    private var pivotX: Pivot = Pivot.X.CENTER.create()
    private var pivotY: Pivot = Pivot.Y.CENTER.create()
    private var minScale: Float = 0.8f
    private var maxScale: Float = 1f
    private var maxMinDiff: Float = maxScale - minScale

    override fun transformItem(item: View?, position: Float) {
        item?.let {
            pivotX.setOn(it)
            pivotY.setOn(it)
            val closenessToCenter = 1f - kotlin.math.abs(position)
            val scale = minScale + maxMinDiff * closenessToCenter
            it.scaleX = scale
            it.scaleY = scale
        }
    }

    class Builder {
        private val transformer = ScaleTransformer()

        fun setMinScale(@FloatRange(from = 0.01) scale: Float): Builder {
            transformer.minScale = scale
            return this
        }

        fun setMaxScale(@FloatRange(from = 0.01) scale: Float): Builder {
            transformer.maxScale = scale
            return this
        }

        fun setPivotX(pivotX: Pivot.X): Builder {
            return setPivotX(pivotX.create())
        }

        fun setPivotX(pivot: Pivot): Builder {
            assertAxis(pivot, Pivot.AXIS_X)
            transformer.pivotX = pivot
            return this
        }

        fun setPivotY(pivotY: Pivot.Y): Builder {
            return setPivotY(pivotY.create())
        }

        fun setPivotY(pivot: Pivot): Builder {
            assertAxis(pivot, Pivot.AXIS_Y)
            transformer.pivotY = pivot
            return this
        }

        fun build(): ScaleTransformer {
            transformer.maxMinDiff = transformer.maxScale - transformer.minScale
            return transformer
        }

        private fun assertAxis(pivot: Pivot, axis: Int) {
            if (pivot.getAxis() != axis) {
                throw IllegalArgumentException("You passed a Pivot for wrong axis.")
            }
        }
    }
}
