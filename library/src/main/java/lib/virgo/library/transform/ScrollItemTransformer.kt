package lib.virgo.library.transform

import android.view.View

/**
 * Created by yarolegovich on 02.03.2017.
 */
interface ScrollItemTransformer {
    fun transformItem(item: View?, position: Float)
}
