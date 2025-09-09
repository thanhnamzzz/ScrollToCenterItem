package lib.virgo.lib_scrollcenteritem.callback

interface IScrollStateListener {
    fun onIsBoundReachedFlagChange(isBoundReached: Boolean)
    fun onScrollStart()
    fun onScrollEnd()
    fun onScroll(currentViewPosition: Float)
    fun onCurrentViewFirstLayout()
}