package lib.virgo.library.transform

import android.view.View

/**
 * Rebuilt from [DroidSky/DiscreteScrollView](https://github.com/DroidSky/DiscreteScrollView)
 */
interface ScrollItemTransformer {
    fun transformItem(item: View?, position: Float)
}
